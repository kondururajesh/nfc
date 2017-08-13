package com.konduru.rajesh.nfccheck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Nfcscreen extends Activity {
    @Override
    protected void onCreate(Bundle rajesh) {
        super.onCreate(rajesh);
        setContentView(R.layout.nfcc);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent openStartingPoint = new Intent("com.konduru.rajesh.nfccheck.MAINACTIVITY");
                    startActivity(openStartingPoint);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }




}
