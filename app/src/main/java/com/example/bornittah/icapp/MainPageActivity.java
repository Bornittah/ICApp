package com.example.bornittah.icapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainPageActivity extends Activity {
    ListView listView;
    ArrayList items = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        listView = (ListView)findViewById(R.id.listview1);
    }
        public ArrayList getItems(){
            items.add("Savings");
            items.add("Checking");
            items.add("Loans");

            return null;
        }


}
