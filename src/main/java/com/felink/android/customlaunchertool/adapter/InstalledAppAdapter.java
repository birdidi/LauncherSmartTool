package com.felink.android.customlaunchertool.adapter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.felink.android.customlaunchertool.R;
import com.felink.android.customlaunchertool.config.Global;
import com.felink.android.customlaunchertool.kitset.common.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月19日 19:34.</br>
 * @update: </br>
 */

public class InstalledAppAdapter extends BaseAdapter {

    private List<ResolveInfo> mData = new ArrayList<ResolveInfo>();
    private PackageManager packageManager;

    public InstalledAppAdapter(PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = parent.inflate(parent.getContext(), R.layout.item_app, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ResolveInfo info = (ResolveInfo) getItem(position);

        String name = info.loadLabel(packageManager).toString();
        String clazz = info.activityInfo.name;
        String pkg = info.activityInfo.packageName;
        Drawable icon = info.loadIcon(packageManager);

        holder.appName.setText(name);
        holder.appPkg.setText(pkg);
        holder.appClazz.setText(clazz);
        holder.appIcon.setImageDrawable(icon);

        return convertView;
    }

    public synchronized void refresh() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> list = packageManager.queryIntentActivities(mainIntent, 0);
        if (list != null && !list.isEmpty()) {
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    mData.clear();
                    mData.addAll(list);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private static class ViewHolder {

        ImageView appIcon;
        TextView appPkg;
        TextView appClazz;
        TextView appName;

        public ViewHolder(View view) {

            appClazz = (TextView) view.findViewById(R.id.tv_app_clazz);
            appIcon = (ImageView) view.findViewById(R.id.iv_app_icon);
            appPkg = (TextView) view.findViewById(R.id.tv_app_pkg);
            appName = (TextView) view.findViewById(R.id.tv_app_name);
        }
    }
}
