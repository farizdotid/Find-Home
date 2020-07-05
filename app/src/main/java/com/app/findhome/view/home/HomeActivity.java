package com.app.findhome.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.findhome.adapter.PropertyAdapter;
import com.app.findhome.databinding.ActivityHomeBinding;
import com.app.findhome.model.entity.Property;
import com.app.findhome.view.detail.DetailActivity;
import com.app.findhome.view.favorite.FavoriteActivity;
import com.app.findhome.view.map.MapActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeContract.View,
        PropertyAdapter.PropertyAdapterCallback {

    public static void startActivity(Context context) {
        Intent intent = new Intent(new Intent(context, HomeActivity.class));
        context.startActivity(intent);
    }

    private ActivityHomeBinding binding;
    private HomePresenter homePresenter;
    private PropertyAdapter propertyAdapter;

    private List<Property> mProperties = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homePresenter = new HomePresenter(this);

        initPropertyAdapter();
        initAction();

        homePresenter.requestProperty();
    }


    private void initAction() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                String mergeKeyword = keyword.isEmpty() ? "" : "%" + keyword + "%";
                homePresenter.requestSearchProperty(mergeKeyword);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.ivDelete.setOnClickListener(v -> {
            binding.etSearch.setText("");
            homePresenter.requestSearchProperty("");
        });

        binding.ivMap.setOnClickListener(v -> MapActivity.startActivity(this));

        binding.btnSeeAll.setOnClickListener(v -> FavoriteActivity.startActivity(this));
    }

    @Override
    public void showLoading() {
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.rvProperty.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.rvProperty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isReqProperty(List<Property> properties) {
        propertyAdapter.clear();
        mProperties = properties;
        propertyAdapter.addData(mProperties);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.requestDisposable();
    }

    private void initPropertyAdapter() {
        propertyAdapter = new PropertyAdapter(this, mProperties, this);
        binding.rvProperty.setLayoutManager(new LinearLayoutManager(this));
        binding.rvProperty.setItemAnimator(new DefaultItemAnimator());
        binding.rvProperty.setAdapter(propertyAdapter);
    }

    @Override
    public void onRowPropertyAdapterClicked(int position) {
        String id = mProperties.get(position).id;
        DetailActivity.startActivity(this, id);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        homePresenter.requestProperty();
    }
}