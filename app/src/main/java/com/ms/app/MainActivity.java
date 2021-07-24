package com.ms.app;

import android.Manifest;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.oaid.OaidClient;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<MyAdapter.Item> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        XXPermissions.with(this).permission(Manifest.permission.READ_PHONE_STATE).request(new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean all) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                OaidClient.Info info = new OaidClient(MainActivity.this, 5, TimeUnit.SECONDS).fetch();
                if (info != null) {
                    String id = info.getId();
                    if (id != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                datas.add(new MyAdapter.Item("oaid", id));
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AdvertisingIdClient.AdInfo advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(MainActivity.this);
                    String id = advertisingIdInfo.getId();
                    if (id != null) {
                        datas.add(new MyAdapter.Item("ad_vertising_id", id));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                datas.add(new MyAdapter.Item("android_id", Settings.System.getString(getContentResolver(), "android_id")));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}