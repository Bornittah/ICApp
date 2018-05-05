package com.example.bornittah.icapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends Activity {
    ListView listView;
    List items = new ArrayList();
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        listView = (ListView)findViewById(R.id.listview1);
        setView();
    }

    private void setView(){
        items = new ArrayList();
        items.add("Savings");
        items.add("Checking");
        items.add("Loans");
        try{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_list_item_1,
                    items
            );
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
