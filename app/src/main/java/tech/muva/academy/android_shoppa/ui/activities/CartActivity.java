package tech.muva.academy.android_shoppa.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.adapters.CartTabLayoutAdapter;
import tech.muva.academy.android_shoppa.ui.fragments.CartFragment;
import tech.muva.academy.android_shoppa.ui.fragments.WishListFragment;

public class CartActivity extends AppCompatActivity {

    CartFragment cartFragment = CartFragment.newInstance();
    WishListFragment wishListFragment = WishListFragment.newInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        TabLayout tabLayout = findViewById(R.id.cart_tab);
        tabLayout.addTab(tabLayout.newTab().setText("WishList"));
        tabLayout.addTab(tabLayout.newTab().setText("Cart"));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tabContent2, new WishListFragment())
                .commit();

//        if(getIntent().getStringExtra(ProductActivity.CURRENT_POSITION) == "cart"){
//            Log.d("Meeh", getIntent().getStringExtra(ProductActivity.CURRENT_POSITION));
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.tabContent2, new CartFragment())
//                    .commit();
//        }
//        else{
//            Log.d("Meeh", getIntent().getStringExtra(ProductActivity.CURRENT_POSITION));
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.tabContent2, new WishListFragment())
//                    .commit();
//        }



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabId = (String)tab.getText();
                if(tabId == "Cart"){

                    loadFragment(cartFragment);

                }
                else{
                    loadFragment(wishListFragment);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Log.d("stuff", "A fragment is loaded");
        ft.replace(R.id.tabContent2, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }


}
