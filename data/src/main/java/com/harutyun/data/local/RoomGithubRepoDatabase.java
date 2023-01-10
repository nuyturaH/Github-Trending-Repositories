package com.harutyun.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.harutyun.data.local.entities.GithubRepoEntity;

@Database(entities = {GithubRepoEntity.class}, version = 1, exportSchema = false)
public abstract class RoomGithubRepoDatabase extends RoomDatabase {
    public abstract RoomGithubReposDao roomGithubReposDao();
}
