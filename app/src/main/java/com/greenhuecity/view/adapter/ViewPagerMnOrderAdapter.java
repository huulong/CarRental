package com.greenhuecity.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.greenhuecity.view.fragment.child.MnOrderFragment;
import com.greenhuecity.view.fragment.child.StatusOrderFragment;

public class ViewPagerMnOrderAdapter extends FragmentStateAdapter {
    public ViewPagerMnOrderAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new MnOrderFragment("Chờ xác nhận");
            case 1: return  new MnOrderFragment("Đã xác nhận");
            case 2: return  new MnOrderFragment("Đang được thuê");
            case 3: return new MnOrderFragment("Đã hoàn thành");
            case 4: return new MnOrderFragment("Bị hủy");
            default: return new MnOrderFragment("Chờ xác nhận");
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}