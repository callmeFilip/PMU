package com.example.rest_thread;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog prgDialog;

    TextView errorMsg;

    EditText nameET;

    EditText emailET;

    EditText pwdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find Error Msg Text View control by ID
        errorMsg = findViewById(R.id.register_error);

        // Find Name Edit View control by ID
        nameET = findViewById(R.id.registerName);

        // Find Email Edit View control by ID
        emailET = findViewById(R.id.registerEmail);

        // Find Password Edit View control by ID
        pwdET = findViewById(R.id.registerPassword);

        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);

        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");

        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }

    public void registerUser(View view) {
        // Retrieve values from input fields
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = pwdET.getText().toString();

        // Clear previous error message
        errorMsg.setText("");

        // Instantiate Http Request parameters object
        RequestParams params = new RequestParams();

        // Validate input fields
        if (Utility.isNotNull(name) && Utility.isNotNull(email) && Utility.isNotNull(password)) {
            // Check if Email is valid
            if (Utility.validate(email)) {
                // Add parameters to request
                params.put("name", name);
                params.put("username", email);
                params.put("password", password);
                // Invoke Web Service
                invokeWS(params);
            } else {
                errorMsg.setText("Please enter a valid email");
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else {
            errorMsg.setText("Please fill the form, don't leave any field blank");
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }
    }

    public void invokeWS(RequestParams params) {
        prgDialog.show(); // Show progress dialog

        // Create Async HTTP client
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("\n Android say: ", params.toString());

        // Send HTTP GET request to the server
        client.get("http://192.168.0.103:8080/useraccount/register/doregister", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide(); // Hide progress dialog
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));

                    // If registration is successful
                    if (obj.getBoolean("status")) {
                        setDefaultValues();
                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                    } else {
                        errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occurred [Server's JSON response might be invalid!]", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide(); // Hide progress dialog

                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error! [Check Internet or server availability]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Method to navigate from Register Activity to MainActivity (Login Activity)
     */
    public void navigatetoLoginActivity(View view){
        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
        // Clear activity history
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    /**
     * Set default values for Edit View controls
     */
    public void setDefaultValues(){
        nameET.setText("");
        emailET.setText("");
        pwdET.setText("");
    }

}