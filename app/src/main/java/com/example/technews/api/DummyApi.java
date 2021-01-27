package com.example.technews.api;

import android.content.Context;
import android.util.Log;

import com.example.technews.App;
import com.example.technews.data.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyApi {

    private final String JSON_FILE = "product.json";
    private final int PAGE_SIZE = 25;

    private final List<Product> list;
    private final List<Product> medList = new ArrayList<>();
    private final List<Product> techList = new ArrayList<>();
    private final List<Product> aiList = new ArrayList<>();
    private final List<Product> empty = new ArrayList<>();

    private final String ALL_TAG = "all";
    private final String AI = "ai";
    private final String MED = "medical";
    private final String TECH = "technology";
    private final String BOOKMARK = "bookmark";

    private static final DummyApi instance = new DummyApi();

    public static DummyApi getInstance(){
        return instance;
    }

    private Map<String,String> filterMap = new HashMap<>();

    private DummyApi(){
        filterMap.put("All",ALL_TAG);
        filterMap.put("Medical",MED);
        filterMap.put("Technology",TECH);
        filterMap.put("AI",AI);
        filterMap.put("Bookmarks",BOOKMARK);

        Context context = App.getContext();
        String json = null;
        try {
            InputStream is = context.getAssets().open(JSON_FILE);
            int size = is.available();
            byte[] bytes = new byte[size];
            final int read = is.read(bytes, 0, size);
            json = new String(bytes);
        } catch (IOException e) {
            Log.w("Exception",e.toString());
        }
        if(json!=null){
            Type productListType = new TypeToken<List<Product>>(){}.getType();
            list = new Gson().fromJson(json,productListType);
        }else{
            list = new ArrayList<>();
        }

        for(Product p:list){
            if(p.tag.contains(AI)){
                aiList.add(p);
            }else if(p.tag.contains(MED)){
                medList.add(p);
            }else if(p.tag.contains(TECH)){
                techList.add(p);
            }
        }

        empty.add(Product.getEmpty());
    }

    public List<Product> getProductList(String filterKey,int page){
        String filter = filterMap.get(filterKey);
        if(ALL_TAG.contains(filter)){
            return getFromList(list,page);
        }else if(AI.contains(filter)){
            return getFromList(aiList,page);
        }else if(MED.contains(filter)){
            return getFromList(medList,page);
        }else if(TECH.contains(filter)){
            return getFromList(techList,page);
        }

        return empty;
    }

    private List<Product> getFromList(List<Product> inputList,int page){
        List<Product> l = new ArrayList<>(PAGE_SIZE);
        if(inputList.size()==0) return l;

        int startIdx = (page-1)*PAGE_SIZE % inputList.size();
        int counter = 0;
        while (counter<PAGE_SIZE){
            int actualIdx = startIdx++;
            Product p = inputList.get((actualIdx)%inputList.size()).clone();
            p.id = actualIdx+1;
            l.add(p);
            counter++;
        }
        return l;
    }
}
