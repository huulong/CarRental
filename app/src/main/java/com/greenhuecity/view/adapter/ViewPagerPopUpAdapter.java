package com.greenhuecity.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.SlidePopup;



import java.util.List;

public class ViewPagerPopUpAdapter extends PagerAdapter {
    List<SlidePopup> mList;
    Context mContext;

    public ViewPagerPopUpAdapter(List<SlidePopup> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else return 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.slide_popup, container, false);


//        ánh xạ
        ImageView img = layout.findViewById(R.id.imageViewPopup);
        TextView tvTitle = layout.findViewById(R.id.textViewTitle);
        TextView tvContent = layout.findViewById(R.id.textView_content);

//        set data
        SlidePopup slidePopup = mList.get(position);
        Glide.with(mContext).load(slidePopup.getPhoto()).into(img);
        tvTitle.setText(slidePopup.getTitle());
        tvContent.setText(slidePopup.getContent());
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
