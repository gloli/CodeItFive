package edu.dartmouth.cs.codeitfive;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import edu.dartmouth.cs.codeitfive.opengl.MyGLSurfaceView;
import edu.dartmouth.cs.codeitfive.opengl.MyGLRenderer;

public class MainActivity extends Activity implements ServiceConnection {
  private Button startButton;
  private TextView counterView;
  private Messenger mServiceMessenger = null;
  boolean mIsBound;
  MyGLSurfaceView surfaceView;

  private final Messenger mMessenger = new Messenger(
      new IncomingMessageHandler());

  private ServiceConnection mConnection = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

// set up xml in conjunction with GLSurfaceView
      XmlPullParser parser = this.getResources().getLayout(R.layout.activity_main);
      AttributeSet attributes = Xml.asAttributeSet(parser);
      surfaceView = new MyGLSurfaceView(this, attributes);
    setContentView(R.layout.activity_main);


    startButton = (Button) findViewById(R.id.startButton);
    counterView = (TextView) findViewById(R.id.best_time);

    // TODO if we even see a start button. If not, we need to think of what to do...
    startButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startShakeSequence();
      }
    });


    if (TrackingService.shakeCounter > 10000) {
      doUnbindService();
      stopService(new Intent(MainActivity.this, TrackingService.class));
      // TODO show results
    }
  }

  private void startShakeSequence() {
    mIsBound = false;

    // Start the service
    Intent serviceIntent = new Intent(MainActivity.this, TrackingService.class);
    startService(serviceIntent);

    // Bind to the service
    bindService();
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
      // TODO write this method to update the view
      updateDrawing(TrackingService.shakeCounter);
      counterView.setText("" + TrackingService.shakeCounter);

    }
  }

  // TODO implement this method
  public void updateDrawing(int shakeCount) {


  }
}
