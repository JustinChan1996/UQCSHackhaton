package com.example.lenovo.uqcs_pool_sharks;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

/**
 * Created by Lenovo on 26/8/2017.
 */

public class Lap_Counter extends Activity
{
    // Define attribute to store the lap counter textView
    private TextView lapCounter;
    // Define attribute to store lap counter
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_display);
        lapCounter = (TextView)findViewById(R.id.text);

    }
}
