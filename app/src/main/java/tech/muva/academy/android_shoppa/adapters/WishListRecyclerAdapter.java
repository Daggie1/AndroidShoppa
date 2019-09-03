package tech.muva.academy.android_shoppa.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.datamanagers.CartManager;
import tech.muva.academy.android_shoppa.datamanagers.WishlistManager;
import tech.muva.academy.android_shoppa.models.WishlistResponse;
import tech.muva.academy.android_shoppa.ui.activities.CartActivity;
import tech.muva.academy.android_shoppa.ui.activities.ViewProductDetails;
import tech.muva.academy.android_shoppa.ui.fragments.CartFragment;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class WishListRecyclerAdapter extends RecyclerView.Adapter<WishListRecyclerAdapter.ViewHolder> {
    public static final String ANOTHER_CURRENT_POSITION_VALUE = "tech.muva.academy.android_shoppa.adapters.Wishlist";
    private Context mContext;
    private ArrayList<WishlistResponse> mWishlistResponse;
    private LayoutInflater mInflater;
    WishlistManager wishlistManager;

    public WishListRecyclerAdapter(Context context,ArrayList<WishlistResponse> arrayList){
        mWishlistResponse = arrayList;
        mContext = context;
        wishlistManager = new WishlistManager(mContext);
    }
    @NonNull
    @Override
    public WishListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wishlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishlistResponse wishlist = mWishlistResponse.get(position);
        holder.productname.setText(wishlist.getName());
        holder.productprice.setText(wishlist.getUnitCost().toString());
        Glide.with(mContext).load("http://192.168.100.14:8000/media/"+wishlist.getFeaturedUrl()
        ).placeholder(R.drawable.ic_cart).into(holder.productimage);
        holder.cart_icon.setImageResource(R.drawable.ic_cart_orange);
        holder.delete_icon.setImageResource(R.drawable.ic_delete);
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mWishlistResponse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productname, productprice;
        ImageView productimage;
        ImageView cart_icon, delete_icon;
        Button viewDetails;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.wishlist_product_name);
            productprice = itemView.findViewById(R.id.wishlist_product_price);
            productimage = itemView.findViewById(R.id.wishlist_product_image);
            cart_icon = itemView.findViewById(R.id.wishlist_cart_icon);
            delete_icon = itemView.findViewById(R.id.wishlist_delete_icon);
            viewDetails = itemView.findViewById(R.id.wishlist_button_view_details);

            cart_icon.setOnClickListener(this);
            delete_icon.setOnClickListener(this);
            viewDetails.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int itemId = view.getId();
            if (itemId == R.id.wishlist_cart_icon) {

                new CartManager(mContext).add(mWishlistResponse.get(mCurrentPosition).getId(), 1);
                remove(mWishlistResponse.get(mCurrentPosition).getWishlistId());


            }
            else if (itemId == R.id.wishlist_delete_icon) {
                remove(mWishlistResponse.get(mCurrentPosition).getWishlistId());

            }
            else if(itemId == R.id.wishlist_button_view_details){
                Intent intent = new Intent(mContext, ViewProductDetails.class);
                intent.putExtra(ANOTHER_CURRENT_POSITION_VALUE, mWishlistResponse.get(mCurrentPosition));
                mContext.startActivity(intent);
            }


        }
    }

    public void remove(int wishlistId){
        mWishlistResponse.clear();
        Call<List<WishlistResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .removeFromWishlist(wishlistId);
        call.enqueue(new Callback<List<WishlistResponse>>() {
            @Override
            public void onResponse(Call<List<WishlistResponse>> call, Response<List<WishlistResponse>> response) {
                if(response.code()==200){
                    mWishlistResponse.addAll(response.body());
                    notifyDataSetChanged();
                    int wishlistItems = mWishlistResponse.size();
                    new SharedPreferencesConfig(mContext).saveWishlistInformation(String.valueOf(wishlistItems));

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<WishlistResponse>> call, Throwable t) {

            }
        });
//        notifyDataSetChanged();
    }
}
