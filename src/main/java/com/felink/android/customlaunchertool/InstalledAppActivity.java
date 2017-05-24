package com.felink.android.customlaunchertool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.felink.android.customlaunchertool.adapter.InstalledAppAdapter;
import com.felink.android.customlaunchertool.config.Global;
import com.felink.android.customlaunchertool.kitset.common.ThreadUtil;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月19日 19:33.</br>
 * @update: </br>
 */

public class InstalledAppActivity extends AppCompatActivity {

    private ListView mList;
    private InstalledAppAdapter mAdapter;

    private ProgressDialog mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed);
        init();
    }

    private void init() {
        mList = (ListView) findViewById(R.id.list_installed);
        mAdapter = new InstalledAppAdapter(getPackageManager());
        mList.setAdapter(mAdapter);

        mLoading = new ProgressDialog(this);
        mLoading.setMessage("加载中...");
        mLoading.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLoading.show();
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                mAdapter.refresh();
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.dismiss();
                    }
                });
            }
        });
    }
}
