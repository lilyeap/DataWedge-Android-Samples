// **********************************************************************************************
// *                                                                                            *
// *    This application is intended for demonstration purposes only. It is provided as-is      *
// *    without guarantee or warranty and may be modified to suit individual needs.             *
// *                                                                                            *
// **********************************************************************************************

package com.zebra.basicintent1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private List<ScanItem> scanItems = new ArrayList<>();
    private ScanAdapter scanAdapter;

    //
    // The section snippet below registers to receive the data broadcast from the
    // DataWedge intent output. In the example, a dynamic broadcast receiver is
    // registered in the onCreate() call of the target app. Notice that the filtered action
    // matches the "Intent action" specified in the DataWedge Intent Output configuration.
    //
    // For a production app, a more efficient way to the register and unregister the receiver
    // might be to use the onResume() and onPause() calls.

    // Note: If DataWedge had been configured to start an activity (instead of a broadcast),
    // the intent could be handled in the app's manifest by calling getIntent() in onCreate().
    // If configured as startService, then a service must be created to receive the intent.
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView scansRecyclerView = findViewById(R.id.scansRecyclerView);
        scansRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scanAdapter = new ScanAdapter(scanItems);
        scansRecyclerView.setAdapter(scanAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    //
    // After registering the broadcast receiver, the next step (below) is to define it.
    // Here it's done in the MainActivity.java, but also can be handled by a separate class.
    // The logic of extracting the scanned data and displaying it on the screen
    // is executed in its own method (later in the code). Note the use of the
    // extra keys defined in the strings.xml file.
    //
    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();

            //  This is useful for debugging to verify the format of received intents from DataWedge
//            for (String key : b.keySet())
//            {
//                Log.v(LOG_TAG, key);
//            }

            if (action.equals(getResources().getString(R.string.activity_intent_filter_action))) {
                //  Received a barcode scan
                try {
                    displayScanResult(intent, "via Broadcast");
                } catch (Exception e) {
                    //  Catch if the UI does not exist when we receive the broadcast
                }
            }
        }
    };

    //
    // The section below assumes that a UI exists in which to place the data. A production
    // application would be driving much of the behavior following a scan.
    //
    private void displayScanResult(Intent initiatingIntent, String howDataReceived)
    {
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
//        // original Stuff
//        final TextView lblScanSource = (TextView) findViewById(R.id.lblScanSource);
//        final TextView lblScanData = (TextView) findViewById(R.id.lblScanData);
//        final TextView lblScanLabelType = (TextView) findViewById(R.id.lblScanDecoder);
//
//        lblScanSource.setText(decodedSource + " " + howDataReceived);
//        lblScanData.setText(decodedData);
//        lblScanLabelType.setText(decodedLabelType);


        // debugging stuff
        Log.d("ScanDataDebug", "Decoded Data: " + decodedData);
        if (decodedData != null) {
            Log.d("ScanDataType", "Type of decodedData: " + decodedData.getClass().getName());
        } else {
            Log.d("ScanDataType", "decodedData is null");
        }

        // add on to the original app
//        final TextView lblCustomText = (TextView) findViewById(R.id.lblCustomText);
//        if ("librasp24".equals(decodedData)) {
//            Log.d("ScanDataType", "entered");
//            lblCustomText.setText(R.string.custom_text_libra);
//        } else {
//            Log.d("ScanDataType", "did entered");
//            lblCustomText.setText(R.string.custom_text_NOTlibra);
//        }


        String customText = decodedSource;
        if ("librasp24".equals(decodedData)) {
            customText = "Libra Barcode!";
        }

        // Create a new ScanItem and add it to the list
        ScanItem newScan = new ScanItem(customText, decodedData, decodedLabelType);
        scanItems.add(newScan);

        // Notify the adapter that the data set has changed so the view can be updated
        scanAdapter.notifyDataSetChanged();
    }
}
