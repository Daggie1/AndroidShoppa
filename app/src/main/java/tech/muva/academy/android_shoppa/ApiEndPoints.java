package tech.muva.academy.android_shoppa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tech.muva.academy.android_shoppa.api.model.ChangePassword;
import tech.muva.academy.android_shoppa.api.model.LoginModel;
import tech.muva.academy.android_shoppa.api.model.RegisterModel;
import tech.muva.academy.android_shoppa.api.model.UsersModel;
import tech.muva.academy.android_shoppa.models.CartResponse;
import tech.muva.academy.android_shoppa.models.Categories;
import tech.muva.academy.android_shoppa.models.Category;
import tech.muva.academy.android_shoppa.models.OrderProduct;
import tech.muva.academy.android_shoppa.models.Product;
import tech.muva.academy.android_shoppa.models.ViewedProduct;

public interface ApiEndPoints {
//    @FormUrlEncoded
    @GET("android/category/")
    Call<List<Category>> getCategories();

    @GET("android/unfilteredcategory/")
    Call<List<Category>> getUnfilteredCategories();

    @POST("android/login/")
    Call<UsersModel> login(@Body LoginModel login);

    @POST("android/signup/")
    Call<UsersModel> register(@Body RegisterModel registerModel);

    @GET("android/orders/")
    Call<List<ViewedProduct>> getProducts();

    @POST("rest_auth/password/change/")
    Call<UsersModel> changePassword(@Header ("Authorization") String Token, @Body ChangePassword changePassword);


    @POST("android/getproducts/")
    Call<List<Product>> getProducts(@Body Category category);


    @POST("api/add_to_cart")
    Call<OrderProduct> addToCart(@Body OrderProduct orderProduct);

    @GET("api/cart/{buyer_id}")
    Call<List<Product>> cart(@Path("buyer_id") Integer buyer_id);

    //cart endpoints
    @GET("android/getcartproducts")
    Call<List<CartResponse>> getCart(@Header("Authorization") String token);
    @POST("android/addtocart/")
    Call<List<CartResponse>> addToCart(@Body int productId, @Body int quantity, @Header("Authorization") String token);
    @PUT("android/updatecart/<{order_id}/")
    Call<List<CartResponse>> updateCart(@Path ("order_id") int orderId, @Body int quantity, @Header("Authorization") String token);
    @DELETE("android/removefromcart/{order_id}")
    Call<List<CartResponse>> removeCart(@Path("order_id") int orderId, @Header("Authorization") String token);
}
