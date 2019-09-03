package tech.muva.academy.android_shoppa.datamanagers;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
import tech.muva.academy.android_shoppa.ui.activities.ProductActivity;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class CartManager {
   Context mContext;
   String orderId;

public  static ArrayList<CartResponse> mCartList;
    public CartManager(Context context) {

        mContext = context;
        mCartList=new ArrayList<>();
    }



    public ArrayList<CartResponse> add(int productId,int quantity){
        mCartList.clear();
        QuantityResponse quantityR = new QuantityResponse(String.valueOf(quantity));
        Call<List<CartResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .addToCart(productId,quantityR);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if(response.code()==201){
                    mCartList.addAll(response.body());
                    Toast.makeText(mContext,"Added To Cart",Toast.LENGTH_SHORT).show();
                    int cartItems = mCartList.size();
                    new SharedPreferencesConfig(mContext).saveCartInformation(String.valueOf(cartItems));
                    ProductActivity.refreshCounters();


                }
                else{
                    Toast.makeText(mContext,"Not Added To Cart, Try Again",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {

            }
        });
        return mCartList;
    }
    public ArrayList<CartResponse> remove(int orderId){
        mCartList.clear();
        Call<List<CartResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .removeCart(orderId);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if(response.code()==201){
                    mCartList.addAll(response.body());
                    int cartItems = mCartList.size();
                    new SharedPreferencesConfig(mContext).saveCartInformation(String.valueOf(cartItems));
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {

            }
        });
        return mCartList;
    }
    public ArrayList<CartResponse> update(int orderId,int quantity){
        mCartList.clear();
        QuantityResponse quantityR = new QuantityResponse(String.valueOf(quantity));
        Call<List<CartResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .updateCart(orderId,quantityR);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if(response.code()==200){
                    mCartList.addAll(response.body());
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {

            }
        });
        return mCartList;
    }
    public ArrayList<CartResponse> get(){
        ArrayList<CartResponse> cartResponses;
        mCartList.clear();
        Call<List<CartResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .getCart();
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if(response.code()==200){
                    mCartList.addAll(response.body());
                    Toast.makeText(mContext, mCartList.get(0).getName(),Toast.LENGTH_SHORT).show();
                    int cartItems = mCartList.size();
                    new SharedPreferencesConfig(mContext).saveCartInformation(String.valueOf(cartItems));

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {
            }

        });
      return mCartList;
    }

}
