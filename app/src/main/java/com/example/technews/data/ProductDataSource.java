package com.example.technews.data;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.technews.api.DummyApi;

import java.util.List;

public class ProductDataSource extends PageKeyedDataSource<Integer,Product> {
    public static final int PAGE_SIZE = 25;
    private final String FILTER;

    ProductDataSource(String filter){
        this.FILTER = filter;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
        int FIRST_PAGE = 1;
        List<Product> data  = DummyApi.getInstance().getProductList(FILTER, FIRST_PAGE);
        callback.onResult(data,null, FIRST_PAGE +1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        List<Product> data = DummyApi.getInstance().getProductList(FILTER,params.key);
        Integer previousKey = params.key > 1? params.key-1:null;
        callback.onResult(data,previousKey);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        List<Product> data = DummyApi.getInstance().getProductList(FILTER,params.key);
        callback.onResult(data,params.key+1);
    }
}

