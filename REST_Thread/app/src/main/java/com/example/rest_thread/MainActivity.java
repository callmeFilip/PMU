package com.example.rest_thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Create Dialog Object
    ProgressDialog prgDialog;

    TextView errorMsg;
    // Name Edit View Object
    EditText nameET;
    // Email Edit View Object
    EditText emailET;
    // Password Edit View Object
    EditText pwdET;

    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMsg = findViewById(R.id.login_error);

        emailET = findViewById(R.id.loginEmail);

        pwdET = findViewById(R.id.loginPassword);

        btnRegister = findViewById(R.id.btnLinkToRegisterScreen);

        prgDialog = new ProgressDialog(this);

        prgDialog.setMessage("Please wait...");

        prgDialog.setCancelable(false);

        btnRegister.setOnClickListener(this::navigatetoRegisterActivity);
    }

    /**
     * Method triggered when the Login button is clicked
     *
     * @param view
     */
    public void loginUser(View view) {
        String email = emailET.getText().toString();
        String password = pwdET.getText().toString();
        RequestParams params = new RequestParams();

        if (Utility.isNotNull(email) && Utility.isNotNull(password)) {

            if (Utility.validate(email)) {
                params.put("username", email);
                params.put("password", password);

                invokeWS(params);
            }

            else {
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }
    }

    public void invokeWS(RequestParams params) {
        prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("\n Android say: ", params.toString());

        client.get("http://10.0.2.2:8080/useraccount/login/dologin", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();

                try {
                    JSONObject obj = new JSONObject(new String(responseBody));

                    if (obj.getBoolean("status")) {
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();

                        navigatetoHomeActivity();
                    } else {
                        errorMsg.setText(obj.getString("error_msg"));

                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid!]", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();

                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void navigatetoHomeActivity() {
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(homeIntent);
    }

    public void navigatetoRegisterActivity(View view) {
        Intent loginIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(loginIntent);
    }
}
