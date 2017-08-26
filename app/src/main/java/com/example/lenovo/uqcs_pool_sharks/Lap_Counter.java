package com.example.lenovo.uqcs_pool_sharks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Lenovo on 26/8/2017.
 */

public class Lap_Counter extends Activity
{
    // Define attribute to store the lap counter textView
    private TextView lapCounter;
    private Button minus_button;
    private Button reset_button;
    // Define attribute to store lap counter
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_display);
        lapCounter = (TextView)findViewById(R.id.laps);
        minus_button = (Button) findViewById(R.id.button_minus);
        reset_button = (Button) findViewById(R.id.button_reset);

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
}
