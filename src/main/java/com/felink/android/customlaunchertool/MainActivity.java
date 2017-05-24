package com.felink.android.customlaunchertool;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.felink.android.customlaunchertool.config.Global;
import com.felink.android.customlaunchertool.kitset.BaseConfig;
import com.felink.android.customlaunchertool.kitset.ConfigSettings;
import com.felink.android.customlaunchertool.kitset.Exporter;
import com.felink.android.customlaunchertool.kitset.common.AndroidPackageUtils;
import com.felink.android.customlaunchertool.kitset.common.ThreadUtil;
import com.felink.android.customlaunchertool.kitset.layout.ExportLayoutCompat;
import com.felink.android.customlaunchertool.kitset.permission.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    ProgressDialog loading;
    @Bind(R.id.tv_console)
    TextView tvConsole;
    @Bind(R.id.toggle_apply_original)
    ToggleButton toggleApplyOriginal;
    @Bind(R.id.toggle_apply_search_widget)
    ToggleButton toggleApplySearchWidget;
    @Bind(R.id.toggle_weather_widget_transparent)
    ToggleButton toggleWeatherWidgetTransparent;
    @Bind(R.id.scroll_main)
    ScrollView scrollMain;
    @Bind(R.id.btn_export_layout)
    Button btnExportLayout;
    @Bind(R.id.edit_baidu_channel)
    EditText editBaiduChannel;
    @Bind(R.id.edit_home_url)
    EditText editHomeUrl;
    @Bind(R.id.toggle_on_zero_screen)
    ToggleButton toggleOnZeroScreen;
    @Bind(R.id.tv_title_home_url)
    TextView tvTitleHomeUrl;
    @Bind(R.id.btn_goto_settings)
    Button btnGotoSettings;
    @Bind(R.id.toggle_replace_theme)
    ToggleButton toggleReplaceTheme;

    private Exporter util;

    private SimpleDateFormat dateFormat;
    private AlertDialog mPrompt;

    public volatile static StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        checkPermission();
    }

    private void init() {
        mPrompt = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("为了不影响使用，请在使用前授予该工具所需的所有权限")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent jumpToSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        jumpToSettings.setData(Uri.parse("package:" + Global.getApplicationContext().getPackageName()));
                        startActivity(jumpToSettings);
                    }
                })
                .create();
        mPrompt.setTitle("提示");
        util = new Exporter(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        loading = new ProgressDialog(this);
        loading.setMessage("正在导出配置...");
        loading.setCancelable(false);

        tvConsole.setText(sb.toString());

        toggleApplyOriginal.setChecked(false);

        writeConsole("当前匹配机型：" + ExportLayoutCompat.phoneType());

        tvConsole.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent jumpToList = new Intent();
                jumpToList.setClass(Global.getApplicationContext(), InstalledAppActivity.class);
                jumpToList.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Global.getApplicationContext().startActivity(jumpToList);
                return false;
            }
        });

        toggleApplyOriginal.setChecked(ConfigSettings.isAppliedOriginal);
        toggleApplySearchWidget.setChecked(ConfigSettings.isAppliedSearchWidget);
        toggleOnZeroScreen.setChecked(ConfigSettings.isOpenNavi);
        toggleWeatherWidgetTransparent.setChecked(ConfigSettings.isWeatcherWidgetBGTransparent);
        toggleReplaceTheme.setChecked(ConfigSettings.isReplaceTheme);
    }

    private void writeConsole(String msg) {
        sb.append(msg);
        sb.append("\n");

        tvConsole.setText(sb.toString());
    }

    @OnClick({R.id.btn_export_layout, R.id.btn_goto_settings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_export_layout:
                loading.show();
                writeConsole("正在导出..." + dateFormat.format(new Date()));
                ConfigSettings.baiduChannel = editBaiduChannel.getText() == null ? editBaiduChannel.getHint().toString() :
                        (TextUtils.isEmpty(editBaiduChannel.getText().toString().trim()) ? editBaiduChannel.getHint().toString() : editBaiduChannel.getText().toString());

                ConfigSettings.baiduHomeUrl = editHomeUrl.getText() == null ? editHomeUrl.getHint().toString() :
                        (TextUtils.isEmpty(editHomeUrl.getText().toString().trim()) ? editHomeUrl.getHint().toString() : editHomeUrl.getText().toString());

                ThreadUtil.executeMore(new Runnable() {
                    @Override
                    public void run() {
                        util.exportLayout();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.dismiss();
                                writeConsole("导出成功！" + "[" + Exporter.EXPORT_XML_PATH + Exporter.EXPORT_XML_NAME + "]");
                                Toast.makeText(MainActivity.this, "导出成功！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case R.id.btn_goto_settings:
                if (!AndroidPackageUtils.isPkgInstalled(this, BaseConfig.PKG_NAME)) {
                    Toast.makeText(this, "尚未安装“智桌面”", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent jumpToSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                jumpToSettings.setData(Uri.parse("package:" + BaseConfig.PKG_NAME));
                startActivity(jumpToSettings);
                break;
        }
    }

    @OnCheckedChanged({R.id.toggle_apply_original, R.id.toggle_apply_search_widget, R.id.toggle_weather_widget_transparent, R.id.toggle_on_zero_screen, R.id.toggle_replace_theme})
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.toggle_apply_original:
                ConfigSettings.isAppliedOriginal = (isChecked);
                break;
            case R.id.toggle_apply_search_widget:
                ConfigSettings.isAppliedSearchWidget = isChecked;
                break;
            case R.id.toggle_weather_widget_transparent:
                ConfigSettings.isWeatcherWidgetBGTransparent = isChecked;
                break;
            case R.id.toggle_on_zero_screen:
                ConfigSettings.isOpenNavi = isChecked;
                break;
            case R.id.toggle_replace_theme:
                ConfigSettings.isReplaceTheme = isChecked;
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mPrompt.show();
        }
    }
}
