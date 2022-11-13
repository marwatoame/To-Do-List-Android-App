package bzu.androidstudioproject.registration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.TasksActivity;
import bzu.androidstudioproject.database.DataBaseHelper;
import bzu.androidstudioproject.loggedinuser.LoggedInUser;

public class Login extends AppCompatActivity {

    Button login ;
    EditText email ;
    EditText password ;
    TextView signup ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.cirLoginButton);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        signup  = findViewById(R.id.signup_noacc);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Empty Field! Please fill all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(email.getText().toString().isEmpty())){
                    if(!(password.getText().toString().isEmpty())) {
                        try {
                            validUser(email.getText().toString(), password.getText().toString());
                        } catch (Exception e) {
                            Log.d("error", e.getMessage());
                            Toast.makeText(Login.this, "Enter Correct the email/password !", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSignup();
            }
        });
    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, TasksActivity.class);
        startActivity(switchActivityIntent);
    }
    private void switchToSignup(){
        Intent switchActivityIntent = new Intent(this, Register.class);
        startActivity(switchActivityIntent);
    }
    private void validUser(String userName, String userPassword) throws Exception {

        DataBaseHelper dbh = new DataBaseHelper(Login.this, "tasks_db", null, 1);
        User user = new User(userName,userPassword);
        Cursor temp = dbh.getUser(user);

        Log.d("input: ",userName + "\n" + userPassword);

        if(temp!=null) {
            String string = cursor2String(temp);
            String[] array = string.split(",");
            Log.d("result: ", array[2] + "\t" + array[3]);
            LoggedInUser.setLoggedInUser(user);
            switchActivities();
            Toast.makeText(Login.this, "Login Succeeded !", Toast.LENGTH_SHORT).show();
        }
        else {
                Toast.makeText(Login.this, "Enter Correct the email/password !", Toast.LENGTH_LONG).show();
                return;
            }
        }
    @SuppressLint("Range")
    public String cursor2String(Cursor cursor){
        String strCursor = "";
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();

            for (String name: columnNames) {
                strCursor += String.format("%s,", cursor.getString(cursor.getColumnIndex(name)));
            }
        }
        return strCursor;
    }
}