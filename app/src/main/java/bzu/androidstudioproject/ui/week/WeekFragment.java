package bzu.androidstudioproject.ui.week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.Tasks.Task;
import bzu.androidstudioproject.TasksActivity;
import bzu.androidstudioproject.adapters.ToDoAdapter;
import bzu.androidstudioproject.databinding.FragmentTodayBinding;

public class WeekFragment extends Fragment {

    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today,
                container, false);
        ArrayList<Task> weekTasks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int DayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        for(Task task: TasksActivity.taskList)
        {
            Date date = task.getTime();
            calendar.setTime(date);
            int dayNumber = calendar.get(Calendar.DAY_OF_YEAR);
            if(dayNumber > DayOfYear && dayNumber <= dayNumber+7)
                weekTasks.add(task);
        }
        recyclerView = view.findViewById(R.id.tasksRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        TasksActivity.recyclerAdapter = new ToDoAdapter(view.getContext(), weekTasks);
        recyclerView.setAdapter(TasksActivity.recyclerAdapter);
        for(Task task: weekTasks)
        {
            if(task.getStatus() == false) {
                Toast.makeText(view.getContext(), "You still have tasks to do, go on!", Toast.LENGTH_LONG).show();
                return view;
            }
        }
        if(weekTasks.isEmpty())
        {
            Toast.makeText(view.getContext(), "You don't have tasks for next 7 days!", Toast.LENGTH_LONG).show();
            return view;
        }
        Toast.makeText(view.getContext(), "Congrats ! you have finished all the tasks for the next 7 days !", Toast.LENGTH_LONG).show();
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}