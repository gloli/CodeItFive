package edu.dartmouth.cs.codeitfive;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import edu.dartmouth.cs.codeitfive.opengl.MyGLSurfaceView;

public class MainActivity extends Activity implements ServiceConnection {
  private Button startButton;
    private Button exitButton;
  private TextView counterView;
    private TextView bestTime;
    private String timer = "0:00";
    ImageView finalImage;

    long best;
    long count;
    Chronometer stopWatch;

  private Messenger mServiceMessenger = null;
  boolean mIsBound;

  MyGLSurfaceView surfaceView;
    float screenHeight;
    Bitmap bmap;

  private final Messenger mMessenger = new Messenger(
      new IncomingMessageHandler());

  private ServiceConnection mConnection = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Globals.context = this;

// set up xml in conjunction with GLSurfaceView
      XmlPullParser parser = this.getResources().getLayout(R.layout.activity_main);
      AttributeSet attributes = Xml.asAttributeSet(parser);
      surfaceView = new MyGLSurfaceView(this, attributes);
    setContentView(R.layout.activity_main);

      getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

    startButton = (Button) findViewById(R.id.startButton);
      exitButton = (Button) findViewById(R.id.exitButton);
      finalImage = (ImageView) findViewById(R.id.finalImageView);

      exitButton.setVisibility(View.INVISIBLE);
      finalImage.setVisibility(View.INVISIBLE);

    bestTime = (TextView) findViewById(R.id.best_time);
      bestTime.setText("Best Time:\n" + timer);
      counterView = (TextView) findViewById(R.id.timer);
      counterView.setText(timer);

      bmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.coke_background);

    startButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startShakeSequence();
          startTimer();
      }
    });
  }

  private void startShakeSequence() {
    mIsBound = false;

    // Start the service
    Intent serviceIntent = new Intent(MainActivity.this, TrackingService.class);
    startService(serviceIntent);

    // Bind to the service
    bindService();
  }

    private void startTimer() {
        exitButton.setVisibility(View.INVISIBLE);
        finalImage.setVisibility(View.INVISIBLE);

        stopWatch = (Chronometer) findViewById(R.id.chronometer);
        final long start = SystemClock.elapsedRealtime();

        stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                count = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
                timer = (count/60) + ":" + (count%60); //+ ":" + ((count%1000) / 100);
                counterView.setText(timer);
            }
        });
        stopWatch.setBase(start);
        stopWatch.start();
    }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  private void bindService() {
    bindService(new Intent(this, TrackingService.class), mConnection,
        Context.BIND_AUTO_CREATE);
    mIsBound = true;
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public boolean isDestroyed() {
    return super.isDestroyed();
  }

  /**
   * Un-bind this Activity to TimerService
   */
  private void doUnbindService() {
    if (mIsBound) {
      // If we have received the service, and hence registered with it,
      // then now is the time to unregister.
      if (mServiceMessenger != null) {
        try {
          Message msg = Message.obtain(null,
              TrackingService.MSG_UNREGISTER_CLIENT);
          mServiceMessenger.send(msg);
        } catch (RemoteException e) {
        }
      }
      // Detach our existing connection.
      unbindService(mConnection);
      mIsBound = false;
    }
  }

  @Override
  public void onServiceConnected(ComponentName name, IBinder service) {
    mServiceMessenger = new Messenger(service);
    try {
      Message msg = Message.obtain(null, TrackingService.MSG_REGISTER_CLIENT);
      msg.replyTo = mMessenger;
      mServiceMessenger.send(msg);
    } catch (RemoteException e) {
    }
  }

  @Override
  public void onServiceDisconnected(ComponentName name) {
    mServiceMessenger = null;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    try {
      doUnbindService();
      stopService(new Intent(MainActivity.this, TrackingService.class));
    } catch (Throwable t) {
    }
  }


  private class IncomingMessageHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
        if (TrackingService.shakeCounter < Globals.SHAKE_MAX) {
            surfaceView.raiseGraphic(TrackingService.shakeCounter);

        }
      else
            stopTracking();
    }
  }


  // tracking complete
  public void stopTracking() {
    // stop service
      doUnbindService();
      stopService(new Intent(MainActivity.this, TrackingService.class));

      // change view
      finalImage.setVisibility(View.VISIBLE);
      finalImage.setImageResource(R.drawable.open);
      stopWatch.stop();

      if (count < best || best == 0) {
          best = count;
          bestTime.setText("Best Time:\n" + (best/60) + ":" + (best%60));
      }


      // reset
      startButton.setText("Restart");
      exitButton.setVisibility(View.VISIBLE);
      exitButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });

  }
}
