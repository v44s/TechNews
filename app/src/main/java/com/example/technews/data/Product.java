package com.example.technews.data;

import android.app.Person;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Cloneable{
    public int id;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("desc")
    public String desc;

    @Expose
    @SerializedName("image")
    public String image;

    @Expose
    @SerializedName("founder")
    public String founder;

    @Expose
    @SerializedName("tag")
    public String tag;

    @Expose
    @SerializedName("votes")
    public int votes;

    @NonNull
    @Override
    public Product clone(){
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.w("Exceptions",e.toString());
        }
        Product p = new Product();
        p.name = this.name;
        p.desc = this.desc;
        p.tag = this.tag;
        p.founder = this.founder;
        p.votes = this.votes;
        p.image = this.image;
        return p;
    }

    public static Product getEmpty(){
        Product p = new Product();
        String BLANK = "---";
        p.name = BLANK;
        p.desc = BLANK;
        p.tag = BLANK;
        p.founder = BLANK;
        p.votes = 0;
        return p;
    }
}
