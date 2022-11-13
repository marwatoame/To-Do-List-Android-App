package bzu.androidstudioproject.registration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.database.DataBaseHelper;

public class Register extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etRepeatPassword;
    TextView notification;
    Button signup;
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewInitializations();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp(view);
            }
        });
        etFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFirstName.setHint("");
            }
        });
        etLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etLastName.setHint("");
            }
        });
        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.setHint("");
            }
        });
        etPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPassword.setHint("");
            }
        });
        etRepeatPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etRepeatPassword.setHint("");
            }
        });
    }

    void viewInitializations() {
        signup = findViewById(R.id.bt_register);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etRepeatPassword = findViewById(R.id.et_repeat_password);
        notification = findViewById(R.id.notification);
        notification.setVisibility(View.INVISIBLE);
        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Checking if the input in form is valid
    boolean validateInput() {
        notification.setVisibility(View.INVISIBLE);
        notification.setTextColor(Color.RED);
        notification.setText("");
        if (etFirstName.getText().toString().equals("")) {
            etFirstName.setError("Please Enter First Name");
            return false;
        }
        if (etLastName.getText().toString().equals("")) {
            etLastName.setError("Please Enter Last Name");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Please Enter Password");
            return false;
        }
        if (etRepeatPassword.getText().toString().equals("")) {
            etRepeatPassword.setError("Please Enter Repeat Password");
            return false;
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            notification.setVisibility(View.VISIBLE);
            notification.setText("Please Enter Valid Email");
            notification.setTextColor(Color.RED);
            return false;
        }

        // checking minimum password Length
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            notification.setVisibility(View.VISIBLE);
            notification.setText("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            notification.setTextColor(Color.RED);
            return false;
        }

        // Checking if repeat password is same
        if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            notification.setVisibility(View.VISIBLE);
            notification.setText("Password does not match!");
            notification.setTextColor(Color.RED);
            return false;
        }

        if(etFirstName.getText().length() < 3)
        {
            notification.setVisibility(View.VISIBLE);
            notification.setText("First name is invalid!");
            notification.setTextColor(Color.RED);
            return false;
        }
        if(etLastName.getText().length() < 3)
        {
            notification.setVisibility(View.VISIBLE);
            notification.setText("Last name is invalid!");
            notification.setTextColor(Color.RED);
            return false;
        }

        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performSignUp (View v) {
        if (validateInput()) {

            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String repeatPassword = etRepeatPassword.getText().toString();

            Toast.makeText(this,"SignUp Success",Toast.LENGTH_SHORT).show();
            DataBaseHelper dataBaseHelper = new DataBaseHelper(Register.this, "tasks_db", null, 1);
            dataBaseHelper.addUser(firstName, lastName, email, password);
            Intent switchActivityIntent = new Intent(this, Login.class);
            startActivity(switchActivityIntent);
        }
        else
        {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(Register.this, "tasks_db", null, 1);
        }
    }

}