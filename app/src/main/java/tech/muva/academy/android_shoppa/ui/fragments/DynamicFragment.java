package tech.muva.academy.android_shoppa.ui.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.RetrofitClient;
import tech.muva.academy.android_shoppa.adapters.ProductsRecyclerAdapter;
import tech.muva.academy.android_shoppa.models.Category;
import tech.muva.academy.android_shoppa.models.Product;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.models.Testproduct;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends Fragment {


    private static final String CATEGORY_ID ="tech.muva.academy.android_shoppa.ui.fragments.category_id" ;

    public DynamicFragment() {
        // Required empty public constructor
    }
    private Box<Testproduct> mTestproductBox;
    private List<Product> mProductList = new ArrayList<>();
    private ArrayList<Product> categoryProducts = new ArrayList<>();
    public static ArrayList<Testproduct> mArraylist = new ArrayList<>();
    private ProductsRecyclerAdapter mMyRecyclerViewAdapter;
    private List<Testproduct> allproducts =new ArrayList<>();
    int val;
    String fragment_name;
    private LinearLayout errorLyt;
    private RelativeLayout progressBarLyt;
    private ImageView refreshImg;
    private TextView errortxt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_electronics,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.electronics_recycler_view);

        errorLyt=view.findViewById(R.id.erroLyt);
        progressBarLyt=view.findViewById(R.id.progressLyt);
        refreshImg=view.findViewById(R.id.refreshNetImage);
        errortxt=view.findViewById(R.id.errorText);
        //populateRecyclerView();
        recyclerView.hasFixedSize();


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.product_grid_span)));

        mMyRecyclerViewAdapter = new ProductsRecyclerAdapter(getActivity(),categoryProducts,getActivity().getWindowManager().getDefaultDisplay(),getFragmentManager());

        recyclerView.setAdapter(mMyRecyclerViewAdapter);

        getCategorisedProducts();

        return view;

    }
    public static DynamicFragment newInstance(String fragmentname) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
//        args.putInt(CATEGORY_ID,val);
        args.putString("fragment_name",fragmentname);
        fragment.setArguments(args);
        return fragment;
    }

    private void getCategorisedProducts()
    {
        categoryProducts.clear();
        onLoading();

        fragment_name = getArguments().getString("fragment_name",fragment_name);
        final int categoryId=getArguments().getInt(CATEGORY_ID);
        Category category = new Category(fragment_name);

        Call<List<Product>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getProducts(category);
//                .getProducts(String.valueOf(categoryId));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                onLoaded();
                if(response.code() == 200)
                {
                    if(!response.body().isEmpty()){
                        Log.d(TAG, "onResponse: product{"+response.body().get(0).getName()+"} ");
                        mProductList.addAll(response.body());
                        categoryProducts.addAll(mProductList);


                    }
                  else{
                        Log.d(TAG, "onResponse: categoryID{"+categoryId+"} empty category");
                  }
                }
                else
                {
                    Toast.makeText(getActivity(), "Code: "+response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                onLoaderror(t.getMessage());
                if (t instanceof IOException)
                {
//                    Toast.makeText(getActivity(), "network failure", Toast.LENGTH_LONG).show();
                }
                else
                {
//                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
        mMyRecyclerViewAdapter.notifyDataSetChanged();

    }
public void onLoading(){
        errorLyt.setVisibility(View.GONE);
        progressBarLyt.setVisibility(View.VISIBLE);
}
public void onLoaderror(String errorMessage){
        errorLyt.setVisibility(View.VISIBLE);
        progressBarLyt.setVisibility(View.GONE);
        errortxt.setText(errorMessage);
}
public void onLoaded(){
        errorLyt.setVisibility(View.GONE);
        progressBarLyt.setVisibility(View.GONE);
}
}