package tech.muva.academy.android_shoppa.ui.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.models.CartResponse;
import tech.muva.academy.android_shoppa.models.Testproduct;
import tech.muva.academy.android_shoppa.models.Testproduct_;
import tech.muva.academy.android_shoppa.adapters.WishListRecyclerAdapter;
import tech.muva.academy.android_shoppa.models.WishlistResponse;
import tech.muva.academy.android_shoppa.object_box.ObjectBox;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishListFragment extends Fragment {

    public Context context;


    public WishListFragment() {
        // Required empty public constructor
    }
    private WishListRecyclerAdapter mWishListRecyclerAdapter;
    private ArrayList<Testproduct> mArrayList = new ArrayList<>();
    private List<Testproduct> allproducts = new ArrayList<>();
    private Box<Testproduct> mTestproductBox;
    private ArrayList<WishlistResponse> mWishlistResponses=new ArrayList<>();
    private WishListRecyclerAdapter mWishlistRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.wishlist_recyclerview);
        mWishlistRecyclerAdapter = new WishListRecyclerAdapter(getActivity(), mWishlistResponses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mWishlistRecyclerAdapter);

        getWishlist();

        return view;
    }
    public static WishListFragment newInstance(){
        WishListFragment wishlistFragment = new WishListFragment();
        Bundle args = new Bundle();
        wishlistFragment.setArguments(args);
        return wishlistFragment;
    }

    public void getWishlist(){
        ArrayList<CartResponse> cartResponses;
        mWishlistResponses.clear();
        Call<List<WishlistResponse>> call = ServiceGenerator.getInstance(getActivity())
                .getAnotherApiConnector()
                .getWishlist();
        call.enqueue(new Callback<List<WishlistResponse>>() {
            @Override
            public void onResponse(Call<List<WishlistResponse>> call, Response<List<WishlistResponse>> response) {
                if(response.code()==200){
                    mWishlistResponses.addAll(response.body());
                    mWishlistRecyclerAdapter.notifyDataSetChanged();
                    int wishlistItems = mWishlistResponses.size();
                    new SharedPreferencesConfig(getActivity()).saveWishlistInformation(String.valueOf(wishlistItems));

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<WishlistResponse>> call, Throwable t) {
            }

        });

    }


}
