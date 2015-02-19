package edu.dartmouth.cs.codeitfive;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;

public class TrackingService extends Service implements

    SensorEventListener {
  private long startTime;

  private Messenger mClient;
  public static final int MSG_REGISTER_CLIENT = 1;
  public static final int MSG_UNREGISTER_CLIENT = 2;
  public static final int MSG_SHAKE_UPDATED = 4;
  public static int shakeCounter = 0;
  private SensorManager mSensorManager;
  private Sensor mAccelerometer;

  private final Messenger mMessenger = new Messenger(
      new IncomingMessageHandler()); // Target we publish for clients to

  // ==================== Service Lifecycle Functions ====================
  @Override
  public void onCreate() {
    Log.d("CREATED", "CREATED");

    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    startTime = System.currentTimeMillis();
    Log.d("STARTED", "STARTED");

    startActivityUpdate();
    return START_STICKY; // Run until explicitly stopped.
  }

  @Override
  public IBinder onBind(Intent intent) {
    Log.d("BOUND", "BOUND");

    return mMessenger.getBinder();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mSensorManager.unregisterListener(this);
  }


  public void startActivityUpdate() {
    mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    mAccelerometer = mSensorManager
        .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

    mSensorManager.registerListener(this, mAccelerometer,
        SensorManager.SENSOR_DELAY_FASTEST);
  }

  // =================== Message Handling Functions ===================

  private void sendMessageToMainActivity() {
    if (mClient != null) {
      try {
        Message msg = Message.obtain(null, MSG_SHAKE_UPDATED);
        mClient.send(msg);
      } catch (RemoteException e) {
        // The client is dead.
        mClient = null;
      }
    }
  }

  /**
   * Handle incoming messages from MainActivity
   */
  private class IncomingMessageHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_REGISTER_CLIENT:
          mClient = msg.replyTo;
          break;
        case MSG_UNREGISTER_CLIENT:
          mClient = null;
          break;
        default:
          super.handleMessage(msg);
      }
    }
  }

  private ArrayList<Double> featVect = new ArrayList<>();
  private int blockIndex = 0;
  private double[] accBlock = new double[Globals.ACCELEROMETER_BLOCK_CAPACITY];

  //=================== SensorEventListener Functions ====================
  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
      double m = Math.sqrt(event.values[0] * event.values[0]
          + event.values[1] * event.values[1] + event.values[2]
          * event.values[2]);
      accBlock[blockIndex] = m;
      blockIndex++;
      if (blockIndex == Globals.ACCELEROMETER_BLOCK_CAPACITY) {
        createFeatureVector();
        try {
          double classification = WekaClassifier.classify(featVect.toArray());
          switch((int)classification) {
            case Globals.SHAKE_ID_HARD:
              shakeCounter += 5;
              Log.d("SHAKE MONITOR", "SHAKING HARD");
              break;
            case Globals.SHAKE_ID_MODERATE:
              shakeCounter += 3;
              Log.d("SHAKE MONITOR", "SHAKING MODERATE");
              break;
            case Globals.SHAKE_ID_SOFT:
              shakeCounter += 1;
              Log.d("SHAKE MONITOR", "SHAKING SOFT");
              break;
            case Globals.SHAKE_ID_NONE:
              shakeCounter = Math.max(0, shakeCounter/2);
              Log.d("SHAKE MONITOR", "SHAKING NONE");
              break;
          }
          // have a decay function;

          sendMessageToMainActivity();
        } catch (Exception e) {
          // fail quietly
        }
        blockIndex = 0;
        featVect.clear();
      }
    }
  }

  private void createFeatureVector() {
    FFT fft = new FFT(Globals.ACCELEROMETER_BLOCK_CAPACITY);
    double[] re = accBlock;
    double[] im = new double[Globals.ACCELEROMETER_BLOCK_CAPACITY];

    double max = Double.MIN_VALUE;

    for (double val : accBlock) {
      if (max < val) {
        max = val;
      }
    }

    fft.fft(re, im);

    for (int i = 0; i < re.length; i++) {
      // Compute each coefficient
      double mag = Math.sqrt(re[i] * re[i] + im[i] * im[i]);
      // Adding the computed FFT coefficient to the
      // featVect
      featVect.add(Double.valueOf(mag));
      // Clear the field
      im[i] = .0;
    }
    // Finally, append max after frequency components
    featVect.add(Double.valueOf(max));
    accBlock = new double[64];
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }
  //======================================================================
}