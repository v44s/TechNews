package com.example.technews.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductViewModelFactory implements ViewModelProvider.Factory {

    private String FILTER;

    public ProductViewModelFactory(String FILTER){
        this.FILTER = FILTER;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProductFeedViewModel(FILTER);
    }
}
