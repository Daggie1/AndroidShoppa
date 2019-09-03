package tech.muva.academy.android_shoppa.datamanagers;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.RetrofitClient;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.models.Cart;
import tech.muva.academy.android_shoppa.models.CartResponse;
import tech.muva.academy.android_shoppa.models.QuantityResponse;
import tech.muva.academy.android_shoppa.models.WishlistResponse;
import tech.muva.academy.android_shoppa.ui.activities.ProductActivity;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class WishlistManager {
    Context mContext;
    String orderId;
    public  static List<WishlistResponse> mWishlistList;

    public WishlistManager(Context context) {
        mWishlistList=new ArrayList<>();
        mContext = context;

    }



    public List<WishlistResponse> add(int productId){
        mWishlistList.clear();
        Call<List<WishlistResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .addToWishlist(productId);
        call.enqueue(new Callback<List<WishlistResponse>>() {
            @Override
            public void onResponse(Call<List<WishlistResponse>> call, Response<List<WishlistResponse>> response) {
                if(response.code()==201){
                    mWishlistList.addAll(response.body());
                    Toast.makeText(mContext,"Added To Wishlist",Toast.LENGTH_SHORT).show();
                    int wishlistItems = mWishlistList.size();
                    new SharedPreferencesConfig(mContext).saveWishlistInformation(String.valueOf(wishlistItems));
                    ProductActivity.refreshCounters();



                }
                else{
                    Toast.makeText(mContext,"Not Added To Wishlist, Try Again",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<WishlistResponse>> call, Throwable t) {

            }
        });
        return mWishlistList;
    }
    public List<WishlistResponse> remove(int wishlistId){
        mWishlistList.clear();
        Call<List<WishlistResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .removeFromWishlist(wishlistId);
        call.enqueue(new Callback<List<WishlistResponse>>() {
            @Override
            public void onResponse(Call<List<WishlistResponse>> call, Response<List<WishlistResponse>> response) {
                if(response.code()==201){
                    mWishlistList.addAll(response.body());

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<WishlistResponse>> call, Throwable t) {

            }
        });
        return mWishlistList;
    }

    public List<WishlistResponse> get(){
        mWishlistList.clear();
        Call<List<WishlistResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .getWishlist();
        call.enqueue(new Callback<List<WishlistResponse>>() {
            @Override
            public void onResponse(Call<List<WishlistResponse>> call, Response<List<WishlistResponse>> response) {
                if(response.code()==200){
                    mWishlistList.addAll(response.body());
                    Toast.makeText(mContext, mWishlistList.get(0).getName(),Toast.LENGTH_SHORT).show();
                    int wishlistItems = mWishlistList.size();
                   new SharedPreferencesConfig(mContext).saveWishlistInformation(String.valueOf(wishlistItems));
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<WishlistResponse>> call, Throwable t) {
            }
        });
        return mWishlistList;
    }

}
