package com.app.findhome.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.app.findhome.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.text.DecimalFormat;

public class FunctionHelper {
    public static void debugLog(String className, String message) {
        Log.i("debug" + className, message);
    }

    public static String rupiahFormat(int price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "Rp " + formatter.format(price).replaceAll(",", ".");
    }

    public static void loadImage(Context context, Object source, ImageView imageView) {
        Glide.with(context)
                .load(source)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageCirlce(Context context, Object source, ImageView imageView) {
        Glide.with(context)
                .load(source)
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.ic_baseline_person_pin_black_24)
                .into(imageView);
    }

    public static void loadImageWithRounded(Context context, Object source, ImageView imageView, int radius) {
        Glide.with(context)
                .load(source)
                .placeholder(R.color.colorPrimaryDark)
                .transform(new CenterCrop(), new RoundedCorners(radius))
                .into(imageView);
    }

    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, @NonNull int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static void shareLink(Context context, String message) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    public static void openDialPad(Context context, String phoneNumber) {
        Uri number = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        context.startActivity(callIntent);
    }
}
