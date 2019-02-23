package com.shark.channel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.ChannelReader;
import com.meituan.android.walle.WalleChannelReader;

import java.io.File;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityPermissionsDispatcher.getChannelWithPermissionCheck(MainActivity.this);
            }
        });
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void getChannel() {
        EditText editText = findViewById(R.id.editText);
        TextView channelNameTextView = findViewById(R.id.channelNameTextView);
        String apkFileName = editText.getText().toString();

        File file = new File(Environment.getExternalStorageDirectory(), apkFileName);
        channelNameTextView.setText("未发现渠道名");
        ChannelInfo info = ChannelReader.get(new File(Environment.getExternalStorageDirectory(), apkFileName));
        if (!file.exists()) return;
        if (info == null) return;
        channelNameTextView.setText(ChannelReader.get(new File(Environment.getExternalStorageDirectory(), apkFileName)).getChannel());
    }
    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
