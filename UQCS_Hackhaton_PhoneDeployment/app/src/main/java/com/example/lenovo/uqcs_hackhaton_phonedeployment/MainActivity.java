package com.example.lenovo.uqcs_hackhaton_phonedeployment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    // Define attribute to store the lap counter textView
    private TextView lapCounter;
    private Button minus_button;
    private Button reset_button;
    // Define attribute to store lap counter
    private int counter = 0;
    // device sensor manager
    private SensorManager mSensorManager;
    // define accelerometer sensor
    private Sensor accelerometer;
    // define magtenometer sensor
    private Sensor magnetometer;
    // boolean flag to indicate if application has just been used. If it is just started. The value is false. Else, it is true
    private boolean flag = false;
    // record the initial bearing of the smartphone when the lap starts. Put the initial value as an nonsensical value since (-180 degrees <angle<180 degrees)
    private int initialLapDegrees;
    float[] mGravity;
    float[] mGeomagnetic;
    float angle;
    private TextView azimut;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            lapCounter = (TextView)findViewById(R.id.laps);
            minus_button = (Button) findViewById(R.id.button_minus);
            reset_button = (Button) findViewById(R.id.button_reset);
            azimut = (TextView) findViewById(R.id.azimut);
            mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        lapCounter.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Increment the counter by 1
                    if(counter == 99)
                    {
                        // reset lap counter to 1
                        counter = 1;
                    }
                    else
                    {
                        // increment the lap counter
                        counter ++;
                    }
                    // update the display
                    // if the display is less than 10, append 0 in front of the label
                    if(counter < 10)
                    {
                        lapCounter.setText("0"+ counter);
                    }
                    else
                    {
                        lapCounter.setText(Integer.toString(counter));
                    }

                }
            });

            minus_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(counter > 0)
                    {
                        counter = counter - 1;
                    }
                    if(counter < 10)
                    {
                        lapCounter.setText("0"+ counter);
                    }
                    else
                    {
                        lapCounter.setText(Integer.toString(counter));
                    }
                }
            });

            reset_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    counter = 0;
                    lapCounter.setText("0" + Integer.toString(counter));
                }
            });
        }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            mGravity = sensorEvent.values;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            mGeomagnetic = sensorEvent.values;
        }
        if (mGravity != null && mGeomagnetic != null)
        {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                angle = orientation[0]; // orientation contains: azimut, pitch and roll

            }
        }
        float degrees = -angle * 360/(2*3.14159f);
        int degreesInteger = (int) degrees;
        azimut.setText(Integer.toString(degreesInteger));
        // if the app is launched(initial degree values should be -181)
        if(flag == false)
        {
            // set the initial bearings to the sensor values
            initialLapDegrees = degreesInteger;
            flag = true;
        }
        // check if the difference between the sensor readout and initial angle is greater than 110 degrees. If yes, replace the initialLapDegrees with the present readout and increment the  lap counter by 1
        if((Math.abs(initialLapDegrees-degreesInteger))>= 110)
        {
            initialLapDegrees = degreesInteger;
            counter = counter + 1;
            if(counter < 10)
            {
                lapCounter.setText("0"+ counter);
            }
            else
            {
                lapCounter.setText(Integer.toString(counter));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
}
