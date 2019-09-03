package tech.muva.academy.android_shoppa.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tech.muva.academy.android_shoppa.api.model.ChangePassword;
import tech.muva.academy.android_shoppa.api.model.LoginModel;
import tech.muva.academy.android_shoppa.api.model.RegisterModel;
import tech.muva.academy.android_shoppa.api.model.UsersModel;
import tech.muva.academy.android_shoppa.models.Cart;
import tech.muva.academy.android_shoppa.models.CartResponse;
import tech.muva.academy.android_shoppa.models.Category;
import tech.muva.academy.android_shoppa.models.Checkout;
import tech.muva.academy.android_shoppa.models.Product;
import tech.muva.academy.android_shoppa.models.QuantityResponse;
import tech.muva.academy.android_shoppa.models.ViewedProduct;
import tech.muva.academy.android_shoppa.models.WishlistResponse;

public interface UserClient {

    @POST("android/login/")
    Call<UsersModel> login(@Body LoginModel login);

    @POST("android/signup/")
    Call<UsersModel> register(@Body RegisterModel registerModel);

    @GET("android/orders/")
    Call<List<ViewedProduct>> getProducts();

    @POST("rest_auth/password/change/")
    Call<UsersModel> changePassword(@Header ("Authorization") String Token, @Body ChangePassword changePassword);

    @GET("android/category/")
    Call<List<Category>> getCategories();

    @POST("android/getproducts/")
    Call<List<Product>> getSpecificProducts(@Body Category category);

    //cart endpoints
    @GET("android/getcartproducts")
    Call<List<CartResponse>> getCart();

    @POST("android/addtocart/{product_id}/")
    Call<List<CartResponse>> addToCart(@Path ("product_id") int productId, @Body QuantityResponse quantityResponse);

    @PUT("android/updatecart/{order_id}/")
    Call<List<CartResponse>> updateCart(@Path ("order_id") int orderId, @Body QuantityResponse quantityResponse);

    @DELETE("android/removefromcart/{order_id}/")
    Call<List<CartResponse>> removeCart(@Path("order_id") int orderId);


    //wishlist endpoints

    @GET("android/getwishlistproducts/")
    Call<List<WishlistResponse>> getWishlist();

    @POST("android/addtowishlist/{product_id}/")
    Call<List<WishlistResponse>> addToWishlist(@Path ("product_id") int productId);

    @DELETE("android/removefromwishlist/{wishlist_id}/")
    Call<List<WishlistResponse>> removeFromWishlist(@Path("wishlist_id") int wishlistId);

    //checkout endpoints

    @POST("android/checkout/{region_id}/")
    Call<List<Checkout>> checkout(@Path ("region_id") int region);



}


