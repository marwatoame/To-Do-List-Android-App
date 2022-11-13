package bzu.androidstudioproject.ui.today;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.Tasks.Task;
import bzu.androidstudioproject.TasksActivity;
import bzu.androidstudioproject.adapters.ToDoAdapter;
import bzu.androidstudioproject.databinding.FragmentTodayBinding;
import bzu.androidstudioproject.registration.Login;

public class TodayFragment extends Fragment {
    private ToDoAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today,
                container, false);

        ArrayList<Task> TodayTasks = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        for(Task task: TasksActivity.taskList)
        {
            String taskDate = new SimpleDateFormat("yyyy-MM-dd").format(task.getTime());
            if(taskDate.equals(timeStamp))
                TodayTasks.add(task);
        }
        recyclerView = view.findViewById(R.id.tasksRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        TasksActivity.recyclerAdapter = new ToDoAdapter(view.getContext(), TodayTasks);
        recyclerView.setAdapter(TasksActivity.recyclerAdapter);
        for(Task task: TodayTasks)
        {
            if(task.getStatus() == false) {
                Toast.makeText(view.getContext(), "You still have tasks to do, go on!", Toast.LENGTH_LONG).show();
                return view;
            }
        }
        if(TodayTasks.isEmpty())
        {
            Toast.makeText(view.getContext(), "You don't have tasks for today!", Toast.LENGTH_LONG).show();
            return view;
        }
        Toast.makeText(view.getContext(), "Congrats ! you have finished all the tasks for today !", Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}