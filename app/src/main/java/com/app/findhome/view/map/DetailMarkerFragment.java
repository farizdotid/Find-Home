package com.app.findhome.view.map;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.app.findhome.R;
import com.app.findhome.databinding.FragmentDetailMarkerBinding;
import com.app.findhome.util.FunctionHelper;
import com.app.findhome.view.detail.DetailActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DetailMarkerFragment extends DialogFragment {

    private static final String ARG_PARAM_IMAGE = "param_image";
    private static final String ARG_PARAM_PRICE = "param_price";
    private static final String ARG_PARAM_TITLE = "param_title";
    private static final String ARG_PARAM_ADDRESS = "param_address";
    private static final String ARG_PARAM_IS_FAVORITE = "param_is_favorite";
    private static final String ARG_PARAM_ID = "param_id";

    private FragmentDetailMarkerBinding binding;

    private String mImage;
    private int mPrice;
    private String mTitle;
    private String mAddress;
    private int mIsFavorite;
    private String mId;

    public DetailMarkerFragment() {
        // Required empty public constructor
    }

    public static DetailMarkerFragment newInstance(String image, int price, String title,
                                                   String address, int isFavorite, String id) {
        DetailMarkerFragment fragment = new DetailMarkerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_IMAGE, image);
        args.putInt(ARG_PARAM_PRICE, price);
        args.putString(ARG_PARAM_TITLE, title);
        args.putString(ARG_PARAM_ADDRESS, address);
        args.putInt(ARG_PARAM_IS_FAVORITE, isFavorite);
        args.putString(ARG_PARAM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImage = getArguments().getString(ARG_PARAM_IMAGE);
            mPrice = getArguments().getInt(ARG_PARAM_PRICE);
            mTitle = getArguments().getString(ARG_PARAM_TITLE);
            mAddress = getArguments().getString(ARG_PARAM_ADDRESS);
            mIsFavorite = getArguments().getInt(ARG_PARAM_IS_FAVORITE);
            mId = getArguments().getString(ARG_PARAM_ID);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = MATCH_PARENT;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;

        getDialog().getWindow().setAttributes(p);

        binding = FragmentDetailMarkerBinding.inflate(inflater, container, false);

        loadData();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAction();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initAction() {
        binding.rlRoot.setOnClickListener(v -> DetailActivity.startActivity(getActivity(), mId));
    }

    private void loadData() {
        FunctionHelper.loadImage(getActivity(), mImage, binding.ivImage);

        String initPrice = FunctionHelper.rupiahFormat(mPrice);
        binding.tvPrice.setText(initPrice);

        binding.tvTitle.setText(mTitle);

        binding.tvLocation.setText(mAddress);

        if (mIsFavorite == 1)
            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
    }
}