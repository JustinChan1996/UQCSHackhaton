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
    // record the initial bearing of the smartphone when the lap starts
    private float initialLapDegrees = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            lapCounter = (TextView)findViewById(R.id.laps);
            minus_button = (Button) findViewById(R.id.button_minus);
            reset_button = (Button) findViewById(R.id.button_reset);
            // initialize your android device sensor capabilities
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


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

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
}
