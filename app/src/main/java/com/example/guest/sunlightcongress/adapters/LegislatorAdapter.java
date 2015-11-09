package com.example.guest.sunlightcongress.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
        TextView phoneLabel;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;



        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.legislator_list_item, null);
            holder = new ViewHolder();
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
            holder.phoneLabel = (TextView) convertView.findViewById(R.id.phoneLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Legislator legislator = mLegislators.get(position);

        holder.nameLabel.setText(legislator.getFullName());
        holder.phoneLabel.setText(legislator.getPhone());
        final String phoneNum = legislator.getPhone();
        final String office = legislator.getOffice();
        final String state = legislator.getState();


        Button mapButton = (Button) convertView.findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:0,0?q=" + office + "," + state);

                //Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                mContext.startActivity(mapIntent);
            }
        });

        holder.phoneLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri number = Uri.parse("tel:" + phoneNum);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                mContext.startActivity(callIntent);
            }
        });

        return convertView;
    }

}
