package com.harutyun.githubtrendingrepositories.di;

import com.harutyun.data.BuildConfig;
import com.harutyun.data.remote.GithubReposRemoteDataSource;
import com.harutyun.data.remote.GithubReposService;
import com.harutyun.data.remote.GithubReposServiceDataSource;
import com.harutyun.data.repository.GithubRepoRepositoryImpl;
import com.harutyun.domain.repository.GithubRepoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
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
    GithubReposRemoteDataSource provideGithubReposRemoteDataSource(GithubReposService trendingRepositoriesApi) {
        return new GithubReposServiceDataSource(trendingRepositoriesApi);
    }

    @Singleton
    @Provides
    GithubRepoRepository provideGithubRepoRepository(GithubReposRemoteDataSource remoteDataSource) {
        return new GithubRepoRepositoryImpl(remoteDataSource);
    }

    @Singleton
    @Provides
    GithubReposService provideGithubReposService(Retrofit retrofit) {
        return retrofit.create(GithubReposService.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient().newBuilder().build();
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
}
