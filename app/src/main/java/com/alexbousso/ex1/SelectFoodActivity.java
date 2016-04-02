package com.alexbousso.ex1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedHashMap;

public class SelectFoodActivity extends AppCompatActivity {
    public static final String STRING_INTENT_RESPONSE_TAG =
            "SelectFoodActivity.STRING_INTENT_RESPONSE_TAG";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);

        // Changing the title of the activity
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            String orderSentStr = getResources().getString(R.string.SelectFoodActivityTitle);
            actionBar.setTitle(orderSentStr);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        initializeComponents();
    }

    private void initializeComponents() {
        listView = (ListView) findViewById(R.id.listview);

        initializeListView();
    }

    private void initializeListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) findViewById(R.id.item_text);
                ComponentName caller = getCallingActivity();
                try {
                    if (textView != null && caller != null) {
                        Class activity = Class.forName(caller.getClassName());
                        Intent result = new Intent(view.getContext(), activity);
                        result.putExtra(STRING_INTENT_RESPONSE_TAG, textView.getText());
                        setResult(RESULT_OK, result);
                        finish();
                    }
                } catch (Exception e) {
                    Log.e("SelectFoodActivity", "Exception caught: " + e.getMessage());
                }
            }
        });

        String[] values = getResources().getStringArray(R.array.foodArray);
        int[] imgs = {
                R.mipmap.bananas,
                R.mipmap.apple,
                R.mipmap.cherry,
                R.mipmap.orange,
                R.mipmap.steak,
        };
        FoodItemContent[] items = new FoodItemContent[values.length];
        for (int i = 0; i < values.length; i++) {
            items[i] = new FoodItemContent(values[i], imgs[i]);
        }
        ImageTextArrayAdapter adapter = new ImageTextArrayAdapter(this, items);
        listView.setAdapter(adapter);
    }
}
