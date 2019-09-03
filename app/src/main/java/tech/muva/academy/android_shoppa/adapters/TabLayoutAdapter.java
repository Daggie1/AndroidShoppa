package tech.muva.academy.android_shoppa.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


import tech.muva.academy.android_shoppa.models.Categories;
import tech.muva.academy.android_shoppa.models.Category;


public class TabLayoutAdapter extends FragmentPagerAdapter
{
    int number_of_categories;
    private Context mContext;
    Categories categories;
    List<Category> categories2 ;


    public TabLayoutAdapter(@NonNull FragmentManager fm) {
        super(fm);
        categories2= new ArrayList<>();
                  Log.d("consttt", "TabLayoutAdapter:i am here ");

    }

    @Override
    public Fragment getItem(int position) {

        number_of_categories = 4;
                  Log.d("consttt", "TabLayoutAdapter:init categories ");

        if (number_of_categories != 0){
            for (int index = 0; index<= number_of_categories;index++  )
            {    Log.d("consttt", "TabLayoutAdapter:new fragments ");

                //return DynamicFragment.newInstance(position, fragmentname);
            }
        }
        else {
            Log.d("consttt", "getItem: else");
            return null;
        }

        return null;
    }


    @Override
    public int getCount() {
         Log.d("consttt", "getItem: kljb");

        return number_of_categories;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d("consttt", "getItem: ;hb");

        return "category";

    }
}


