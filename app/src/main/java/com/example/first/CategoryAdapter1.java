package com.example.first;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter1 extends BaseAdapter {
    private Context context;
    private List<String> categories;
    private List<String> images;

    public CategoryAdapter1(Context context, List<String> categories, List<String> images) {
        this.context = context;
        this.categories = categories;
        this.images = images;
    }

    @Override
    public int getCount() {
        // Return half of the size of categories list because we are displaying two categories per row
        return (int) Math.ceil(categories.size() / 2.0);
    }

    @Override
    public Object getItem(int position) {
        return null; // Not used
    }

    @Override
    public long getItemId(int position) {
        return position; // You can return a unique ID if needed
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
        }

        // Get references to the views
        ImageView imageView1 = convertView.findViewById(R.id.imageViewCategory);
        TextView textView1 = convertView.findViewById(R.id.textViewCategory);
        ImageView imageView2 = convertView.findViewById(R.id.imageViewCategory2);
        TextView textView2 = convertView.findViewById(R.id.textViewCategory2);

        // Bind data for the first category
        int firstIndex = position * 2;
        textView1.setText(categories.get(firstIndex));
        Glide.with(context).load(images.get(firstIndex)).into(imageView1);

        // Bind data for the second category, if it exists
        if (firstIndex + 1 < categories.size()) {
            int secondIndex = firstIndex + 1;
            textView2.setText(categories.get(secondIndex));
            Glide.with(context).load(images.get(secondIndex)).into(imageView2);
        } else {
            // Hide the second category view if it does not exist
            imageView2.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
        }

        return convertView;
    }
}
