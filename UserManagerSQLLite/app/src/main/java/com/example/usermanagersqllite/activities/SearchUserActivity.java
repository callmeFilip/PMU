package com.example.usermanagersqllite.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usermanagersqllite.R;
import com.example.usermanagersqllite.models.Database;
import com.example.usermanagersqllite.models.User;

public class SearchUserActivity extends AppCompatActivity {

    private EditText searchUserNameInput;
    private Button searchUserButton;
    private TextView userDetails;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchUserNameInput = findViewById(R.id.searchUserNameInput);
        searchUserButton = findViewById(R.id.searchUserButton);
        userDetails = findViewById(R.id.userDetails);

        db = new Database(this);

        searchUserButton.setOnClickListener(v -> {
            String username = searchUserNameInput.getText().toString().trim();

            if (!username.isEmpty()) {
                User user = db.getUserByUsername(username);
                if (user != null) {
                    userDetails.setText("ID: " + user.getID() +
                            "\nName: " + user.getUserName() +
                            "\nPassword: " + user.getPassword() +
                            "\nPhone: " + user.getPhoneNumber() +
                            "\nEmail: " + user.getEmail() +
                            "\nAddress: " + user.getAddress() +
                            "\nAge: " + user.getAge());
                } else {
                    userDetails.setText("");
                    Toast.makeText(SearchUserActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SearchUserActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
