package com.harutyun.githubtrendingrepositories.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.harutyun.data.BuildConfig;
import com.harutyun.data.local.GithubReposLocalDataSource;
import com.harutyun.data.local.RoomGithubRepoDatabase;
import com.harutyun.data.local.RoomGithubReposDao;
import com.harutyun.data.local.RoomGithubReposLocalDataSource;
import com.harutyun.data.mappers.GithubRepoMapper;
import com.harutyun.data.remote.GithubReposRemoteDataSource;
import com.harutyun.data.remote.GithubReposService;
import com.harutyun.data.remote.GithubReposServiceDataSource;
import com.harutyun.data.remote.NetworkConnectionInterceptor;
import com.harutyun.data.repository.GithubRepoRepositoryImpl;
import com.harutyun.domain.repository.GithubRepoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Singleton
    @Provides
    GithubReposRemoteDataSource provideGithubReposRemoteDataSource(GithubReposService trendingRepositoriesApi, GithubRepoMapper githubRepoMapper) {
        return new GithubReposServiceDataSource(trendingRepositoriesApi, githubRepoMapper);
    }

    @Singleton
    @Provides
    GithubRepoMapper provideGithubRepoMapper() {
        return new GithubRepoMapper();
    }

    @Singleton
    @Provides
    GithubRepoRepository provideGithubRepoRepository(GithubReposRemoteDataSource remoteDataSource,
                                                     GithubReposLocalDataSource githubReposLocalDataSource,
                                                     GithubRepoMapper githubRepoMapper) {
        return new GithubRepoRepositoryImpl(remoteDataSource, githubReposLocalDataSource, githubRepoMapper);
    }

    @Singleton
    @Provides
    GithubReposService provideGithubReposService(Retrofit retrofit) {
        return retrofit.create(GithubReposService.class);
    }

    @Singleton
    @Provides
    NetworkConnectionInterceptor provideConnectivityInterceptor(@ApplicationContext Context context) {
        return new NetworkConnectionInterceptor(context);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(NetworkConnectionInterceptor networkConnectionInterceptor) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(networkConnectionInterceptor)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    RoomGithubRepoDatabase provideRoomGithubRepoDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, RoomGithubRepoDatabase.class, "GithubRepoDatabase.db").build();
    }

    @Singleton
    @Provides
    RoomGithubReposDao provideRoomGithubReposDao(RoomGithubRepoDatabase githubRepoDatabase) {
        return githubRepoDatabase.roomGithubReposDao();
    }

    @Singleton
    @Provides
    GithubReposLocalDataSource provideGithubReposLocalDataSource(RoomGithubReposDao roomGithubReposDao) {
        return new RoomGithubReposLocalDataSource(roomGithubReposDao);
    }

}
