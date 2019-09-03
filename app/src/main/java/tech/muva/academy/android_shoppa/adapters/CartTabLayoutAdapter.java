package tech.muva.academy.android_shoppa.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import tech.muva.academy.android_shoppa.ui.fragments.WishListFragment;
import tech.muva.academy.android_shoppa.ui.fragments.CartFragment;

public class CartTabLayoutAdapter extends FragmentStatePagerAdapter {

    public CartTabLayoutAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new CartFragment();
            case 1:
                return new WishListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Cart";
            case 1:
                return "WishList";
            default:
                return null;
        }
    }
}
