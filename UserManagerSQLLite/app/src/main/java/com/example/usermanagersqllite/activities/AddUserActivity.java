package com.example.usermanagersqllite.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usermanagersqllite.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.usermanagersqllite.models.Database;
import com.example.usermanagersqllite.models.User;

public class AddUserActivity extends AppCompatActivity {

    private EditText userNameInput, passwordInput;
    private Button addUserButton, clearButton, backButton;
    private Database db;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // Initialize UI components
        userNameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        addUserButton = findViewById(R.id.addUserButton);
        clearButton = findViewById(R.id.clearButton);
        backButton = findViewById(R.id.backButton);

        db = new Database(this);

        alertDialogBuilder = new AlertDialog.Builder(this);

        addUserButton.setOnClickListener(v -> {
            String username = userNameInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                db.addUser(new User(username, password));
                showDialog("User is added!");
            } else {
                showDialog("Both fields are required!");
            }
        });

        clearButton.setOnClickListener(v -> {
            userNameInput.setText("");
            passwordInput.setText("");
        });

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(AddUserActivity.this, WorkActivity.class));
            finish();
        });
    }

    public void showDialog(String message) {
        alertDialogBuilder.setMessage(message);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
