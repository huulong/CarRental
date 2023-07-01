package com.greenhuecity.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.util.NetworkUtils;
import com.greenhuecity.view.activity.CarDetailActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private List<Cars> mList;
    private Context mContext;

    public CarAdapter(List<Cars> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cars car = mList.get(position);
        Glide.with(mContext).load(car.getCar_img()).into(holder.imgCar);
        holder.tvName.setText(car.getCar_name());
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.tvPrice.setText(currencyFormatter.format(car.getPrice()) + "/ ngày");
        holder.tvPower.setText(car.getPower_name());
        holder.tvStatus.setText(car.getStatus());
        Glide.with(mContext).load(car.getBrand_img()).into(holder.imgBrand);
        holder.imgCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(NetworkUtils.isInternetAvailable(mContext)){
                   Intent intent = new Intent(mContext, CarDetailActivity.class);
                   intent.putExtra("car", car);
                   mContext.startActivity(intent);
               }else Toast.makeText(mContext, "Lỗi mạng!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvPower, tvStatus;
        private ImageView imgCar, imgBrand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCar = itemView.findViewById(R.id.imageView_car);
            imgBrand = itemView.findViewById(R.id.imageView_brandCar);
            tvName = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
            tvPower = itemView.findViewById(R.id.textView_power);
            tvStatus = itemView.findViewById(R.id.status);
        }
    }
}