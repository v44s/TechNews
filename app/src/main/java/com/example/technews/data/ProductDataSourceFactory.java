package com.example.technews.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;


public class ProductDataSourceFactory extends DataSource.Factory<Integer,Product> {

    private MutableLiveData<ProductDataSource> sourceLiveData = new MutableLiveData<>();
    private final String FILTER;

    public ProductDataSourceFactory(String filter){
        this.FILTER = filter;
    }

    @NonNull
    @Override
    public DataSource<Integer, Product> create() {
        ProductDataSource dataSource = new ProductDataSource(FILTER);
        sourceLiveData.postValue(dataSource);
        return dataSource;
    }
}
