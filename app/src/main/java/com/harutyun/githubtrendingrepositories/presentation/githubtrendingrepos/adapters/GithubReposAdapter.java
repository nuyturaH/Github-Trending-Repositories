package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.harutyun.domain.models.GithubRepo;
import com.harutyun.githubtrendingrepositories.R;
import com.harutyun.githubtrendingrepositories.databinding.ItemGithubRepoBinding;

public class GithubReposAdapter extends PagingDataAdapter<GithubRepo, GithubReposAdapter.ViewHolder> {

    private final OnItemClickListener mOnItemClickListener;

    public GithubReposAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGithubRepoBinding binding = ItemGithubRepoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GithubRepo repo = getItem(position);
        holder.binding.tvNameItemRepo.setText(repo.getName());
        holder.binding.tvDescriptionItemRepo.setText(repo.getDescription());
        holder.binding.tvLoginItemRepo.setText(repo.getOwner().getLogin());
        holder.binding.tvStargazersCountItemRepo.setText(String.valueOf(repo.getStargazersCount()));
        Glide.with(holder.binding.ivAvatarItemRepo.getContext())
                .load(repo.getOwner().getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_person_box)
                .into(holder.binding.ivAvatarItemRepo);

        holder.binding.getRoot().setOnClickListener(v -> mOnItemClickListener.onItemClicked(repo));
        holder.binding.ivFavouriteItemRepo.setOnClickListener(v -> {
            mOnItemClickListener.onFavouriteClicked(repo);
            notifyItemChanged(position);
        });

        if (repo.isFavourite()) {
            holder.binding.ivFavouriteItemRepo.setBackgroundResource(R.drawable.ic_favorite_filled);
        } else {
            holder.binding.ivFavouriteItemRepo.setBackgroundResource(R.drawable.ic_favorite);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemGithubRepoBinding binding;

        ViewHolder(ItemGithubRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static final DiffUtil.ItemCallback<GithubRepo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<GithubRepo>() {
                @Override
                public boolean areItemsTheSame(@NonNull GithubRepo oldItem, @NonNull GithubRepo newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull GithubRepo oldItem, @NonNull GithubRepo newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public interface OnItemClickListener {
        void onItemClicked(GithubRepo githubRepo);

        void onFavouriteClicked(GithubRepo githubRepo);
    }
}
