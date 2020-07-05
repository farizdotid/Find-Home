package com.app.findhome.view.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.findhome.R;
import com.app.findhome.databinding.ActivityDetailBinding;
import com.app.findhome.model.entity.Property;
import com.app.findhome.util.FunctionHelper;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private static String PROPERTY_ID = "property_id";

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(new Intent(context, DetailActivity.class));
        intent.putExtra(PROPERTY_ID, id);
        context.startActivity(intent);
    }

    private ActivityDetailBinding binding;
    private DetailPresenter detailPresenter;

    private String mId = "";
    private String mTitle = "";
    private int mIsFavorite = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        detailPresenter = new DetailPresenter(this);

        binding.ivBack.bringToFront();
        loadData();
        initAction();
    }

    private void loadData() {
        Uri data = this.getIntent().getData();
        if (data != null && data.isHierarchical()) {
            String path = data.getPath();
            String[] urlPath;
            if (path != null) {
                urlPath = path.split("/");
                String id = urlPath[2];
                detailPresenter.requestDetail(id);
            }
        } else {
            mId = getIntent().getStringExtra(PROPERTY_ID);
            detailPresenter.requestDetail(mId);
        }
    }

    private void initAction() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());

        binding.ivShare.setOnClickListener(v -> {
            String message = "Find Home : " + mTitle + " \n "
                    + "https://99.co/findhome/" + mId;
            FunctionHelper.shareLink(this, message);
        });

        binding.btnKontak.setOnClickListener(v -> FunctionHelper
                .openDialPad(this, "085860781871"));

        binding.ivFavorite.setOnClickListener(v -> {
            if (mIsFavorite == 1) {
                mIsFavorite = 0;
                detailPresenter.requestFavorite(0, mId);
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            } else {
                mIsFavorite = 1;
                detailPresenter.requestFavorite(1, mId);
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        });
    }

    @Override
    public void showLoading() {
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.linContent.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.linContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isReqDetail(Property property) {
        String image = property.image;
        FunctionHelper.loadImage(this, image, binding.ivImage);

        int price = property.price;
        String initPrice = FunctionHelper.rupiahFormat(price);
        binding.tvPrice.setText(initPrice);

        String title = property.title;
        mTitle = title;
        binding.tvTitle.setText(title);

        String address = property.address;
        binding.tvLocation.setText(address);

        String deskripsi = property.deskripsi;
        binding.tvDeskripsi.setText(deskripsi);

        String fasilitas = property.fasilitas;
        binding.tvFasilitas.setText(fasilitas);

        String agentAvatar = property.agentAvatar;
        FunctionHelper.loadImageCirlce(this, agentAvatar, binding.ivAvatar);

        String agentName = property.agentName;
        binding.tvAgentName.setText(agentName);

        String agentType = property.agentType;
        binding.tvAgentType.setText(agentType);

        mIsFavorite = property.isFavorite;
        if (mIsFavorite == 1) binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.requestDisposable();
    }
}