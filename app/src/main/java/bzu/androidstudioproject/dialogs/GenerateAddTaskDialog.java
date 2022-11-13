package bzu.androidstudioproject.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.Tasks.Task;
import bzu.androidstudioproject.TasksActivity;
import bzu.androidstudioproject.adapters.ToDoAdapter;
import bzu.androidstudioproject.database.DataBaseHelper;
import bzu.androidstudioproject.loggedinuser.LoggedInUser;

public class GenerateAddTaskDialog extends Dialog {
    public GenerateAddTaskDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar);
        setContentView(R.layout.add_dialog);

        EditText title = (EditText) findViewById(R.id.title);
        EditText description = (EditText) findViewById(R.id.description);
        EditText date = (EditText) findViewById(R.id.date);
        Button add = (Button) findViewById(R.id.add);
        Button cancel = (Button) findViewById(R.id.cancel);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "tasks_db", null, 1);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString() == null || title.getText().toString() == "")
                {
                    Toast.makeText(context, "Title must be filled !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(title.getText().toString() == null || title.getText().toString() == "")
                {
                    Toast.makeText(context, "Invalid Date format !", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(context, "Invalid Date format !", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    dataBaseHelper.addNewTask(title.getText().toString(), description.getText().toString(), date.getText().toString());
                    TasksActivity.taskList.add(new Task(TasksActivity.taskList.get(TasksActivity.taskList.size()-1).getId()+1, title.getText().toString(), description.getText().toString(), new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date.getText().toString()), LoggedInUser.loggedIn.getEmail()));
                    Toast.makeText(context, "Succeeded !", Toast.LENGTH_SHORT).show();
                    dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

}
