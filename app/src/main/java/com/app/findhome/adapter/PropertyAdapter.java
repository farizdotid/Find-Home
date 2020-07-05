package com.app.findhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.findhome.R;
import com.app.findhome.databinding.ItemPropertyBinding;
import com.app.findhome.model.entity.Property;
import com.app.findhome.util.FunctionHelper;

import java.util.List;

public class PropertyAdapter extends
        RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private static final String TAG = PropertyAdapter.class.getSimpleName();

    private Context context;
    private List<Property> list;
    private PropertyAdapterCallback mAdapterCallback;
    private ItemPropertyBinding binding;

    public PropertyAdapter(Context context, List<Property> list, PropertyAdapterCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ItemPropertyBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        Property item = list.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        int size = this.list.size();
        this.list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addData(List<Property> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull ItemPropertyBinding itemView) {
            super(itemView.getRoot());

            itemView.getRoot().setOnClickListener(v -> mAdapterCallback
                    .onRowPropertyAdapterClicked(getAdapterPosition()));
        }

        void bindData(Property item) {
            String image = item.image;
            FunctionHelper.loadImage(context, image, binding.ivImage);

            int price = item.price;
            String initPrice = FunctionHelper.rupiahFormat(price);
            binding.tvPrice.setText(initPrice);

            String title = item.title;
            binding.tvTitle.setText(title);

            String address = item.address;
            binding.tvLocation.setText(address);

            int isFavorite = item.isFavorite;
            if (isFavorite == 1) binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
    }

    public interface PropertyAdapterCallback {
        void onRowPropertyAdapterClicked(int position);
    }
}