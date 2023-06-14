package com.greenhuecity.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.greenhuecity.view.fragment.child.StatusOrderFragment;

public class ViewPagerOrderSAdapter extends FragmentStateAdapter {
    public ViewPagerOrderSAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new StatusOrderFragment("Chờ xác nhận");
            case 1: return  new StatusOrderFragment("Đã xác nhận");
            case 2: return  new StatusOrderFragment("Đang được thuê");
            case 3: return new StatusOrderFragment("Đã hoàn thành");
            case 4: return new StatusOrderFragment("Bị hủy");
            default: return new StatusOrderFragment("Đang được thuê");
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
