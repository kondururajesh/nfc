package com.konduru.rajesh.nfccheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;


public class NfcWrite extends Activity {
    ListView lv;
     Button button,updatebtn,deletebtn,clearbtn;
     EditText et1;
    ArrayList<String> names=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfcwrite);
        lv=(ListView)findViewById(R.id.listview1);
        et1=(EditText)findViewById(R.id.et);
        button=(Button)findViewById(R.id.button);
        updatebtn=(Button)findViewById(R.id.button1);
        deletebtn=(Button)findViewById(R.id.button2);
        clearbtn=(Button)findViewById(R.id.button3);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,names);
        lv.setAdapter(adapter);
         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                 et1.setText(names.get(pos));
             }
         });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                write();
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });


    }
    private void write()
    {  String myData=et1.getText().toString();
        if (!myData.isEmpty() && myData.length()>0)
        {  adapter.add(myData);
            adapter.notifyDataSetChanged();
            et1.setText("");
            Toast.makeText(getApplicationContext(),"Written"+myData,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),Writetag.class);
            intent.putExtra("data",myData);
            startActivity(intent);

        }else{
            Toast.makeText(getApplicationContext(),"Nothing Written",Toast.LENGTH_SHORT).show();
        }

    }
    private void update(){
        String myData=et1.getText().toString();

        int pos=lv.getCheckedItemPosition();
        if (!myData.isEmpty() && myData.length()>0)
        {  adapter.remove(names.get(pos));
            adapter.insert(myData,pos);
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Updated"+myData,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),Writetag.class);
            intent.putExtra("data",myData);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Nothing to update",Toast.LENGTH_SHORT).show();
        }
    }
    private void delete(){
        int pos=lv.getCheckedItemPosition();
        if(pos>-1){
            adapter.remove(names.get(pos));
            adapter.notifyDataSetChanged();
            et1.setText("");
            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Nothing to delete",Toast.LENGTH_SHORT).show();
        }
    }
    private void clear(){
        adapter.clear();
    }


}
