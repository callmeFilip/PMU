package com.example.usermanagersqllite.activities;

import android.content.Intent;
import com.example.usermanagersqllite.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.usermanagersqllite.models.Database;


public class DeleteUserActivity extends AppCompatActivity {

    private EditText deleteUserNameInput;
    private Button deleteUserButton;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        deleteUserNameInput = findViewById(R.id.deleteUserNameInput);
        deleteUserButton = findViewById(R.id.deleteUserButton);

        db = new Database(this);

        deleteUserButton.setOnClickListener(v -> {
            String username = deleteUserNameInput.getText().toString().trim();

            if (!username.isEmpty()) {
                boolean isDeleted = db.deleteUserByUsername(username);
                if (isDeleted) {
                    Toast.makeText(DeleteUserActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DeleteUserActivity.this, WorkActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DeleteUserActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DeleteUserActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
