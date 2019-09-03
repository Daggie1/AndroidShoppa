package tech.muva.academy.android_shoppa.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.RetrofitClient;
import tech.muva.academy.android_shoppa.adapters.CartRecyclerAdapter;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.datamanagers.CartManager;
import tech.muva.academy.android_shoppa.models.CartResponse;
import tech.muva.academy.android_shoppa.models.Checkout;
import tech.muva.academy.android_shoppa.models.Product;
import tech.muva.academy.android_shoppa.models.QuantityResponse;
import tech.muva.academy.android_shoppa.ui.activities.CheckoutActivity;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static tech.muva.academy.android_shoppa.datamanagers.CartManager.mCartList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


    public CartFragment() {
        // Required empty public constructor
    }

    private ArrayList<Product> mCheckoutArrayList = new ArrayList<>();
    private List<Product> mProductList = new ArrayList<>();
    private ArrayList<Checkout> mCheckoutResponses = new ArrayList<>();
    private CartRecyclerAdapter mCartRecyclerAdapter;
    private ArrayList<CartResponse> mCartResponses=new ArrayList<>();
    Integer buyer_id = 5;
    private double total;
    private int items;
    TextView totalText;
    Button checkout;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.hasFixedSize();
        totalText = view.findViewById(R.id.total_text);
        mCartRecyclerAdapter = new CartRecyclerAdapter(getActivity(),mCartResponses,totalText);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mCartRecyclerAdapter);
        getCart();

        checkout = view.findViewById(R.id.checkout_button);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();



            }
        });


        return view;
    }
    public static CartFragment newInstance(){
        CartFragment cartFragment = new CartFragment();
        Bundle args = new Bundle();
        cartFragment.setArguments(args);
        return cartFragment;
    }
    public void getCart(){
        ArrayList<CartResponse> cartResponses;
        mCartResponses.clear();
        Call<List<CartResponse>> call = ServiceGenerator.getInstance(getActivity())
                .getAnotherApiConnector()
                .getCart();
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if(response.code()==200){
                    mCartResponses.addAll(response.body());
                   mCartRecyclerAdapter.notifyDataSetChanged();

                    for(int index = 0; index<mCartResponses.size(); index++){
                        double amount = mCartResponses.get(index).getUnitCost();
                        double quantity = mCartResponses.get(index).getQuantity();
                        total = total + (amount*quantity);

                    }
                    totalText.setText("Ksh "+total);
                    items = mCartResponses.size();

                    int cartItems = mCartResponses.size();
                    new SharedPreferencesConfig(getActivity()).saveCartInformation(String.valueOf(cartItems));


                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {
            }

        });

    }

    public void checkout(){
        mCheckoutResponses.clear();
        Call<List<Checkout>> call = ServiceGenerator.getInstance(getActivity())
                .getAnotherApiConnector()
                .checkout(1);
        call.enqueue(new Callback<List<Checkout>>() {
            @Override
            public void onResponse(Call<List<Checkout>> call, Response<List<Checkout>> response) {
                if(response.code()==201){
                    mCheckoutResponses.addAll(response.body());
                    String phone_number = mCheckoutResponses.get(0).getPhonenumber();
                    String reference_code = mCheckoutResponses.get(0).getReferenceCode();
                    String shipping_region = mCheckoutResponses.get(0).getCity();

                    Intent intent = CheckoutActivity.startSelfIntent(getActivity(), String.valueOf(total), String.valueOf(items), phone_number,reference_code,shipping_region);
                    startActivity(intent);
                    Objects.requireNonNull(getActivity()).finish();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<Checkout>> call, Throwable t) {
            }

        });

    }


}