package com.example.technews.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.technews.data.Product;
import com.example.technews.data.ProductDataSource;
import com.example.technews.data.ProductDataSourceFactory;

import java.util.Objects;

public class ProductFeedViewModel extends ViewModel {
    public LiveData<PagedList<Product>> productList;
    private DataSource<Integer,Product> mostRecentDataSource;
    private String mFilter;

    public ProductFeedViewModel(String FILTER){
        initViewModel(FILTER);
    }

    public void invalidateDataSource() {
        mostRecentDataSource.invalidate();
    }

    public void initViewModel(String FILTER){
        if(Objects.equals(FILTER,mFilter)){
            return;
        }
        mFilter = FILTER;
        Log.d("ViewModel","init");
        ProductDataSourceFactory dataSourceFactory = new ProductDataSourceFactory(FILTER);
        mostRecentDataSource = dataSourceFactory.create();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE).build();

        productList = new LivePagedListBuilder<>(dataSourceFactory,pagedListConfig)
                .build();
    }
}