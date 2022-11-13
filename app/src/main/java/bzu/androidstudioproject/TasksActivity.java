package bzu.androidstudioproject;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;


import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SearchView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bzu.androidstudioproject.Tasks.Task;
import bzu.androidstudioproject.adapters.ToDoAdapter;
import bzu.androidstudioproject.database.DataBaseHelper;
import bzu.androidstudioproject.databinding.ActivityTasksBinding;
import bzu.androidstudioproject.dialogs.GenerateAddTaskDialog;
import bzu.androidstudioproject.loggedinuser.LoggedInUser;
import bzu.androidstudioproject.registration.Login;

public class TasksActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTasksBinding binding;
    public static ArrayList<Task> taskList;
    public static ToDoAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readData();

        binding = ActivityTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTasks.toolbar);
        GenerateAddTaskDialog generateAddTaskDialog = new GenerateAddTaskDialog(this);
        binding.appBarTasks.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAddTaskDialog.setCanceledOnTouchOutside(true);
                generateAddTaskDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                generateAddTaskDialog.getWindow().setLayout(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                generateAddTaskDialog.show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_today, R.id.nav_week, R.id.nav_all, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tasks);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tasks, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && newText != "")
                {
                    recyclerAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        return true;
    }
    public void LogOut()
    {

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tasks);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.action_log_out:
                taskList = null;
                mAppBarConfiguration = null;
                LoggedInUser.setLoggedInUser(null);
                binding = null;
                recyclerAdapter = null;
                Intent switchActivityIntent = new Intent(TasksActivity.this, Login.class);
                startActivity(switchActivityIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void readData() {
        try {
            taskList = new ArrayList<>();
            DataBaseHelper dataBaseHelper = new DataBaseHelper(TasksActivity.this, "tasks_db", null, 1);
            Cursor cursor = dataBaseHelper.getAllTasks();
            if (cursor.getCount() > 0) {
                int idIndex = cursor.getColumnIndex("ID");
                int titleIndex = cursor.getColumnIndex("Title");
                int descriptionIndex = cursor.getColumnIndex("Description");
                int dateIndex = cursor.getColumnIndex("Date");
                int emailIndex = cursor.getColumnIndex("Email");
                int statusIndex = cursor.getColumnIndex("Status");
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    int Id = cursor.getInt(idIndex);
                    String Title = cursor.getString(titleIndex);
                    String Description = cursor.getString(descriptionIndex);
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(cursor.getString(dateIndex));
                    String Email = cursor.getString(emailIndex);
                    int Status = cursor.getInt(statusIndex);
                    Task task = new Task(Id, Title, Description, date, Email);
                    if (Status > 0)
                        task.setStatus(true);
                    else
                        task.setStatus(false);
                    taskList.add(task);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}