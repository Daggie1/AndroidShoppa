package tech.muva.academy.android_shoppa.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.RetrofitClient;
import tech.muva.academy.android_shoppa.adapters.OrdersRecyclerAdapter;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.models.Product;
import tech.muva.academy.android_shoppa.models.ViewedProduct;

public class ViewOrdersActivity extends AppCompatActivity {

    private List<ViewedProduct> mProductList = new ArrayList<>();
    private ArrayList<ViewedProduct> orderProducts = new ArrayList<>();
    private OrdersRecyclerAdapter ordersRecyclerAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        RecyclerView recyclerView = findViewById(R.id.orders_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView.hasFixedSize();
        getOrderFromBackend();

        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        ordersRecyclerAdapter = new OrdersRecyclerAdapter(context,orderProducts);

        recyclerView.setAdapter(ordersRecyclerAdapter);


    }

    private void getOrderFromBackend() {
            Call<List<ViewedProduct>> call = RetrofitClient.getInstance(context)
                    .getApiConnector()
                    .getProducts();

            call.enqueue(new Callback<List<ViewedProduct>>() {
                @Override
                public void onResponse(Call<List<ViewedProduct>> call, Response<List<ViewedProduct>> response) {
                    if(response.code() == 200)
                    {

                        orderProducts.clear();
                        mProductList.clear();
                        mProductList.addAll(response.body());
                        orderProducts.addAll(mProductList);
                        ordersRecyclerAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Toast.makeText(ViewOrdersActivity.this, "Code: "+response.code(),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ViewedProduct>> call, Throwable t) {

                        Toast.makeText(ViewOrdersActivity.this, "You're not connected to the internet", Toast.LENGTH_LONG).show();
                }
            });
        }


}
