package bzu.androidstudioproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.Tasks.Task;
import bzu.androidstudioproject.database.DataBaseHelper;
import bzu.androidstudioproject.registration.Login;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> implements Filterable {
    private Context context;
    public static ArrayList<Task> taskModelList;
    public static ArrayList<Task> itemsModelListFiltered;
    private ViewGroup parent;
    DataBaseHelper dataBaseHelper;
    public ToDoAdapter(Context context, ArrayList<Task> taskModelList)
    {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context, "tasks_db", null, 1);
        this.taskModelList = taskModelList;
        this.itemsModelListFiltered = taskModelList;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {

        Task model = taskModelList.get(position);
        holder.todoCheckBox.setText(model.getTitle() + "\n" + model.getDescription() +"\n" + new SimpleDateFormat("yyyy-MMMM-dd hh:mm").format(model.getTime()));
        if(model.getStatus() == true)
        {
            holder.todoCheckBox.setChecked(true);
        }
        holder.todoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dataBaseHelper.setStatus(model);
                    model.setStatus(!model.getStatus());
                    Log.d("Status changed: ", model.getTitle()+" from "+Boolean.parseBoolean(String.valueOf(!model.getStatus())) +" to "+Boolean.parseBoolean(String.valueOf(model.getStatus())));
            }
        });
        holder.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent gmailIntent =new Intent();
                    gmailIntent.setAction(Intent.ACTION_SENDTO);
                    gmailIntent.setType("message/rfc822");
                    gmailIntent.setData(Uri.parse("mailto:"));
                    gmailIntent.putExtra(Intent.EXTRA_EMAIL,model.getEmail());
                    gmailIntent.putExtra(Intent.EXTRA_SUBJECT,model.getTitle());
                    gmailIntent.putExtra(Intent.EXTRA_TEXT,model.getDescription());
                    context.startActivity(gmailIntent);
                    }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Removing: ", model.getTitle()+" Id "+model.getId());
                dataBaseHelper.deleteTask(model);
                itemsModelListFiltered.remove(model);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.taskModelList.size();
    }
        // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox todoCheckBox;
        private ImageButton deleteButton;
        private ImageButton emailButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoCheckBox = itemView.findViewById(R.id.todoCheckBox);
            deleteButton = itemView.findViewById(R.id.delete);
            emailButton = itemView.findViewById(R.id.email);
        }
    }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    taskModelList = itemsModelListFiltered;
                    filterResults.count = taskModelList.size();
                    filterResults.values = taskModelList;

                }else{
                    taskModelList = itemsModelListFiltered;
                    ArrayList<Task> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Task itemsModel: taskModelList){
                        if(itemsModel.getTitle().toLowerCase().contains(searchStr.toLowerCase())
                                || itemsModel.getDescription().toLowerCase().contains(searchStr.toLowerCase())
                        ){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                taskModelList = (ArrayList<Task>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}

