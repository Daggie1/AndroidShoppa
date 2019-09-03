package tech.muva.academy.android_shoppa.api.services;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class ServiceGenerator {
    private static ServiceGenerator mInstance;
    public static final String BaseUrl = "http://192.168.100.14:8000/";


    private Retrofit mRetrofit;
    private Retrofit retrofit;

    private ServiceGenerator(final Context context){
        final String token;
        if(new SharedPreferencesConfig(context).isloggedIn()){
            token=new SharedPreferencesConfig(context).readBuyerToken();

        }else{
            token="";
        }
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request=chain.request();
                        Request.Builder new_request=request.newBuilder().addHeader("Authorization","Token "+token );
                        return chain.proceed(new_request.build());
                    }
                });


        retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static  synchronized ServiceGenerator getInstance(Context context){
        if(mInstance==null){
            mInstance=new ServiceGenerator(context);
        }
        return mInstance;
    }

    public UserClient getAnotherApiConnector(){
        return retrofit.create(UserClient.class);
    }
}