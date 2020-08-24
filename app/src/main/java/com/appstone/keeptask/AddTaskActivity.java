package com.appstone.keeptask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {


    private EditText mEtTaskTitle;
    private LinearLayout mLlListItems;
    private LinearLayout mLlAddListItem;
    private Button mBtnAddTask;

    private int itemIDValue = 0;


    private ArrayList<Items> items;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mEtTaskTitle = findViewById(R.id.et_task_title);
        mLlListItems = findViewById(R.id.ll_list_items);
        mLlAddListItem = findViewById(R.id.ll_add_item);

        mBtnAddTask = findViewById(R.id.btn_add_task);


        dbHelper = new DBHelper(AddTaskActivity.this);

        items = new ArrayList<>();
    }


    public void onAddItemListClicked(View view) {

        mBtnAddTask.setEnabled(false);
        mLlAddListItem.setEnabled(false);
        mBtnAddTask.setAlpha(0.5f);
        mLlAddListItem.setAlpha(0.5f);

        itemIDValue++;

        View view1 = LayoutInflater.from(AddTaskActivity.this).inflate(R.layout.cell_insert_item, null);

        final EditText mEtItem = view1.findViewById(R.id.et_insert_item);
        final ImageView mIvDone = view1.findViewById(R.id.iv_insert_done);

        mIvDone.setVisibility(View.GONE);


        mEtItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    mIvDone.setVisibility(View.VISIBLE);
                } else {
                    mIvDone.setVisibility(View.GONE);
                }
            }
        });

        mIvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBtnAddTask.setEnabled(true);
                mLlAddListItem.setEnabled(true);
                mBtnAddTask.setAlpha(1.0f);
                mLlAddListItem.setAlpha(1.0f);

                Items newItem = new Items();
                newItem.itemID = itemIDValue;
                newItem.itemName = mEtItem.getText().toString();


                items.add(newItem);
            }
        });


        mLlListItems.addView(view1);

    }

    public void onAddTaskClicked(View view) {
        String taskTitle = mEtTaskTitle.getText().toString();

        if (!taskTitle.isEmpty() && items.size() > 0) {
            Task task = new Task();
            task.taskTitle = taskTitle;
            task.taskItemString = Task.convertArrayListToJSONArrayString(items);
            dbHelper.insertDataToDatabase(dbHelper.getWritableDatabase(), task);
            setResult(Activity.RESULT_OK);
            finish();
            //Show alert or toast.
        }


    }
}