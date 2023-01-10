package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harutyun.githubtrendingrepositories.R;
import com.harutyun.githubtrendingrepositories.databinding.ItemHeaderBinding;

public class GithubReposHeaderAdapter extends RecyclerView.Adapter<GithubReposHeaderAdapter.HeaderViewHolder> {

    private String mHeader;
    public GithubReposHeaderAdapter(String header) {
        mHeader = header;
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHeaderBinding binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.tvHeaderItem.setText(mHeader);

        return new HeaderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        ItemHeaderBinding binding;

        HeaderViewHolder(ItemHeaderBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }
}
