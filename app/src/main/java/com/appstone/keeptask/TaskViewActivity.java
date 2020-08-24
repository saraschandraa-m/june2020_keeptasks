package com.appstone.keeptask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class TaskViewActivity extends AppCompatActivity implements TaskListAdapter.TaskItemClickListener {

    private RecyclerView mRcTasks;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);


        mRcTasks = findViewById(R.id.rc_tasks);
        mRcTasks.setLayoutManager(new GridLayoutManager(TaskViewActivity.this, 2));

        dbHelper = new DBHelper(TaskViewActivity.this);

        getDataFromDatabase();
    }


    private void getDataFromDatabase() {
        ArrayList<Task> tasks = dbHelper.getTasksFromDB(dbHelper.getReadableDatabase());

        TaskListAdapter adapter = new TaskListAdapter(TaskViewActivity.this, tasks);
        adapter.setListener(this);
        mRcTasks.setAdapter(adapter);
    }

    public void onAddNewTaskClicked(View view) {
        startActivityForResult(new Intent(TaskViewActivity.this, AddTaskActivity.class), 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            getDataFromDatabase();
        }
    }

    @Override
    public void onTaskItemClicked(Items item, boolean checkedValue, Task task) {
        ArrayList<Items> taskItems = Task.convertStringtoArrayList(task.taskItemString);

        for (Items existingItem : taskItems) {
            if (existingItem.itemID == item.itemID) {
                existingItem.isItemChecked = checkedValue;
            }
        }

        String editedTaskItems = Task.convertArrayListToJSONArrayString(taskItems);

        Task editedTask = new Task();
        editedTask.taskID = task.taskID;
        editedTask.taskTitle = task.taskTitle;
        editedTask.taskItemString = editedTaskItems;

        dbHelper.updateDataToDatabse(dbHelper.getWritableDatabase(), editedTask);

        getDataFromDatabase();
    }
}