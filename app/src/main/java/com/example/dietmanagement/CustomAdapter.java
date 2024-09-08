package com.example.dietmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<costomlistview> itemList;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, List<costomlistview> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        costomlistview item = (costomlistview) getItem(position);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView titleView = convertView.findViewById(R.id.item_title);
        TextView subtitleView = convertView.findViewById(R.id.item_subtitle);

        imageView.setImageResource(item.getImageResId());
        titleView.setText(item.getTitle());
        subtitleView.setText(item.getSubtitle());

        return convertView;
    }
}