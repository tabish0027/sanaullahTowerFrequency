package com.example.murtaza.firebaseproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    TextView DisplayDateTime;
    TextView mobileMac;
    TextView tv,cell_id, internetSpeed;
    private TextView lat;
    private TextView longit;


    //LTE Services
    public static final String TAG = MainActivity.class.getSimpleName();
    private SignalStrength mSignalStrength;
    private boolean mDone = false;
    private TextView mText = null;
    private String mTextStr;
    private TelephonyManager mManager;

    //Button Savebtn;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) this.findViewById(R.id.textView1);
        cell_id = (TextView)findViewById(R.id.cellId);
        internetSpeed = (TextView)findViewById(R.id.dataSpeed);
        DisplayDateTime = (TextView)findViewById(R.id.textViewtime);
        lat = (TextView) findViewById(R.id.latitude_textview) ;
        longit = (TextView) findViewById(R.id.longitude_textview);
        mobileMac = (TextView) findViewById(R.id.MacAddres);

        //LTE Services
        mManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mText = (TextView) findViewById(R.id.text);
        mManager.listen(mListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CELL_LOCATION);


        //Savebtn =(Button)findViewById(R.id.SaveButton);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        BaseStationInfoHelper.BaseStationInfo info = BaseStationInfoHelper.getSimCardInfo(this);

        String displayString = "";
        if(info != null){
            //displayString += "Country mcc = " + info.mcc + "\r\n";
            // displayString += "ISP mnc = " + info.mnc + "\r\n";
            displayString += "BaseStation Identity: " + info.lac;
            //displayString += " Cell id = " + info.cid + "\r\n";
            tv.setText(displayString);
            cell_id.setText("Cell ID : "+info.cid);
        }
        else{
            tv.setText("Cannot get the base station information.");
        }
        //Time and date
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calander.getTime());
      //  DisplayDateTime.setText(Date);


        Date date = new Date();
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(date);
        DisplayDateTime.setText(strDate);
        //Lat Long
        GPSTracker mGPS = new GPSTracker(this);
        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            lat.setText("Latitude : "+mGPS.getLatitude());
            longit.setText("Langitude : "+mGPS.getLongitude());
        }else{
            lat.setText("Unabletofind");
            System.out.println("Unable");
        }
        //getting Mac  Address for device
        String Mobile_Mac = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //displaying id in textvie
        mobileMac.setText(" Mobile Mac Address: "+Mobile_Mac);
        /*Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // myRef.setValue("Hello world");
                SaveData();
            }
        });*/
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run () {

                MainActivity.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        resetData();
                        SaveData();
                    }
                });


            }
        };

// schedule the task to run starting now and then every hour...
        timer.schedule (hourlyTask, 0l, 1000*1*60);   // 1000*10*60 every 10 minut

        //internet Speed
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            Integer linkSpeed = wifiInfo.getLinkSpeed(); //measured using WifiInfo.LINK_SPEED_UNITS
            String speed = String.valueOf(linkSpeed);
            internetSpeed.setText("Internet Speed "+speed+" kbit/s");
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // Permission already Granted
            //Do your work here
