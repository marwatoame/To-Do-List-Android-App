package bzu.androidstudioproject.ui.all;

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

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.Tasks.Task;
import bzu.androidstudioproject.TasksActivity;
import bzu.androidstudioproject.adapters.ToDoAdapter;
import bzu.androidstudioproject.databinding.FragmentTodayBinding;

public class AllFragment extends Fragment {

    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today,
                container, false);

        recyclerView = view.findViewById(R.id.tasksRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        TasksActivity.recyclerAdapter = new ToDoAdapter(view.getContext(), TasksActivity.taskList);
        recyclerView.setAdapter(TasksActivity.recyclerAdapter);
        for(Task task: TasksActivity.taskList)
        {
            if(task.getStatus() == false) {
                Toast.makeText(view.getContext(), "You still have tasks to do, go on!", Toast.LENGTH_LONG).show();
                return view;
            }
        }
        if(TasksActivity.taskList.isEmpty())
        {
            Toast.makeText(view.getContext(), "You don't have tasks!", Toast.LENGTH_LONG).show();
            return view;
        }
        Toast.makeText(view.getContext(), "Congrats ! you have finished all the tasks assigned to you !", Toast.LENGTH_LONG).show();
        return view;    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}