package tech.muva.academy.android_shoppa;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.models.Category;
import tech.muva.academy.android_shoppa.ui.fragments.DynamicFragment;

public class TabAdapter extends FragmentStatePagerAdapter {
    private List<Category> categories = new ArrayList<>();
    private Context mContext;

    public TabAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        mContext=context;
        TabAdapter.this.notifyDataSetChanged();
        getCategoryList();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(!categories.isEmpty()){
        for(int index = 0; index<categories.size(); index++){
            String fragmentname = categories.get(position).getName();
            Log.d("position", "categoryPosition: "+position);
            Log.d("position", "categoryIndex: "+index);
//            int categoryId=categories.get(position).getId();
            Log.d("getItem",categories.get(index).getName());
            return DynamicFragment.newInstance(fragmentname);

        }}
        return null;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d("adapttt","getpagetitle");
        Log.d("getTitile",categories.get(position).getName());
        return categories.get(position).getName();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {

        return POSITION_NONE;
    }

    public void getCategoryList(){

        Call<List<Category>> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
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

                    TabAdapter.this.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(mContext, "this is an actual network failure :( " +
                            "inform the user and possibly retry", Toast.LENGTH_LONG).show();
                    // logging probably not necessary
                }
//                Toast.makeText(mContext, "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

}
