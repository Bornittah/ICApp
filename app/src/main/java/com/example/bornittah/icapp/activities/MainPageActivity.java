package com.example.bornittah.icapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bornittah.icapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {
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

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = items.get(i).toString();
                    if (item.equals("Savings")){
                        startActivity(new Intent(context, SavingActivity.class));
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
