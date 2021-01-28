package com.example.technews.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                @Override
                public boolean areItemsTheSame(Product oldProduct, Product newProduct) {
                    return oldProduct.id == newProduct.id;
                }

                @Override
                public boolean areContentsTheSame(Product oldProduct,Product newProduct) {
                    return oldProduct.id == newProduct.id;
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
        Button vote,share,bookmark;
        ImageView img;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            founder = itemView.findViewById(R.id.founder);
            desc = itemView.findViewById(R.id.desc);
            votes = itemView.findViewById(R.id.votes);
            img = itemView.findViewById(R.id.image);

            vote = itemView.findViewById(R.id.vote_btn);
            share = itemView.findViewById(R.id.share_btn);
            bookmark = itemView.findViewById(R.id.bookmark_btn);
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

            vote.setOnClickListener(x->{
                votes.setText(String.valueOf(product.votes+1));
                votes.setTextColor(Color.GREEN);
            });

            share.setOnClickListener(x->{
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = String.format("%s\n By-%s",product.name,product.founder);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I found this interesting.");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                itemView.getContext().startActivity(Intent.createChooser(intent, "Share using"));
                    });

            bookmark.setOnClickListener(x->{
                Toast.makeText(itemView.getContext(),"Bookmarked",Toast.LENGTH_LONG).show();
            });
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
