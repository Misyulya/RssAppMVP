package com.example.rssappmvp.model.rest;



import com.example.rssappmvp.model.data.Article;
import com.example.rssappmvp.model.data.RssItem;
import com.example.rssappmvp.model.data.RssResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

//import com.misyulya.lesson11_json.models.Review;

/**
 * Created by 1 on 14.04.2016.
 */
public interface ApiInterface {
    @GET(RestFactory.RSS)
    Call<RssResponse> getRSS();
}
