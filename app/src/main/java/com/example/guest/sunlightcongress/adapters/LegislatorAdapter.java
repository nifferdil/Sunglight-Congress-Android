package com.example.guest.sunlightcongress.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guest.sunlightcongress.Legislator;
import com.example.guest.sunlightcongress.R;

import java.util.ArrayList;

/**
 * Created by Guest on 11/4/15.
 */
public class LegislatorAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Legislator> mLegislators;

    public LegislatorAdapter(Context context, ArrayList<Legislator> legislators) {
        mContext = context;
        mLegislators = legislators;
    }

    @Override
    public int getCount() {
        return mLegislators.size();
    }

    @Override
    public Object getItem(int position) {
        return mLegislators.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView nameLabel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.legislator_list_item, null);
            holder = new ViewHolder();
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Legislator legislator = mLegislators.get(position);

        holder.nameLabel.setText(legislator.getFullName());

        return convertView;
    }

}
