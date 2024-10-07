package com.example.first;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {

    // List to hold image resources
    private final List<Integer> images;

    // Constructor
    public ImageSliderAdapter(List<Integer> images) {
        this.images = images;
    }

    // Method to inflate the layout for each item of the RecyclerView
    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the individual item (slider_item.xml)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    // Method to bind the data (image and text) to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        // Set the image resource
        holder.imageView.setImageResource(images.get(position));
        // Set the text for the slider (optional)
        holder.textView.setText("Handmade Product " + position);  // Example text
    }

    // Method to get the total count of items in the list
    @Override
    public int getItemCount() {
        return images.size();
    }

    // Static inner class to represent each item in the RecyclerView
    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_slider);  // Bind the ImageView
            textView = itemView.findViewById(R.id.slider_text);  // Bind the TextView
        }
    }
}
