package com.example.usermanagersqllite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.usermanagersqllite.activities.WorkActivity;
import com.example.usermanagersqllite.models.Database;
import com.example.usermanagersqllite.models.User;

public class MainActivity extends AppCompatActivity {

    private EditText userNameInput, passwordInput, phoneInput, emailInput, addressInput, ageInput;
    private Button loginButton;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        userNameInput = findViewById(R.id.userNameBOX);
        passwordInput = findViewById(R.id.PasswordBOX);
        phoneInput = findViewById(R.id.phoneBOX);
        emailInput = findViewById(R.id.EmailBOX);
        addressInput = findViewById(R.id.AddressBox);
        ageInput = findViewById(R.id.ageBOX);
        loginButton = findViewById(R.id.LoginBUT);

        // Initialize database
        db = new Database(this);

        // Handle login button click
        loginButton.setOnClickListener(v -> {

            String username = userNameInput.getText().toString().trim();
            String password = User.hashPassword(passwordInput.getText().toString().trim());
            String phone = phoneInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String address = addressInput.getText().toString().trim();
            String ageStr = ageInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || ageStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageStr); // Convert Age from String to Integer

            // Check if user already exists
            User user = db.getUserByUsername(username);

            if (user == null) {
                // User doesn't exist, create a new user
                User newUser = new User(username, password, phone, email, address, age);
                db.addUser(newUser);
                Toast.makeText(MainActivity.this, "New User Created!", Toast.LENGTH_SHORT).show();

                // Retrieve the user again after creation
                user = db.getUserByUsername(username);
            }

            // Validate user credentials and log in
            if (user != null && user.getPassword().equals(password)) {
                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WorkActivity.class);
                intent.putExtra("USERNAME", user.getUserName());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

