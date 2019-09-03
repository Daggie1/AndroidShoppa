package tech.muva.academy.android_shoppa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import tech.muva.academy.android_shoppa.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SharedPreferencesConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.SHARED_PREFERENCES),Context.MODE_PRIVATE);
    }

    public void saveAuthenticationInformation(String token, String buyerusername, String first_name , String last_name, String email, String status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.BUYER_TOKEN),token);
        editor.putString(context.getResources().getString(R.string.BUYER_USERNAME),buyerusername);
        editor.putString(context.getResources().getString(R.string.BUYER_FIRSTNAME),first_name);
        editor.putString(context.getResources().getString(R.string.BUYER_LASTNAME),last_name);
        editor.putString(context.getResources().getString(R.string.BUYER_EMAIL),email);
        editor.putString(context.getResources().getString(R.string.BUYER_STATUS),status);



        editor.apply();

    }
    public void saveCartInformation(String cartItems){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.BUYER_CART),cartItems);
        editor.apply();
    }
    public void saveWishlistInformation(String wishListItems){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.BUYER_WISHLIST),wishListItems);
        editor.apply();
    }


    public String readBuyerCart(){
        String cartItems;
        cartItems = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_CART), "");
        return cartItems;
    }

    public String readBuyerWishlist(){
        String wishlistItems;
        wishlistItems = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_WISHLIST), "");
        return wishlistItems;
    }

    public String readBuyerUsername(){
        String username;
        username = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_USERNAME),"");
        return  username;
    }

    public String readBuyerFirstname(){
        String firstname;
        firstname = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_FIRSTNAME),"");
        return  firstname;
    }

    public String readBuyerLastname(){
        String lastname;
        lastname = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_LASTNAME),"");
        return  lastname;
    }

    public String readBuyerEmail(){
        String email;
        email = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_EMAIL),"");
        return  email;
    }

    public String readBuyerToken(){
        String token;
        token = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_TOKEN),"");
        return token;
    }

    public String readBuyerStatus(){
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.BUYER_STATUS),"");
        Log.d(TAG, "readBuyerStatus: "+status);
        return status;
    }
    public  boolean isloggedIn(){
        return readBuyerStatus().equals(Constants.ACTIVE_CONSTANT);
    }
    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }
}
