package com.example.arya.project5_clientplayer;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arya's on 11/23/2016.
 */

//Activity to record all the transactions athat happen in the player
public class Records extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_layout);
        String array_records[] = new String[1000];
        ListView listview;

        //if the media player is not null, then we get all the data from the service in an array
        if (MediaPlayerApp.mediaPlayerService!=null){
            try {
                array_records=MediaPlayerApp.mediaPlayerService.getAllData();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        //adding all the items from rhe array in an array-list
        if (array_records!=null){
            List<String> myList = new ArrayList<>();
            for(String s: array_records){
                if (s != null && s.length() > 0) {
                    myList.add(s);
                }
            }
            //creating a list view adapter to add all the items in the list view on-the-fly
            listview= (ListView) findViewById(R.id.records);
            ArrayAdapter adapter = new ArrayAdapter(this,R.layout.record_single_row_layout,R.id.textView, myList);
            listview.setAdapter(adapter);
        }
    }
}
