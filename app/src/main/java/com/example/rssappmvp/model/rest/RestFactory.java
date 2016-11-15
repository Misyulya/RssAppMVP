package com.example.rssappmvp.model.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1 on 14.04.2016.
 */
public class RestFactory {

    public static final String RSS = "articles?source=the-lad-bible&apiKey=955c8e47d9ed40f7866197b9a4fc0eff";
    private static final String BASE_URL = "https://newsapi.org/v1/";

    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor).build();

    public static ApiInterface getApiInterface() {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        return service;
    }
}
