package com.example.bornittah.icapp.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bornittah.icapp.R;
import com.example.bornittah.icapp.util.DateTime;
import com.example.bornittah.icapp.util.UserDetails;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.example.bornittah.icapp.util.Constants.config.AMOUNT;
import static com.example.bornittah.icapp.util.Constants.config.AMOUNT_WORD;
import static com.example.bornittah.icapp.util.Constants.config.FIREBASE_URL;
import static com.example.bornittah.icapp.util.Constants.config.FIRST_NAME;
import static com.example.bornittah.icapp.util.Constants.config.LAST_NAME;
import static com.example.bornittah.icapp.util.Constants.config.LOANS;
import static com.example.bornittah.icapp.util.Constants.config.PASSWORD;
import static com.example.bornittah.icapp.util.Constants.config.RETURN_DATE;
import static com.example.bornittah.icapp.util.Constants.config.START_DATE;
import static com.example.bornittah.icapp.util.Constants.config.USERS;
import static com.example.bornittah.icapp.util.Constants.config.USER_ROLE;

/**
 * Created by john on 5/5/18.
 */

public class SavingActivity extends AppCompatActivity  {
    private EditText input_amount,input_words,start_date, return_date;
    private Button submit_btn;
    private Context context = this;
    private static final String TAG = "SavingActivity";
    private DatabaseReference databaseReference;
    private List<String> list ;
    private ProgressDialog pd;
    //private Spinner spinner;
    private Spinner spinner, spinner2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loan_application);
        input_amount = (EditText) findViewById(R.id.loan_amount);
        input_words = (EditText) findViewById(R.id.amount_in_words);
        start_date = (EditText) findViewById(R.id.start_date);
        return_date = (EditText) findViewById(R.id.return_date);
        submit_btn = (Button) findViewById(R.id.submit_btn);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new PickDate(start_date);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        return_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new PickDate(return_date);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        Firebase.setAndroidContext(this);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ///
                if (!pd.isShowing()){
                    pd = new ProgressDialog(context);
                    pd.setMessage("Loading...");
                    //pd.show();
                }


                String amount = input_amount.getText().toString().trim();
                String amount_word = input_words.getText().toString().trim();
                String startdate = start_date.getText().toString().trim();
                String returnDate = return_date.getText().toString().trim();
                String guaranty1 = spinner.getSelectedItem().toString().trim();
                String guaranty2 = spinner2.getSelectedItem().toString().trim();

                if(amount.equals("")){
                    input_amount.setError("Invalid input");
                }
                else if(amount_word.equals("")){
                    input_words.setError("Invalid input");
                }else if(startdate.equals("")){
                    start_date.setError("Invalid input");
                }else if(returnDate.equals("")){
                    return_date.setError("Invalid input");
                }else if (guaranty1.equals(guaranty2)){
                   Toast.makeText(context,"Select different Guarantors..!", Toast.LENGTH_SHORT).show();
                } else {
                    Firebase reference = new Firebase(FIREBASE_URL+LOANS);
                    HashMap map = new HashMap();
                    map.put(AMOUNT, amount);
                    map.put(AMOUNT_WORD, amount_word);
                    map.put(START_DATE, startdate);
                    map.put(RETURN_DATE, returnDate);
                    reference.push().setValue(map);
                    Toast.makeText(context, "Loan details submited..!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //TODO:: Verifying ...
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
        setListView();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    private void setListView(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                try {
                    list = new ArrayList<>();
                    pd.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }
                for (com.google.firebase.database.DataSnapshot childData: dataSnapshot.getChildren()) {
                    String keyy = childData.getKey();
                    String key2 = "";
                    for (com.google.firebase.database.DataSnapshot child2: childData.getChildren())
                        key2 = child2.getKey();
                    Log.e(TAG, "Key1: "+keyy+"\t Key2: "+key2);

                    String fname = (String) childData.child(key2).child(FIRST_NAME).getValue();
                    String lname = (String) childData.child(key2).child(LAST_NAME).getValue();

                    list.add(fname+" "+lname);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        list
                );
                spinner.setAdapter(adapter);
                spinner2.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static class PickDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        EditText editText;
        public PickDate(EditText editText){
            this.editText = editText;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,Integer.parseInt(DateTime.getCurrentDate().split("-")[0]));
            c.set(Calendar.MONTH,Integer.parseInt(DateTime.getCurrentDate().split("-")[1]));
            c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(DateTime.getCurrentDate().split("-")[2]));

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //Button button= (Button) getActivity().findViewById(R.id.dob_btn);
            editText.setText(+view.getYear()+"-"+view.getMonth()+"-"+view.getDayOfMonth());
        }
    }


}
