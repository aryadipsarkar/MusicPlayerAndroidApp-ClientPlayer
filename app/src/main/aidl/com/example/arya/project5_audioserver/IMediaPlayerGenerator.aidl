// IMediaPlayerGenerator.aidl
package com.example.arya.project5_audioserver;

// Declare any non-default types here with import statements

interface IMediaPlayerGenerator {
       void getPlay(String str);
       void getPause();
       void getResume();
       void getStop();
       String[] getAllData();
}