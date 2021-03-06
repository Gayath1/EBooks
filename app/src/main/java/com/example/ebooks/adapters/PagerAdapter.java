package com.example.ebooks.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ebooks.CategoriesFragment;
import com.example.ebooks.HomeFragment;
import com.example.ebooks.OrderFragment;
import com.example.ebooks.ProfileFragment;
import com.example.ebooks.UserFragment;

public class PagerAdapter extends FragmentStateAdapter {
    private int tabCount;
    private boolean isAdmin;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity, int count, boolean isAdmin) {
        super(fragmentActivity);
        this.tabCount = count;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (isAdmin) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return CategoriesFragment.newInstance();
                case 2:
                    return UserFragment.newInstance();
                case 3:
                    return OrderFragment.newInstance();
                default:
                    return null;
            }
        } else {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return CategoriesFragment.newInstance();
                case 2:
                    return OrderFragment.newInstance();
                case 3:
                    return ProfileFragment.newInstance();
                default:
                    return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
