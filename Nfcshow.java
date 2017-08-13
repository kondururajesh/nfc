package com.konduru.rajesh.nfccheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class Nfcshow extends Activity {
    private TextView txtview;
    private String datta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfcshow);
        txtview=(TextView)findViewById(R.id.data);
        Intent intent = getIntent();
         datta = intent.getStringExtra("data");
        txtview.setText( datta);

    }
}
