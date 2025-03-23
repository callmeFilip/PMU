package com.example.usermanagersqllite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usermanagersqllite.R;
import com.example.usermanagersqllite.MainActivity;
import com.example.usermanagersqllite.models.Database;
import com.example.usermanagersqllite.models.User;

import java.util.List;


public class WorkActivity extends AppCompatActivity {

    private TextView textUsers;
    private Button logOut, showAllUsers, addUser, deleteUser, search;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        // Initialize UI components
        textUsers = findViewById(R.id.ShowView);
        logOut = findViewById(R.id.logout);
        showAllUsers = findViewById(R.id.show_users);
        addUser = findViewById(R.id.add_user);
        deleteUser = findViewById(R.id.delete_user);
        search = findViewById(R.id.search);

        // Initialize database
        db = new Database(this);

        final List<User> users = db.getAllUsers();


        addUser.setOnClickListener(v -> startActivity(new Intent(WorkActivity.this, AddUserActivity.class)));


        showAllUsers.setOnClickListener(v -> {
            textUsers.setText("");

            List<User> updatedUsers = db.getAllUsers();

            if (updatedUsers.isEmpty()) {
                textUsers.setText("No users found.");
            } else {
                StringBuilder usersText = new StringBuilder();

                for (User us : updatedUsers) {
                    usersText.append("Id: ").append(us.getID()).append(" , Name: ").append(us.getUserName()).append("\n");
                }

                textUsers.setText(usersText.toString());
            }
        });

        // Logout - Navigate back to MainActivity
        logOut.setOnClickListener(v -> {
            startActivity(new Intent(WorkActivity.this, MainActivity.class));
            finish();
        });

        deleteUser.setOnClickListener(v -> startActivity(new Intent(WorkActivity.this, DeleteUserActivity.class)));

        search.setOnClickListener(v -> startActivity(new Intent(WorkActivity.this, SearchUserActivity.class)));
    }
}