package com.harutyun.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.harutyun.data.local.entities.GithubRepoLocalEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


@Dao
public interface RoomGithubReposDao {
    @Query("SELECT * FROM github_repo_table")
    Single<List<GithubRepoLocalEntity>> getAllFavouriteRepos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRepo(GithubRepoLocalEntity githubRepo);

    @Delete
    Completable deleteRepo(GithubRepoLocalEntity githubRepo);
}
