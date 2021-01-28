package com.example.technews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.technews.adapter.ProductFeedAdapter;
import com.example.technews.data.Product;
import com.example.technews.viewmodels.ProductFeedViewModel;
import com.example.technews.viewmodels.ProductViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int lastFilter = 0;

        String FILTER_ALL = getString(R.string.FILTER_KEY1);

        ProductFeedViewModel viewModel = new ViewModelProvider(this,new ProductViewModelFactory(FILTER_ALL)).get(ProductFeedViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductFeedAdapter adapter = new ProductFeedAdapter();
        viewModel.productList.observe(this,adapter::submitList);
        recyclerView.setAdapter(adapter);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.filter_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.filter_spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(lastFilter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] arr = getResources().getStringArray(R.array.filter_array);
                viewModel.initViewModel(arr[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String[] arr = getResources().getStringArray(R.array.filter_array);
                viewModel.initViewModel(arr[1]);
            }
        });
    }
}