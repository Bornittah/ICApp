package com.example.bornittah.icapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bornittah.icapp.R;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.bornittah.icapp.util.Constants.config.FIREBASE_URL;
import static com.example.bornittah.icapp.util.Constants.config.FIRST_NAME;
import static com.example.bornittah.icapp.util.Constants.config.LAST_NAME;
import static com.example.bornittah.icapp.util.Constants.config.ONLINE;
import static com.example.bornittah.icapp.util.Constants.config.PASSWORD;
import static com.example.bornittah.icapp.util.Constants.config.USERNAME;
import static com.example.bornittah.icapp.util.Constants.config.USERS;
import static com.example.bornittah.icapp.util.Constants.config.USER_ROLE;

public class SignupActivity extends AppCompatActivity {

    private Context context = this;
    private EditText input_fname, input_lname, input_username, input_password;
    private Spinner spinner_role;
    private Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_signup);

        Firebase.setAndroidContext(this);
        input_fname = (EditText) findViewById(R.id.input_fname);
        input_lname = (EditText) findViewById(R.id.input_lname);
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);

        spinner_role = (Spinner) findViewById(R.id.spinner_role);
        submit_btn = (Button) findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname = input_fname.getText().toString().trim();
                final String lname = input_lname.getText().toString().trim();
                final String username = input_username.getText().toString().trim();
                final String password = input_password.getText().toString().trim();
                final String role = spinner_role.getSelectedItem().toString().trim();

                if (fname.equals("")){
                    input_fname.setError("can't be blank");
                }
                else if (lname.equals("")){
                    input_lname.setError("can't be blank");
                }
                else if (username.equals("")){
                    input_username.setError("can't be blank");
                }
                else if (password.equals("")){
                    input_password.setError("can't be blank");
                }
                else if(username.equals("")){
                    input_password.setError("can't be blank");
                }
                else if(password.equals("")){
                    input_password.setError("can't be blank");
                }
                else if(role.equals("-- SELECT USER ROLE -- ")){
                   Toast.makeText(context, "Select user role..!", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = FIREBASE_URL+USERS+".json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase(FIREBASE_URL+USERS);

                            if(s.equals("null")) {
                                reference.child(username);

                                Map<String, String> map = new HashMap<String, String>();

                                map.put(FIRST_NAME, fname);
                                map.put(LAST_NAME, lname);
                                map.put(USERNAME, username);
                                map.put(PASSWORD, password);
                                map.put(USER_ROLE, role);
                                map.put(ONLINE, "online");
                                //map.put("photoUrl", "");
                                reference.child(username).push().setValue(map);

                                Toast.makeText(context, "registration successful", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);
                                    if (!obj.has(username)) {
                                        Map<String, String> map = new HashMap<String, String>();
                                        map.put(FIRST_NAME, fname);
                                        map.put(LAST_NAME, lname);
                                        map.put(USERNAME, username);
                                        map.put(PASSWORD, password);
                                        map.put(USER_ROLE, role);
                                        map.put(ONLINE, "online");

                                        reference.child(username).push().setValue(map);
                                        Toast.makeText(context, "registration successful", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(context, "username already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(context);
                    rQueue.add(request);
                }



            }
        });
        setView();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setView(){
        List<String> list = new ArrayList<>();
        list.add("-- SELECT USER ROLE -- ");
        list.add("Loan Officer");
        list.add("Member");
        try{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_spinner_dropdown_item,
                    list
            );
            spinner_role.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
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
}
