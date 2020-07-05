package com.app.findhome.view.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.findhome.adapter.PropertyAdapter;
import com.app.findhome.databinding.ActivityFavoriteBinding;
import com.app.findhome.model.entity.Property;
import com.app.findhome.view.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements FavoriteContract.View,
        PropertyAdapter.PropertyAdapterCallback {

    public static void startActivity(Context context) {
        Intent intent = new Intent(new Intent(context, FavoriteActivity.class));
        context.startActivity(intent);
    }

    private ActivityFavoriteBinding binding;
    private FavoritePresenter favoritePresenter;

    private PropertyAdapter propertyAdapter;

    private List<Property> mProperties = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        favoritePresenter = new FavoritePresenter(this);

        initPropertyAdapter();
        initAction();

        favoritePresenter.requestFavoriteProperty();
    }


    private void initAction() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
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
    public void isReqFavorites(List<Property> properties) {
        propertyAdapter.clear();
        mProperties = properties;
        propertyAdapter.addData(mProperties);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoritePresenter.requestDisposable();
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
        favoritePresenter.requestFavoriteProperty();
    }
}