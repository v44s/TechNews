package com.example.technews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technews.R;
import com.example.technews.App;
import com.example.technews.data.Product;

import java.util.Objects;

public class ProductFeedAdapter extends PagedListAdapter<Product, ProductFeedAdapter.ProductViewHolder> {
    private static DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Product oldProduct, Product newProduct) {
                    return false;
//                    return oldProduct.getId() == newProduct.getId();
                }

                @Override
                public boolean areContentsTheSame(Product oldProduct,Product newProduct) {
                    return false;
//                    return oldProduct.equals(newProduct);
                }
            };

    public ProductFeedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_feed_tile, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        if (product != null) {
            holder.bindTo(product);
        } else {
            // Null place holder
            holder.clear();
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView name,founder,desc,votes;
        ImageView img;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            founder = itemView.findViewById(R.id.founder);
            desc = itemView.findViewById(R.id.desc);
            votes = itemView.findViewById(R.id.votes);
            img = itemView.findViewById(R.id.image);
        }

        public void bindTo(Product product) {
            name.setText(product.name);
            desc.setText(product.desc);
            founder.setText(String.format(App.getContext().getString(R.string.FOUNDER),product.founder));
            votes.setText(String.valueOf(product.votes));
            String TECH = "tech.jpg";
            String MED = "med.jpg";
            String AI = "ai.jpg";
            if(Objects.equals(product.image,TECH)){
                img.setImageResource(R.drawable.tech);
            }else if(Objects.equals(product.image,MED)){
                img.setImageResource(R.drawable.med);
            }else if(Objects.equals(product.image,AI)){
                img.setImageResource(R.drawable.ai);
            }else{
                img.setImageResource(R.drawable.place);
            }
        }

        public void clear() {
            String BLANK = "___";
            name.setText(BLANK);
            desc.setText(BLANK);
            founder.setText(BLANK);
            votes.setText(BLANK);
            img.setImageResource(R.drawable.place);
        }
    }
}
