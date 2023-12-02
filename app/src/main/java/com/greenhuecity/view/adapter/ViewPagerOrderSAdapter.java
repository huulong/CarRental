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
            case 0: return new StatusOrderFragment("Chờ Xác Nhận");
            case 1: return  new StatusOrderFragment("Đã Xác Nhận");
            case 2: return  new StatusOrderFragment("Đang Được Thuê");
            case 3: return new StatusOrderFragment("Đã Hoàn Thành");
            case 4: return new StatusOrderFragment("Bị Hủy");
            default: return new StatusOrderFragment("Đang được Thuê");
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
