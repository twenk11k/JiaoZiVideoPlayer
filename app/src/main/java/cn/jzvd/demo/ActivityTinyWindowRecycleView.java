package cn.jzvd.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Nathen on 2017/11/1.
 */

public class ActivityTinyWindowRecycleView extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterRecyclerViewVideo adapterVideoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("RecyclerViewTinyWindow");
        setContentView(R.layout.activity_recyclerview_content);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterVideoList = new AdapterRecyclerViewVideo(this);
        recyclerView.setAdapter(adapterVideoList);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                Jzvd.onChildViewAttachedToWindow(view, R.id.videoplayer);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd.onChildViewDetachedFromWindow(view);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
