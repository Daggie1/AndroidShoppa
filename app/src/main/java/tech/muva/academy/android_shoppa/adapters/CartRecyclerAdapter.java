package tech.muva.academy.android_shoppa.adapters;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import tech.muva.academy.android_shoppa.models.CartResponse;
import tech.muva.academy.android_shoppa.models.QuantityResponse;
import tech.muva.academy.android_shoppa.utils.Constants;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<CartResponse> mCartArrayList;
    private static int product_quantity;
    CartManager cartManager;
    private  TextView mTotalTxt;

    public CartRecyclerAdapter(Context context, ArrayList<CartResponse> list,TextView totalTxt) {
        mContext = context;
        cartManager = new CartManager(context);
mTotalTxt=totalTxt;
        mCartArrayList = list;
    }

    @NonNull
    @Override
    public CartRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartResponse cart = mCartArrayList.get(position);
        holder.product_name.setText(cart.getName());
        holder.quantity.setText(String.valueOf(cart.getQuantity().intValue()));
        holder.product_price.setText("Kshs "+cart.getUnitCost().toString());
        Glide.with(mContext).load("http://192.168.100.14:8000/media/"+cart.getFeaturedUrl()
        ).placeholder(R.drawable.ic_cart).into(holder.cart_image);
        holder.mCurrentPosition = position;

    }



    @Override
    public int getItemCount() {
        return mCartArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView cart_image;
        TextView product_name, product_price,quantity;
        Button btn_remove, btn_edit;
        public int mCurrentPosition;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_image = itemView.findViewById(R.id.cart_productImage);
            product_name = itemView.findViewById(R.id.cartProductName);
            product_price = itemView.findViewById(R.id.cartPriceTxt);
            quantity=itemView.findViewById(R.id.cartQtyText);
            btn_remove = itemView.findViewById(R.id.cartRemoveBtn);
            btn_edit = itemView.findViewById(R.id.cartEditBtn);

            btn_edit.setOnClickListener(this);
            btn_remove.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemId = view.getId();
            if (itemId == R.id.cartRemoveBtn) {

                remove(mCartArrayList.get(mCurrentPosition).getOrderId());

            } else if (itemId == R.id.cartEditBtn) {
                int order_id = mCartArrayList.get(mCurrentPosition).getOrderId();
                addingItemQuantityPopUp(order_id);
            }

        }

        public int addingItemQuantityPopUp(final int order_id) {


            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

            // Inflate the custom layout/view
            View customView = inflater.inflate(R.layout.item_quantity_pop_up_layout2, null);

            NumberPicker numberPicker = customView.findViewById(R.id.numberPicker);

            numberPicker.setMaxValue(20);
            numberPicker.setMinValue(1);


            // Initialize a new instance of popup window
            final PopupWindow mPopupWindow = new PopupWindow(
                    customView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Set an elevation value for popup window
            // Call requires API level 21
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }

            ImageButton closePopUpButton = customView.findViewById(R.id.close_pop_up);
            ImageButton addQuantityFromPopUp = customView.findViewById(R.id.button_add_quantity);

            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    product_quantity = picker.getValue();
                }
            });


            addQuantityFromPopUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(order_id, product_quantity);
                    mPopupWindow.dismiss();

                }
            });


            // Set a click listener for the popup window close button
            closePopUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Dismiss the popup window
                    mPopupWindow.dismiss();
                }
            });


            mPopupWindow.showAtLocation(itemView, Gravity.CENTER, 0, 0);


            return product_quantity;
        }

    }

    public void remove(int orderId){
        mCartArrayList.clear();
        Call<List<CartResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .removeCart(orderId);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if(response.code()==201){
                    mCartArrayList.addAll(response.body());
                    notifyDataSetChanged();
                    double total=0.0;
                    for(int index = 0; index<mCartArrayList.size(); index++){
                        double amount = mCartArrayList.get(index).getUnitCost();
                        double quantity = mCartArrayList.get(index).getQuantity();
                        total = total + (amount*quantity);

                    }
                    mTotalTxt.setText("Ksh "+total);
                    int cartItems = mCartArrayList.size();
                    new SharedPreferencesConfig(mContext).saveCartInformation(String.valueOf(cartItems));
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {

            }
        });
    }

    public void update(int orderId,int quantity){
        mCartArrayList.clear();
        QuantityResponse quantityR = new QuantityResponse(String.valueOf(quantity));
        Call<List<CartResponse>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .updateCart(orderId,quantityR);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if(response.code()==200){
                    mCartArrayList.addAll(response.body());
                    notifyDataSetChanged();
                    double total=0.0;
                    for(int index = 0; index<mCartArrayList.size(); index++){
                        double amount = mCartArrayList.get(index).getUnitCost();
                        double quantity = mCartArrayList.get(index).getQuantity();
                        total = total + (amount*quantity);

                    }
                    mTotalTxt.setText("Ksh "+total);
                    int cartItems = mCartArrayList.size();
                    new SharedPreferencesConfig(mContext).saveCartInformation(String.valueOf(cartItems));
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {

            }
        });
    }

}
