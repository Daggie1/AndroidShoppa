package tech.muva.academy.android_shoppa.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.RetrofitClient;
import tech.muva.academy.android_shoppa.adapters.CategoryListAdapter;
import tech.muva.academy.android_shoppa.adapters.OrdersRecyclerAdapter;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.models.Category;
import tech.muva.academy.android_shoppa.models.ViewedProduct;

public class CategoryActivity extends AppCompatActivity {

    private List<Category> mCategoryList = new ArrayList<>();
    private ArrayList<Category> categoryName = new ArrayList<>();
    private CategoryListAdapter categoryListAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView.hasFixedSize();
        getOrderFromBackend();

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        categoryListAdapter = new CategoryListAdapter(getApplicationContext(), categoryName);

        recyclerView.setAdapter(categoryListAdapter);


    }

    private void getOrderFromBackend() {
        Call<List<Category>> call = RetrofitClient.getInstance(context)
                .getApiConnector()
                .getUnfilteredCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.code() == 200)
                {

                    categoryName.clear();
                    mCategoryList.clear();
                    mCategoryList.addAll(response.body());
                    categoryName.addAll(mCategoryList);
                    categoryListAdapter.notifyDataSetChanged();

                }
                else
                {
                    Toast.makeText(CategoryActivity.this, "Code: "+response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

                Toast.makeText(CategoryActivity.this, "You're not connected to the internet", Toast.LENGTH_LONG).show();
            }
        });
    }


}
