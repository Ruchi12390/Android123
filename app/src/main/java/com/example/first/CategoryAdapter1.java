package com.example.first;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CategoryAdapter1 extends RecyclerView.Adapter<CategoryAdapter1.CategoryViewHolder> {

    private Context context;
    private List<String> categories;
    private List<String> images;

    public CategoryAdapter1(Context context, List<String> categories, List<String> images) {
        this.context = context;
        this.categories = categories;
        this.images = images;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_list_activity1, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Calculate the indices for the two categories in each row
        int firstIndex = position * 2;
        int secondIndex = firstIndex + 1;

        // Bind data for the first category
        holder.textViewCategory1.setText(categories.get(firstIndex));
        Glide.with(context).load(images.get(firstIndex)).into(holder.imageViewCategory1);

        // Check if there's a second category for this row
        if (secondIndex < categories.size()) {
            holder.textViewCategory2.setText(categories.get(secondIndex));
            Glide.with(context).load(images.get(secondIndex)).into(holder.imageViewCategory2);

            // Ensure the second category views are visible
            holder.imageViewCategory2.setVisibility(View.VISIBLE);
            holder.textViewCategory2.setVisibility(View.VISIBLE);
        } else {
            // Hide the second category views if there's no second category
            holder.imageViewCategory2.setVisibility(View.INVISIBLE);
            holder.textViewCategory2.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        // Return half the size of the category list (since 2 categories per row)
        return (int) Math.ceil(categories.size() / 2.0);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCategory1, imageViewCategory2;
        TextView textViewCategory1, textViewCategory2;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory1 = itemView.findViewById(R.id.imageViewCategory1);
            textViewCategory1 = itemView.findViewById(R.id.textViewCategory1);
            imageViewCategory2 = itemView.findViewById(R.id.imageViewCategory2);
            textViewCategory2 = itemView.findViewById(R.id.textViewCategory2);
        }
    }
}
