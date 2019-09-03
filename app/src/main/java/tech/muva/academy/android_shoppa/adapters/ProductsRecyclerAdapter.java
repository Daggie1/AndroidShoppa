package tech.muva.academy.android_shoppa.adapters;

import android.content.Context;


import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.auth.LoginDialogFragment;
import tech.muva.academy.android_shoppa.customviews.DaggieShadowBuilder;
import tech.muva.academy.android_shoppa.datamanagers.CartManager;
import tech.muva.academy.android_shoppa.datamanagers.WishlistManager;
import tech.muva.academy.android_shoppa.models.Product;
import tech.muva.academy.android_shoppa.models.Testproduct;
import tech.muva.academy.android_shoppa.ui.activities.ProductActivity;
import tech.muva.academy.android_shoppa.ui.activities.ViewProductDetails;
import tech.muva.academy.android_shoppa.utils.Constants;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Created by hp on 6/18/2019.
 */

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>
{
    public static final String CURRENT_POSITION_VALUE =" tech.muva.academy.android_shoppa.current_position" ;
    @NonNull
    private Context mContext;
    private LayoutInflater inflater;
    public static ArrayList<Product> mProductArrayList;
    public static final int CURRENT_POSITION = 361;
    private Box<Testproduct> mTestproductBox;
    private List<Testproduct> allproducts;
    private Display mDisplay;
    private FragmentManager mFragmentManager;
    NumberPicker numberPicker;
    private static int quantity = 1;
    private RelativeLayout mRelativeLayout;


    public ProductsRecyclerAdapter(@NonNull Context context, ArrayList<Product> productArrayList, Display display,FragmentManager fragmentManager) {
        mDisplay=display;
        mContext = context;
        mProductArrayList = productArrayList;
        mFragmentManager=fragmentManager;
    }

    @Override
    public ProductsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_products,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsRecyclerAdapter.ViewHolder holder, int position)
    {

        Product product = mProductArrayList.get(position);
        holder.product_price.setText(product.getUnitCost()+" kSh");
//        if(product.getStrikedPrice==null){
//            holder.striked_product_price.setVisibility(View.GONE);
//        }
        holder.striked_product_price.setText(product.getUnitCost()+" kSh");
        Glide.with(mContext).load("http://192.168.100.14:8000"+product.getFeaturedImageUrl())
                .placeholder(R.drawable.ic_cart).into(holder.productImage);
        holder.product_name.setText(product.getName());
        holder.cart_icon.setImageResource(R.drawable.ic_cart);
        holder.wishlist_icon.setImageResource(R.drawable.valentines_heart);
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount()

    {
       return mProductArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnTouchListener,
            View.OnClickListener,
            GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener,
            View.OnDragListener {
        GestureDetector mGestureDetector;
        ImageView productImage, cart_icon, wishlist_icon, bottomCart;
        TextView product_price, cartCounter;
        TextView product_name;
        TextView striked_product_price;
        public int mCurrentPosition;
        private Rect mCartPositionRectangle;


        public ViewHolder(View itemView) {
            super(itemView);
            mGestureDetector = new GestureDetector(mContext, this);
            productImage = itemView.findViewById(R.id.product_image_recy);
            product_price = itemView.findViewById(R.id.product_price_recy);
            product_name = itemView.findViewById(R.id.product_name_recy);
            striked_product_price = itemView.findViewById(R.id.striked_product_price);
            cart_icon = itemView.findViewById(R.id.cart_icon);
            wishlist_icon = itemView.findViewById(R.id.wish_list_icon);
            bottomCart = ProductActivity.bottomAppBarCart;
            cartCounter = ProductActivity.cartCounter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("SHOPPY:",""+mProductArrayList.get(mCurrentPosition).getDescription());
                    Intent intent = new Intent(mContext, ViewProductDetails.class);
                    intent.putExtra(CURRENT_POSITION_VALUE, mProductArrayList.get(mCurrentPosition));
                    mContext.startActivity(intent);
                }
            });
            productImage.setOnTouchListener(this);
            // bottomCart.setOnTouchListener(this);
            cart_icon.setOnClickListener(this);
            wishlist_icon.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int itemId = view.getId();
            if (itemId == R.id.cart_icon) {

                if (!new SharedPreferencesConfig(mContext).isloggedIn()) {
                    new LoginDialogFragment(mContext).startDialog(mFragmentManager);
                } else {
                    int product_id = mProductArrayList.get(mCurrentPosition).getId();
                    addingItemQuantityPopUp(product_id);
                }
            } else if (itemId == R.id.wish_list_icon) {
                new WishlistManager(mContext).add(mProductArrayList.get(mCurrentPosition).getId());
            }


        }


        private void getCartPosition() {
            mCartPositionRectangle = new Rect();
            bottomCart.getGlobalVisibleRect(mCartPositionRectangle);

            Display display = mDisplay;
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            mCartPositionRectangle.left = mCartPositionRectangle.left - Math.round((int) (width * 0.18));
            mCartPositionRectangle.top = mCartPositionRectangle.bottom - Math.round((int) (width * 0.03));
            mCartPositionRectangle.right = width;
            mCartPositionRectangle.bottom = 0;
        }

        /*
        onTouch
         */
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            getCartPosition();
            mGestureDetector.onTouchEvent(motionEvent);

            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            //initiateShadowDrag
            View.DragShadowBuilder shadowBuilder = new DaggieShadowBuilder(productImage, "http://192.168.100.14:8000/" + mProductArrayList.get(mCurrentPosition).getFeaturedImageUrl());
