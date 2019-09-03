package tech.muva.academy.android_shoppa.ui.activities;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.RetrofitClient;
import tech.muva.academy.android_shoppa.TabAdapter;

import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.auth.LoginDialogFragment;
import tech.muva.academy.android_shoppa.models.Category;
import tech.muva.academy.android_shoppa.settings.SettingsActivity;

import tech.muva.academy.android_shoppa.ui.fragments.DynamicFragment;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CURRENT_POSITION ="tech.muva.academy.android_shoppa.ui.activities.Postition" ;
    private static String SHOW_CATEGORY = "tech.muva.academy.android_shoppa.ui.activities.category";
    public static ImageView navDrawerImageView, bottomAppBarCart, bottomAppBarWishlist, bottomAppBarHome;
    public static TextView navDrawerUsernameTextView, navDrawerEmailTextView, wishlistCounter, cartCounter;
    private static SharedPreferencesConfig sharedPreferencesConfig;
    private List<Category> categories = new ArrayList<>();
    private String categoryname;
    TabLayout tabLayout;


    @Override
    protected void onResume() {
        super.onResume();

        cartCounter.setText(sharedPreferencesConfig.readBuyerCart());
        wishlistCounter.setText(sharedPreferencesConfig.readBuyerWishlist());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        categoryname = getIntent().getStringExtra(SHOW_CATEGORY);

        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        bottomAppBarCart = findViewById(R.id.bottm_cartmage);
        bottomAppBarWishlist = findViewById(R.id.bottom_Wishlist);
        bottomAppBarHome = findViewById(R.id.bottom_home);

        bottomAppBarCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new SharedPreferencesConfig(ProductActivity.this).isloggedIn()) {
                    new LoginDialogFragment(ProductActivity.this).startDialog(getSupportFragmentManager());
                } else {

                    Intent intentCart = new Intent(ProductActivity.this, CartActivity.class);
                    intentCart.putExtra(CURRENT_POSITION,"cart" );
                    startActivity(intentCart);
                }

            }
        });

        bottomAppBarWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new SharedPreferencesConfig(ProductActivity.this).isloggedIn()) {
                    new LoginDialogFragment(ProductActivity.this).startDialog(getSupportFragmentManager());
                } else {

                    Intent intentCart = new Intent(ProductActivity.this, CartActivity.class);
                    intentCart.putExtra(CURRENT_POSITION,"wishlist" );
                    startActivity(intentCart);
                }
            }
        });

        bottomAppBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductActivity.this, ProductActivity.class));

            }
        });

        Toolbar mainToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);

        cartCounter = findViewById(R.id.cartCounter);
        wishlistCounter = findViewById(R.id.wishlistCounter);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navDrawer = navigationView.getHeaderView(0);
        navDrawerImageView = navDrawer.findViewById(R.id.nav_drawer_imageView);
        navDrawerUsernameTextView = navDrawer.findViewById(R.id.nav_drawer_username);
        navDrawerEmailTextView = navDrawer.findViewById(R.id.nav_drawer_email);
        tabLayout = findViewById(R.id.main_tab);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if(categoryname != null){
            Log.d("Stuff", categoryname);
            tabLayout.addTab(tabLayout.newTab().setText(categoryname));
            getTabContent(categoryname);

        }else{
            getCategoryList();

        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabId = (String)tab.getText();
                getTabContent(tabId);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        String status = sharedPreferencesConfig.readBuyerStatus();



        if (status.contentEquals("Active")) {
                String username = sharedPreferencesConfig.readBuyerUsername();
                String email = sharedPreferencesConfig.readBuyerEmail();
                navDrawerUsernameTextView.setText(username);
                navDrawerEmailTextView.setText(email);

                getFirstLetterInCircularBackground(navDrawerImageView, username);

            } else {
                String username = navDrawerUsernameTextView.getText().toString();
                getFirstLetterInCircularBackground(navDrawerImageView, username);

            }


        }

    private void fillTabs(TabLayout tabLayout) {
        if(!categories.isEmpty()) {
            for (int index = 0; index < categories.size(); index++) {
                String fragmentname = categories.get(index).getName();
                Log.d("getItem", categories.get(index).getName());
                tabLayout.addTab(tabLayout.newTab().setText(fragmentname));
            }
        }
        else{
                Log.d("getItem", "No Category Name ");
            }

    }


    public void getTabContent(String tabIndex){
        DynamicFragment tabContentFragment = DynamicFragment.newInstance(tabIndex);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.tabContent, tabContentFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


    @Override
        public void onBackPressed () {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.product_activity, menu);
            return true;
        }

        public static void refreshCounters(){
            cartCounter.setText(sharedPreferencesConfig.readBuyerCart());
            wishlistCounter.setText(sharedPreferencesConfig.readBuyerWishlist());
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                Intent i = new Intent(ProductActivity.this, SettingsActivity.class);
                startActivity(i);
            }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.navigation_cart) {
                Intent intentCart = new Intent(ProductActivity.this, CartActivity.class);
                intentCart.putExtra(CURRENT_POSITION,"cart" );
                startActivity(intentCart);

            } else if (id == R.id.navigation_wishlist) {
                Intent intentCart = new Intent(ProductActivity.this, CartActivity.class);
                intentCart.putExtra(CURRENT_POSITION,"wishlist" );
                startActivity(intentCart);


            } else if (id == R.id.nav_category) {
                Intent i = new Intent(ProductActivity.this, CategoryActivity.class);
                startActivity(i);
                finish();

            } else if (id == R.id.nav_settings) {
                Intent i = new Intent(ProductActivity.this, SettingsActivity.class);
                startActivity(i);

            } else if (id == R.id.navigation_orders) {
                Intent i = new Intent(ProductActivity.this, ViewOrdersActivity.class);
                startActivity(i);

            } else if (id == R.id.nav_share) {
                startActivity(new Intent(ProductActivity.this, CheckoutActivity.class));


            } else if (id == R.id.nav_send) {
                sendEmail();

            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        public void getFirstLetterInCircularBackground (ImageView imageView, String username){
//     ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//     generate random color
//     int color = generator.getRandomColor();

            int color = getResources().getColor(R.color.colorPrimary);
            String firstLetter = String.valueOf(username.charAt(0));

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            imageView.setImageDrawable(drawable);
        }

    public void getCategoryList(){

        Call<List<Category>> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.code() == 200)
                {
                    categories.clear();
//                    Category category=new Category();
//                    category.setName("Featured");
//                    category.setId(0);
//                    categories.add(category);
                    categories.addAll(response.body());
                    fillTabs(tabLayout);


                }
                else
                {
                    Toast.makeText(ProductActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(ProductActivity.this, "this is an actual network failure :( " +
                            "inform the user and possibly retry", Toast.LENGTH_LONG).show();
                    // logging probably not necessary
                }
//                Toast.makeText(mContext, "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void sendEmail() {
        String subject = "My Subject";
        String text = "I just noticed there is an issue with product" + subject;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc2822");
        i.putExtra(Intent.EXTRA_SUBJECT, "Faulty system");
        i.putExtra(Intent.EXTRA_TEXT, "The System isn't working");
        startActivity(i);

    }

    public  static  Intent startSelfIntent(Context context,String category){
        Intent i=new Intent(context,ProductActivity.class);
        i.putExtra(SHOW_CATEGORY,category);
        return i;
    }

}
