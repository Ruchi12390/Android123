package com.example.first;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide; // Make sure to add Glide for image loading

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<String> categories;
    private List<String> images;

    public CategoryAdapter(Context context, List<String> categories, List<String> images) {
        this.context = context;
        this.categories = categories;
        this.images = images;
    }

    @Override
    public int getCount() {
        return categories.size();  // Return the number of categories
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);  // Return the category at the given position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewCategory);
        TextView textView = convertView.findViewById(R.id.textViewCategory);

        textView.setText(categories.get(position));
        Glide.with(context)
                .load(images.get(position))
                .error(R.drawable.img)  // Placeholder if image load fails
                .into(imageView);

        return convertView;
    }
}