//Perform operations here only which requires permission
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

    }
    public void resetData(){
        String displayString = "";
        BaseStationInfoHelper.BaseStationInfo info = BaseStationInfoHelper.getSimCardInfo(this);

        if(info != null){
            //displayString += "Country mcc = " + info.mcc + "\r\n";
            // displayString += "ISP mnc = " + info.mnc + "\r\n";
            displayString += "BaseStation Identity: " + info.lac;
            //displayString += " Cell id = " + info.cid + "\r\n";
            tv.setText(displayString);
            cell_id.setText("Cell ID : "+info.cid);
        }
        else{
            tv.setText("Cannot get the base station information.");
        }
        //Time and date
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calander.getTime());
        //  DisplayDateTime.setText(Date);


        Date date = new Date();
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(date);
        DisplayDateTime.setText(strDate);
        //Lat Long
        GPSTracker mGPS = new GPSTracker(this);
        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            lat.setText("Latitude : "+mGPS.getLatitude());
            longit.setText("Langitude : "+mGPS.getLongitude());
        }else{
            lat.setText("Unabletofind");
            System.out.println("Unable");
        }
        //getting Mac  Address for device
        String Mobile_Mac = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //displaying id in textvie
        mobileMac.setText(" Mobile Mac Address: "+Mobile_Mac);
        /*Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // myRef.setValue("Hello world");
                SaveData();
            }
        });*/


    }

    // Listener for signal strength.
    final PhoneStateListener mListener = new PhoneStateListener()
    {
        @Override
        public void onCellLocationChanged(CellLocation mLocation)
        {
            if (mDone) return;
            update();
        }
        @Override
        public void onSignalStrengthsChanged(SignalStrength sStrength)
        {
            if (mDone) return;

            //Log.d(TAG, "Signal strength obtained.");

            mSignalStrength = sStrength;

            update();
        }
    };
    // AsyncTask to avoid an ANR.
    private class ReflectionTask extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void... mVoid)
        {
            mTextStr =
                    (/*"DEVICE INFO\n\n" + "SDK: `" + Build.VERSION.SDK_INT + "`\nCODENAME: `" +
                            Build.VERSION.CODENAME + "`\nRELEASE: `" + Build.VERSION.RELEASE +
                            "`\nDevice: `" + Build.DEVICE + "`\nHARDWARE: `" + Build.HARDWARE +
                            "`\nMANUFACTURER: `" + Build.MANUFACTURER + "`\nMODEL: `" + Build.MODEL +
                            "`\nPRODUCT: `" + Build.PRODUCT + ((getRadio() == null) ? "" : ("`\nRADIO: `" + getRadio())) +
                            "`\nBRAND: `" + Build.BRAND + ((Build.VERSION.SDK_INT >= 8) ? ("`\nBOOTLOADER: `" + Build.BOOTLOADER) : "") +
                            "`\nBOARD: `" + Build.BOARD + "`\nID: `"+ Build.ID + "`\n\n" +*/
                            ReflectionUtils.dumpClass(SignalStrength.class, mSignalStrength) + "\n");
            return null;
        }
        protected void onProgressUpdate(Void... progress)
        {
            // Do nothing...
        }
        protected void onPostExecute(Void result)
        {
            complete();
        }
    }
    private final void complete()
    {
        mDone = true;
        try
        {
            mText.setText(mTextStr);
            // Stop listening.
            mManager.listen(mListener, PhoneStateListener.LISTEN_NONE);
            //Toast.makeText(getApplicationContext(), R.string.done, Toast.LENGTH_SHORT).show();
            // mSubmit.setEnabled(true);
        }
        catch (Exception e)
        {
            Log.e(TAG, "ERROR!!!", e);
        }
    }
    private final void update()
    {
        // if (mSignalStrength == null || mCellLocation == null) return;
        if (mSignalStrength == null)return;

        final ReflectionTask mTask = new ReflectionTask();
        mTask.execute();
    }

    /**
     * @return The Radio of the {@link Build} if available.
     */
    public static final String getRadio()
    {
        if (Build.VERSION.SDK_INT >= 8 && Build.VERSION.SDK_INT < 14)
            return Build.RADIO;
        else if (Build.VERSION.SDK_INT >= 14)
            return Build.getRadioVersion();
        else
            return null;
    }



    public void SaveData(){
        String date = DisplayDateTime.getText().toString().trim();
        String Lat = lat.getText().toString().trim();
        String Long = longit.getText().toString().trim();
        String BaseStationId = tv.getText().toString().trim();
        String Cell_ID = cell_id.getText().toString().trim();
        String Mobilemac = mobileMac.getText().toString().trim();
        String LteData  = mText.getText().toString().trim();
        String internetspeed = internetSpeed.getText().toString().trim();
        String id = myRef.push().getKey();
         AddData addData = new AddData(id, date, BaseStationId, Cell_ID, Lat, Long,Mobilemac,LteData,internetspeed);
         myRef.child("Users").child(id).setValue(addData);

    }


}

