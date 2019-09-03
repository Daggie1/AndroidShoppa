package tech.muva.academy.android_shoppa.ui.activities;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;



import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.adapters.ProductsRecyclerAdapter;
import tech.muva.academy.android_shoppa.adapters.ViewPagerAdapter;
import tech.muva.academy.android_shoppa.adapters.WishListRecyclerAdapter;
import tech.muva.academy.android_shoppa.datamanagers.CartManager;
import tech.muva.academy.android_shoppa.datamanagers.WishlistManager;
import tech.muva.academy.android_shoppa.models.Product;
import tech.muva.academy.android_shoppa.models.WishlistResponse;

import static android.view.View.GONE;

public class ViewProductDetails extends AppCompatActivity implements View.OnClickListener {

    private static final int DEFAULT_INITIAL_POSITION = -1;
    private int maxInventoryQuantity = 20, minInventoryQuantity= 1;
    private static int quantity = 1;
    private static int number = 1;
    private RelativeLayout mRelativeLayout;
    private Context mContext;
    private Activity mActivity;
    private View contentView;
    private View loadingView;
    private Toolbar mTopToolbar;
    private PopupWindow mPopupWindow;
    private Product product;
    private WishlistResponse wproduct;
    private ImageView productImage, wishlist, cart;
    private TextView productName, productPrice, productStrikedPrice, productDescription;
    private Button toggleDescription, addToWishlist, buyProduct;
    private String imageURL;
    ViewPagerAdapter viewPagerAdapter;
    NumberPicker numberPicker;
    ViewPager viewPager;
    TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_details);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        buyProduct = findViewById(R.id.buy_product);
        addToWishlist = findViewById(R.id.add_to_wishlist);
        toggleDescription = findViewById(R.id.toggle_description);
        contentView = findViewById(R.id.content_description);
        loadingView = findViewById(R.id.content_image);
        viewPager = findViewById(R.id.myViewPager);
        tabLayout=findViewById(R.id.tab_layout);
        productImage=findViewById(R.id.detail_product_image);

        ImageButton backPressed = findViewById(R.id.action_back);
        RatingBar ratingBar = findViewById(R.id.rating);
        wishlist = findViewById(R.id.product_add_wishlist);
        cart = findViewById(R.id.product_add_cart);
        productStrikedPrice = findViewById(R.id.product_former_price);
        productDescription = findViewById(R.id.product_detailed_description);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price_recy);


        viewPagerAdapter = new ViewPagerAdapter(this);
        tabLayout.setupWithViewPager(viewPager,true);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        buyProduct.setOnClickListener(this);
        addToWishlist.setOnClickListener(this);
        toggleDescription.setOnClickListener(this);
        ratingBar.setOnClickListener(this);
        backPressed.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        cart.setOnClickListener(this);






        // Initially hide the content view.
        contentView.setVisibility(GONE);


        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = ViewProductDetails.this;
        mRelativeLayout = findViewById(R.id.rl);



    }


    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().hasExtra(ProductsRecyclerAdapter.CURRENT_POSITION_VALUE)){
            product=getIntent().getParcelableExtra(ProductsRecyclerAdapter.CURRENT_POSITION_VALUE);
            fillData(number);
        }else if(getIntent().hasExtra(WishListRecyclerAdapter.ANOTHER_CURRENT_POSITION_VALUE)){
            wproduct=getIntent().getParcelableExtra(WishListRecyclerAdapter.ANOTHER_CURRENT_POSITION_VALUE);
            number = 0;
            wishlist.setVisibility(GONE);
            addToWishlist.setVisibility(GONE);
            fillData(number);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(ViewProductDetails.this, ProductActivity.class);
                    startActivity(intent);
                    finish();

                    break;
                case R.id.heart:
                    Toast.makeText(ViewProductDetails.this,"This goes somewhere",Toast.LENGTH_SHORT).show();

                    break;
                case R.id.navigation_cart:
                    Toast.makeText(ViewProductDetails.this,"This goes somewhere",Toast.LENGTH_SHORT).show();

                    break;
            }
            return false;
        }
    };





    @Override
    public void onClick(View view) {
        int id = view.getId();
         if (id == R.id.buy_product) {
            addingItemQuantityPopUp();

        }
        else if (id == R.id.toggle_description){
            toggleDescriptionVisibility();
        }
        else if(id == R.id.rating){
            Toast.makeText(ViewProductDetails.this,"You can only rate it when you buy it",Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.product_add_wishlist){
            new WishlistManager(mContext).add(product.getId());

        }
        else if(id == R.id.action_back){
            Intent intent = new Intent(ViewProductDetails.this, ProductActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.product_add_cart){
            addingItemQuantityPopUp();

        }
        else if(id == R.id.add_to_wishlist){
            new WishlistManager(mContext).add(product.getId());
        }
    }


    public int  addingItemQuantityPopUp() {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.item_quantity_pop_up_layout,null);

        numberPicker = customView.findViewById(R.id.numberPicker);

        numberPicker.setMaxValue(maxInventoryQuantity);
        numberPicker.setMinValue(minInventoryQuantity);


        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        Button closePopUpButton = customView.findViewById(R.id.close_pop_up);
        Button addQuantityFromPopUp = customView.findViewById(R.id.button_add_quantity);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                quantity = picker.getValue();
            }
        });



        addQuantityFromPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Product To Cart Depending On Whether The Product Was Viewed From Wishlist Or Product List.
                if(number !=0){
                    new CartManager(mContext).add(product.getId(),quantity);
                    Intent intent = new Intent(ViewProductDetails.this, ProductActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    new CartManager(mContext).add(wproduct.getId(),quantity);
                    Intent intent = new Intent(ViewProductDetails.this, CartActivity.class);
                    startActivity(intent);
                    finish();
            }
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


        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);


        return quantity;
    }


    private void toggleDescriptionVisibility() {
        Button toggleDescription = findViewById(R.id.toggle_description);

        if(toggleDescription.getText().toString().equals("Description")){
            contentView.setAlpha(1f);
            contentView.setVisibility(View.VISIBLE);
            loadingView.scrollTo(0, loadingView.getBottom());
            toggleDescription.setText("Hide Description");
        }else{
            contentView.setAlpha(0f);
            contentView.setVisibility(GONE);
            loadingView.scrollTo(0, loadingView.getBottom());
            toggleDescription.setText("Description");
        }
    }



private void fillData(int num){
//Differentiate Between Wishlist product Detail View And Individual Product Detail View
    if(num == 1){
        imageURL = "http://192.168.100.14:8000"+product.getFeaturedImageUrl();
        ViewPagerAdapter.images = new String[]{imageURL};
        productName.setText(product.getName());
        productPrice.setText(product.getUnitCost());
        productStrikedPrice.setText(product.getUnitCost());
        productDescription.setText(product.getShortDescription());
    }else{
        imageURL = "http://192.168.100.14:8000/media/"+wproduct.getFeaturedUrl();
        ViewPagerAdapter.images = new String[]{imageURL};
        productName.setText(wproduct.getName());
        productPrice.setText(wproduct.getUnitCost().toString());
        productStrikedPrice.setText(wproduct.getUnitCost().toString());
        productDescription.setText(wproduct.getShortDescription());
    }


    viewPager.setAdapter(viewPagerAdapter);
    tabLayout.setupWithViewPager(viewPager,true);

}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

}
