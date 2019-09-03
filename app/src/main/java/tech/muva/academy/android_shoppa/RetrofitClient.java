package tech.muva.academy.android_shoppa;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    public static final String BaseUrl = "http://192.168.100.14:8000/";

    private Retrofit mRetrofit;
    private Retrofit retrofit;

    private RetrofitClient(Context context){
        final String token;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();
        retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static  synchronized RetrofitClient getInstance(Context context){
        if(mInstance==null){
            mInstance=new RetrofitClient(context);
        }
        return mInstance;
    }

    public ApiEndPoints getApiConnector(){
        return retrofit.create(ApiEndPoints.class);
    }

}




