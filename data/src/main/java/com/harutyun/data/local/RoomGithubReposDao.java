package com.harutyun.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.harutyun.data.local.entities.GithubRepoEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


@Dao
public interface RoomGithubReposDao {
    @Query("SELECT * FROM github_repo_table")
    Single<List<GithubRepoEntity>> getAllFavouriteRepos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepo(GithubRepoEntity githubRepo);

    @Delete
    void deleteRepo(GithubRepoEntity githubRepo);
}
