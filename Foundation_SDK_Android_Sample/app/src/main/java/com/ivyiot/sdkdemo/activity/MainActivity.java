package com.ivyiot.sdkdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivyiot.sdkdemo.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mFoscamIpc;
    private Button mIvyIpc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFoscamIpc = findViewById(R.id.bt_foscam_ipc);
        mIvyIpc = findViewById(R.id.bt_ivy_ipc);

        mFoscamIpc.setOnClickListener(this);
        mIvyIpc.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_foscam_ipc:
                startActivity(new Intent(this, FoscamIpcActivity.class));
                break;
            case R.id.bt_ivy_ipc:
                startActivity(new Intent(this, IvyIpcActivity.class));

                break;

            default:
                break;
        }

    }


}
