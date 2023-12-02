package com.greenhuecity.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.LeaseCar;
import com.greenhuecity.data.model.RentManagement;
import com.greenhuecity.view.activity.RentMnDetailActivity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaseCarAdapter extends RecyclerView.Adapter<LeaseCarAdapter.ViewHolder> {
    private List<LeaseCar> mList;
    private Context mContext;


    public LeaseCarAdapter(List<LeaseCar> list, Context context) {
        mList = list;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_lease_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        LeaseCar leaseCar = mList.get(position);

        Glide.with(mContext).load(leaseCar.getCar_img()).into(holder.imgCar);
        if (leaseCar.getDistributor_photo() != null)
            Glide.with(mContext).load(leaseCar.getDistributor_photo()).into(holder.circleImageView);
        holder.tvNameCar.setText(leaseCar.getCar_name());
        holder.tvFromTime.setText(leaseCar.getFrom_time());
        holder.tvEndTime.setText(leaseCar.getEnd_time());
        holder.tvUserName.setText(leaseCar.getName());
        holder.tvPrice.setText(currencyFormatter.format(leaseCar.getPrice()) + "/Ngày");
        holder.tvStatus.setText(leaseCar.getStatus());
        String approve = leaseCar.getApprove();
        if (approve.equals("Yes")) holder.tvApprove.setText("Đã Cấp Phép");
        else if (approve.equals("Pending")) holder.tvApprove.setText("Chờ Cấp Phép");
        else if (approve.equals("No")) holder.tvApprove.setText("Không Cấp Phép");

    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameCar, tvPrice, tvFromTime, tvEndTime, tvUserName, tvApprove, tvStatus;
        private ImageView imgCar;
        private CircleImageView circleImageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCar = itemView.findViewById(R.id.imageView_car);
            tvNameCar = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
            circleImageView = itemView.findViewById(R.id.profileCircleImageView);
            tvUserName = itemView.findViewById(R.id.usernameTextView);
            tvFromTime = itemView.findViewById(R.id.textView_fromTime);
            tvEndTime = itemView.findViewById(R.id.textView_endTime);
            cardView = itemView.findViewById(R.id.cardView);
            tvApprove = itemView.findViewById(R.id.textView_approve);
            tvStatus = itemView.findViewById(R.id.textView_status);
        }
    }
}