package com.konduru.rajesh.nfccheck;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity {
    private Button btn;
    private TextView opnmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button) findViewById(R.id.writenfc);
        opnmsg=(TextView) findViewById(R.id.openingmsg);
        final NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        if (adapter != null && adapter.isEnabled()) {
            opnmsg.setText("Please tap your card to phone to see ");
        }
        else {
            opnmsg.setText("NFC DISABLED ...PLZ ENABLE IT");
            btn.setVisibility(View.INVISIBLE);
        }

        btn.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(getApplicationContext(),NfcWrite.class);
                startActivity(myintent);

            }
        });
        handleIntent(getIntent());
    }
    public void handleIntent(Intent intent) {

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefRecord myRecord = ((NdefMessage) rawMsgs[0]).getRecords()[0];
            String nfcDatta = new String(myRecord.getPayload());
            Intent passIntent = new Intent(getApplicationContext(), Nfcshow.class);
            passIntent.putExtra("data", nfcDatta);
            startActivity(passIntent);

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }
}
