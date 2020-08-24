package com.appstone.keeptask;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListHolder> {


    private Context context;
    private ArrayList<Task> tasks;
    private TaskItemClickListener listener;

    public TaskListAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    public void setListener(TaskItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskListHolder(LayoutInflater.from(context).inflate(R.layout.cell_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListHolder holder, int position) {
        final Task currentTask = tasks.get(position);

        holder.mTvTaskTitle.setText(currentTask.taskTitle);

        ArrayList<Items> taskItems = Task.convertStringtoArrayList(currentTask.taskItemString);


        holder.mLlTaskItems.removeAllViews();
        for (final Items item : taskItems) {
            View view = LayoutInflater.from(context).inflate(R.layout.cell_view_items, null);

            CheckBox mChItem = view.findViewById(R.id.ch_view_item);
            TextView mTvItem = view.findViewById(R.id.tv_view_item);

            mTvItem.setText(item.itemName);
            mChItem.setChecked(item.isItemChecked);

            if (item.isItemChecked) {
                mTvItem.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
            }

            holder.mLlTaskItems.addView(view);

            mChItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (listener != null) {
                        listener.onTaskItemClicked(item, b, currentTask);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskListHolder extends RecyclerView.ViewHolder {


        private TextView mTvTaskTitle;
        private LinearLayout mLlTaskItems;

        public TaskListHolder(@NonNull View itemView) {
            super(itemView);

            mTvTaskTitle = itemView.findViewById(R.id.tv_task_title);
            mLlTaskItems = itemView.findViewById(R.id.ll_view_items);
        }
    }

    public interface TaskItemClickListener {
        void onTaskItemClicked(Items item, boolean checkedValue, Task task);
    }
}
