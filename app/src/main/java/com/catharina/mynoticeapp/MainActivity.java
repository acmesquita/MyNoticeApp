package com.catharina.mynoticeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.catharina.mynoticeapp.adapter.NoticeAdapter;
import com.catharina.mynoticeapp.com.github.kevinsawicki.http.HttpRequest;
import com.catharina.mynoticeapp.modelo.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notices App");

        recyclerView = (RecyclerView) findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        atualizar(null);
        Intent intent = new Intent(this, NoticeService.class);
        startService(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void atualizar(MenuItem item) {
        new NoticeTask(recyclerView, adapter, this).execute();
    }

    public void stopService(MenuItem item) {
        Intent intent = new Intent(this, NoticeService.class);
        stopService(intent);
    }
}
