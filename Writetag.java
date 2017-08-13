package com.konduru.rajesh.nfccheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;


public class Writetag extends Activity {

        private String writable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writetag);
        Intent intent = getIntent();
        writable = intent.getStringExtra("data");
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String nfcMessage = intent.getStringExtra("nfcMessage");

        if(nfcMessage != "") {
            writeTag(this, tag, nfcMessage);
        }
        else{
            Toast.makeText(getApplicationContext(),"fill the data field",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        setupIntent();
    }


    public void setupIntent() {
        String nfcMessage = writable;
        final NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent nfcIntent = new Intent(this, Writetag.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        nfcIntent.putExtra("nfcMessage", nfcMessage);
        PendingIntent pi = PendingIntent.getActivity(this, 0, nfcIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

        nfcAdapter.enableForegroundDispatch( this, pi, new IntentFilter[] { tagDetected }, null);
    }

    public boolean writeTag(Context context, Tag tag, String data) {

        NdefRecord appRecord = NdefRecord.createApplicationRecord(context.getPackageName());
        NdefRecord.createUri(data + context);

        NdefRecord relRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                new String("application/" + context.getPackageName()).getBytes(Charset.forName("US-ASCII")),
                null, data.getBytes());

        NdefMessage message = new NdefMessage(new NdefRecord[] { relRecord, appRecord });

        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();


                if (!ndef.isWritable()) {
                    Toast.makeText(getApplicationContext(), "The tag couldnt be written...", Toast.LENGTH_SHORT).show();
                    return false;
                }

                int size = message.toByteArray().length;
                if (ndef.getMaxSize() < size) {
                    Toast.makeText(getApplicationContext(), "No space on the tag...", Toast.LENGTH_SHORT).show();
                    finish();
                    return false;
                }

                try {

                    ndef.writeNdefMessage(message);
                    Toast.makeText(getApplicationContext(), "Writing successful", Toast.LENGTH_LONG).show();
                    finish();
                    return true;
                } catch (TagLostException tle) {
                    return false;
                } catch (IOException ioe) {
                    return false;
                } catch (FormatException fe) {
                    return false;
                }

            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);

                        return true;
                    } catch (TagLostException tle) {
                        return false;
                    } catch (IOException ioe) {

                        return false;
                    } catch (FormatException fe) {

                        return false;
                    }
                } else {

                    return false;
                }
            }
        } catch (Exception e) {

        }

        return false;
    }


}
