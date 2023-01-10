package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.harutyun.githubtrendingrepositories.databinding.ItemGithubReposLoadingStateBinding;

import org.jetbrains.annotations.NotNull;

public class GithubReposLoadStateAdapter extends LoadStateAdapter<GithubReposLoadStateAdapter.LoadStateViewHolder> {
    private final View.OnClickListener mRetryCallback;

    public GithubReposLoadStateAdapter(View.OnClickListener retryCallback) {
        mRetryCallback = retryCallback;
    }

    @NotNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup parent, @NotNull LoadState loadState) {
        ItemGithubReposLoadingStateBinding binding = ItemGithubReposLoadingStateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.btnRetryItemLoadingState.setOnClickListener(mRetryCallback);

        return new LoadStateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull LoadStateViewHolder holder, @NotNull LoadState loadState) {

        if (loadState instanceof LoadState.Error) {
            LoadState.Error loadStateError = (LoadState.Error) loadState;
            holder.binding.tvErrorItemLoadingState.setText(loadStateError.getError().getLocalizedMessage());
        }
        holder.binding.pbItemLoadingState.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
        holder.binding.btnRetryItemLoadingState.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
        holder.binding.tvErrorItemLoadingState.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);

    }


    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {
        ItemGithubReposLoadingStateBinding binding;

        LoadStateViewHolder(ItemGithubReposLoadingStateBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }
}