//startDrag
            Bundle bundle = new Bundle();

            productImage.startDrag(
                    null,  // the data to be dragged
                    shadowBuilder,  // the drag shadow builder
                    null,      // no need to use local data
                    0          // flags (not currently used, set to 0)
            );
            shadowBuilder.getView().setOnDragListener(this);
        }


        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        /*
        onDrag
         */
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {

                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(TAG, "onDrag: drag started.");

                    setDragMode(true);

                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:

                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:

                    Point currentPoint = new Point(Math.round(dragEvent.getX()), Math.round(dragEvent.getY()));
//                Log.d(TAG, "onDrag: x: " + currentPoint.x + ", y: " + currentPoint.y );

                    if (mCartPositionRectangle.contains(currentPoint.x, currentPoint.y)) {
                        bottomCart.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                    } else {
                        bottomCart.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                    }

                    return true;

                case DragEvent.ACTION_DRAG_EXITED:

                    return true;

                case DragEvent.ACTION_DROP:

                    Log.d(TAG, "onDrag: dropped.");

                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "onDrag: ended.");

                    Drawable background = bottomCart.getBackground();
                    if (background instanceof ColorDrawable) {
                        if (((ColorDrawable) background).getColor() == mContext.getResources().getColor(R.color.colorAccent)) {
                            //addCurrentItemToCart();

                            Toast.makeText(mContext, "added to cart", Toast.LENGTH_LONG).show();
                        }
                    }
                    bottomCart.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    setDragMode(false);
                    return true;

                // An unknown action type was received.
                default:
                    Log.e(TAG, "Unknown action type received by OnStartDragListener.");
                    break;

            }

            return false;
        }


        private void setDragMode(boolean isDragging) {
            if (isDragging) {
                bottomCart.setImageResource(R.drawable.ic_add_black_24dp);
                cartCounter.setVisibility(View.GONE);
            } else {
                bottomCart.setImageResource(R.drawable.ic_cart);
                cartCounter.setVisibility(View.VISIBLE);
            }
        }


        public int addingItemQuantityPopUp(final int product_id) {


            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

            // Inflate the custom layout/view
            View customView = inflater.inflate(R.layout.item_quantity_pop_up_layout2, null);

            numberPicker = customView.findViewById(R.id.numberPicker);

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
                    quantity = picker.getValue();
                }
            });


            addQuantityFromPopUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CartManager(mContext).add(product_id, quantity);
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


            return quantity;
        }
    }


}

