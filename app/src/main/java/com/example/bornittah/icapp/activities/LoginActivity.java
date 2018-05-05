package com.example.bornittah.icapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bornittah.icapp.R;
import com.example.bornittah.icapp.util.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.bornittah.icapp.util.Constants.config.FIREBASE_URL;
import static com.example.bornittah.icapp.util.Constants.config.PASSWORD;
import static com.example.bornittah.icapp.util.Constants.config.USERS;
import static com.example.bornittah.icapp.util.Constants.config.USER_ROLE;

/**
 * Created by Bornittah on 5/5/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private TextView text_create;
    private Context context= this;
    private EditText input_username, input_password;
    private static final String TAG = "LoginActivity";
    String username = "";
    String password = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        text_create = (TextView) findViewById(R.id.text_create);
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = input_username.getText().toString().trim();
                password = input_password.getText().toString().trim();

                if (username.equals("")) {
                    input_username.setError("Empty entry..!");
                } else if (password.equals("")) {
                    input_password.setError("Empty entry..!");
                } else {
                    String url = FIREBASE_URL + USERS + ".json";
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.setMessage("Loading...");
                    pd.show();


                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            if (s.equals("null")) {
                                Toast.makeText(context, "user not found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    final JSONObject obj = new JSONObject(s);
                                    Log.e(TAG, obj + "");
                                    if (!obj.has(username)) {
                                        Toast.makeText(context, "user not found", Toast.LENGTH_LONG).show();
                                    } else {
                                        String url = FIREBASE_URL + USERS + "/" + username + ".json";
                                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                doOnSuccess(s);
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                System.out.println("" + volleyError);
                                            }
                                        });

                                        RequestQueue rQueue = Volley.newRequestQueue(context);
                                        rQueue.add(request);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });
                    RequestQueue rQueue = Volley.newRequestQueue(context);
                    rQueue.add(request);
                }
            }
        });
        text_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SignupActivity.class));
            }
        });
    }
    public void doOnSuccess(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()) {
                key = i.next().toString();
                if(obj.getJSONObject(key).getString(PASSWORD).equals(password)){
                    UserDetails.username = username;
                    UserDetails.password = password;
                    //sendToken(UserDetails.username);
                    String user_type = obj.getJSONObject(key).getString(USER_ROLE);
                    startActivity(new Intent(context, MainPageActivity.class));
                }else {
                    Toast.makeText(context, "incorrect password", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
