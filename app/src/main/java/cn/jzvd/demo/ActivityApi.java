package cn.jzvd.demo;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;

/**
 * Created by Nathen on 16/7/31.
 */
public class ActivityApi extends AppCompatActivity implements View.OnClickListener {
    Button mSmallChange, mBigChange, mOrientation, mExtendsNormalActivity, mRationAndVideoSize, mCustomMediaPlayer;
    JzvdStd mJzvdStd;
    Jzvd.JZAutoFullscreenListener mSensorEventListener;
    SensorManager mSensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("Api");
        setContentView(R.layout.activity_api);

        mSmallChange = findViewById(R.id.small_change);
        mBigChange = findViewById(R.id.big_change);
        mOrientation = findViewById(R.id.orientation);
        mExtendsNormalActivity = findViewById(R.id.extends_normal_activity);
        mRationAndVideoSize = findViewById(R.id.rotation_and_videosize);
        mCustomMediaPlayer = findViewById(R.id.custom_mediaplayer);

        mSmallChange.setOnClickListener(this);
        mBigChange.setOnClickListener(this);
        mOrientation.setOnClickListener(this);
        mExtendsNormalActivity.setOnClickListener(this);
        mRationAndVideoSize.setOnClickListener(this);
        mCustomMediaPlayer.setOnClickListener(this);


        mJzvdStd = findViewById(R.id.jz_video);
        LinkedHashMap map = new LinkedHashMap();

        String proxyUrl = ApplicationDemo.getProxy(this).getProxyUrl(VideoConstant.videoUrls[0][9]);

        map.put("高清", proxyUrl);
        map.put("标清", VideoConstant.videoUrls[0][6]);
        map.put("普清", VideoConstant.videoUrlList[0]);
        JZDataSource jzDataSource = new JZDataSource(map, "饺子不信");
        jzDataSource.looping = true;
        jzDataSource.currentUrlIndex = 2;
        jzDataSource.headerMap.put("key", "value");//header
        mJzvdStd.setUp(jzDataSource
                , JzvdStd.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load(VideoConstant.videoThumbList[0]).into(mJzvdStd.thumbImageView);
        mJzvdStd.seekToInAdvance = 20000;
        //JZVideoPlayer.SAVE_PROGRESS = false;

        /** Play video in local path, eg:record by system camera **/
//        cpAssertVideoToLocalPath();
//        mJzVideoPlayerStandard.setUp(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/local_video.mp4"
//                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子不信");
        /** ImageLoader **/
//        ImageLoader.getInstance().displayImage(VideoConstant.videoThumbs[0][1],
//                videoController1.thumbImageView);
        /** volley Fresco omit **/
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new Jzvd.JZAutoFullscreenListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.small_change:
                startActivity(new Intent(ActivityApi.this, ActivityApiUISmallChange.class));
                break;
            case R.id.big_change:
                Toast.makeText(ActivityApi.this, "Comming Soon", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(ActivityApi.this, ActivityApiUIBigChange.class));
                break;
            case R.id.orientation:
                startActivity(new Intent(ActivityApi.this, ActivityApiOrientation.class));
                break;
            case R.id.extends_normal_activity:
                startActivity(new Intent(ActivityApi.this, ActivityApiExtendsNormal.class));
                break;
            case R.id.rotation_and_videosize:
                startActivity(new Intent(ActivityApi.this, ActivityApiRotationVideoSize.class));
                break;
            case R.id.custom_mediaplayer:
                startActivity(new Intent(ActivityApi.this, ActivityApiCustomMediaPlayer.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //home back
        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        Jzvd.clearSavedProgress(this, null);
        //home back
        Jzvd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
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

    public void cpAssertVideoToLocalPath() {
        try {
            InputStream myInput;
            OutputStream myOutput = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/local_video.mp4");
            myInput = this.getAssets().open("local_video.mp4");
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }

            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
