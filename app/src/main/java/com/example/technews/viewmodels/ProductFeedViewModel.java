package com.example.technews.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.technews.data.Product;
import com.example.technews.data.ProductDataSource;
import com.example.technews.data.ProductDataSourceFactory;

public class ProductFeedViewModel extends ViewModel {
    private String filter;
    public final LiveData<PagedList<Product>> productList;
    private final DataSource<Integer,Product> mostRecentDataSource;

    public ProductFeedViewModel(String FILTER){
        ProductDataSourceFactory dataSourceFactory = new ProductDataSourceFactory(FILTER);
        mostRecentDataSource = dataSourceFactory.create();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE).build();

        productList = new LivePagedListBuilder<>(dataSourceFactory,pagedListConfig)
                .build();
    }

    public void invalidateDataSource() {
        mostRecentDataSource.invalidate();
    }
}