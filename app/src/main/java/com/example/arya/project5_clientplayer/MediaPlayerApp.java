package com.example.arya.project5_clientplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arya.project5_audioserver.IMediaPlayerGenerator;

/**
 * Created by arya's on 11/23/2016.
 */
public class MediaPlayerApp extends AppCompatActivity {
    TextView clip1;TextView clip2; TextView clip3;
    TextView clip4;TextView clip5;
    EditText songIndex;
    public static IMediaPlayerGenerator mediaPlayerService;
    String input;
    boolean mIsBound=false;
    String isPlayed="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_layout);
        songIndex= (EditText) findViewById(R.id.editText);
        clip1= (TextView) findViewById(R.id.clip1);
        clip2= (TextView) findViewById(R.id.clip2);
        clip3= (TextView) findViewById(R.id.clip3);
        clip4= (TextView) findViewById(R.id.clip4);
        clip5= (TextView) findViewById(R.id.clip5);
    }

    //the aidl is bound to this service in this method
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(IMediaPlayerGenerator.class.getName());
        ResolveInfo info= getPackageManager().resolveService(intent, PackageManager.GET_META_DATA);
        intent.setComponent(new ComponentName(info.serviceInfo.packageName,info.serviceInfo.name));
        bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);

    }

    //service connection is established and also checked
    ServiceConnection mConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mediaPlayerService=IMediaPlayerGenerator.Stub.asInterface(iBinder);
            mIsBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mediaPlayerService=null;
            mIsBound=false;
        }
    };

    //play function implemented
    public void onPlayFunction(View view) throws RemoteException {
        //the background color of the text views are reset
        clip1.setBackgroundColor(Color.TRANSPARENT);
        clip2.setBackgroundColor(Color.TRANSPARENT);
        clip3.setBackgroundColor(Color.TRANSPARENT);
        clip4.setBackgroundColor(Color.TRANSPARENT);
        clip5.setBackgroundColor(Color.TRANSPARENT);
        //checking if the service is bound to the client
        if (mIsBound==true){
            input=songIndex.getText().toString();
            if (input.isEmpty()){
                Toast.makeText(getBaseContext(),"Please enter a song index",Toast.LENGTH_LONG).show();
            }else {
                //if the service is bound , then send the clicked index to the service and play the music
                mediaPlayerService.getPlay(input);
                highlightList(Integer.parseInt(input));
                //making the index text view (where user will select the song index) disable while the song is played
                songIndex.setEnabled(false);
                isPlayed = "YES";
            }
        }else {
            Toast.makeText(getBaseContext(),"The Audio Service is not bound to the client",Toast.LENGTH_SHORT).show();
        }
    }

    public void onPauseFunction(View view) throws RemoteException {
        if (mIsBound==true){
            //if the service is bound , then send the clicked index to the service and pause the music
            mediaPlayerService.getPause();
            //making the index text view (where user will select the song index) enable while the song is played,so that user can
            //choose another song while playing other song
            songIndex.setEnabled(true);
        }else {
            Toast.makeText(getBaseContext(),"The Audio Service is not bound to the client",Toast.LENGTH_SHORT).show();
        }
    }

    public void onResumeFunction(View view) throws RemoteException {
        if (mIsBound==true){
            //if the service is bound , then send the clicked index to the service and resume the music
            mediaPlayerService.getResume();
            if(isPlayed.equalsIgnoreCase("YES")){
                //making the index text view (where user will select the song index) disable while the song is played
                songIndex.setEnabled(false);
            } else{
                songIndex.setEnabled(true);
            }
        }else{
            Toast.makeText(getBaseContext(),"The Audio Service is not bound to the client",Toast.LENGTH_SHORT).show();
        }
    }

    public void onStopFunction(View view) throws RemoteException {
        if (mIsBound==true){
            //if the service is bound , then send the clicked index to the service and stop the music
            mediaPlayerService.getStop();
            //making the index text view (where user will select the song index) enable while the song is played
            //as to allow the user to stop a certain song and select another song on the fly
            songIndex.setEnabled(true);
        }else {
            Toast.makeText(getBaseContext(),"The Audio Service is not bound to the client",Toast.LENGTH_SHORT).show();
        }
    }

    //creating another activity to start the records function where the transactions are recorded
    public  void onRecordsFunction(View View){
        Intent intent= new Intent(this,Records.class);
        startActivity(intent);
    }

    //highlight the text view based on what index is selected by the user
    public  void highlightList(int input){
        switch (input){
            case 1:{clip1.setBackgroundColor(Color.parseColor("#AED6F1"));break;}
            case 2:{clip2.setBackgroundColor(Color.parseColor("#AED6F1"));break;}
            case 3:{clip3.setBackgroundColor(Color.parseColor("#AED6F1"));break;}
            case 4:{clip4.setBackgroundColor(Color.parseColor("#AED6F1"));break;}
            case 5:{clip5.setBackgroundColor(Color.parseColor("#AED6F1"));break;}
        }
    }

    //over-riding the onDestroy() to unbind the service
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            unbindService(this.mConnection);
        }
    }
}