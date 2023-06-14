package com.greenhuecity.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.RentManagement;
import com.greenhuecity.data.model.UserOrder;
import com.greenhuecity.itf.OnClickButtonUserOrder;
import com.greenhuecity.view.activity.RentMnDetailActivity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {
    private List<UserOrder> mList;
    private Context mContext;
    public OnClickButtonUserOrder listener;

    public void setOnClickButtonOrder(OnClickButtonUserOrder listener) {
        this.listener = listener;
    }


    public UserOrderAdapter(List<UserOrder> list, Context context) {
        mList = list;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_status_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        UserOrder userOrder = mList.get(position);

        Glide.with(mContext).load(userOrder.getCar_img()).into(holder.imgCar);
        if (userOrder.getDistributor_photo() != null)
            Glide.with(mContext).load(userOrder.getDistributor_photo()).into(holder.circleImageView);
        holder.tvNameCar.setText(userOrder.getCar_name());
        holder.tvFromTime.setText(userOrder.getFrom_time());
        holder.tvEndTime.setText(userOrder.getEnd_time());
        holder.tvDistributor.setText(userOrder.getDistributor_name());
        holder.tvPrice.setText(currencyFormatter.format(userOrder.getPrice()));
        holder.tvCode.setText(userOrder.getCode());
        holder.tvLicensePlates.setText(userOrder.getLicense_plates());
        holder.tvAddress.setText(userOrder.getAddress());
        holder.tvStatus.setText(userOrder.getStatus());

        switch (userOrder.getStatus()) {
            case "Đã xác nhận":
                holder.btnCancel.setVisibility(View.VISIBLE);
                holder.btnConfirm.setVisibility(View.VISIBLE);
                break;
            case "Chờ xác nhận":
                holder.btnCancel.setVisibility(View.VISIBLE);
                break;
            case "Đã hoàn thành":
                holder.tvComplete.setVisibility(View.VISIBLE);
                holder.tvComplete.setText("Đã thuê");
                break;
            case "Bị hủy":
                holder.tvComplete.setVisibility(View.VISIBLE);
                holder.tvComplete.setText("Bị hủy");
                break;
        }
        eventClickButton(holder, userOrder);

    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCode, tvLicensePlates, tvNameCar, tvPrice, tvFromTime, tvEndTime, tvDistributor, tvAddress, tvStatus, tvComplete;
        private ImageView imgCar;
        private CircleImageView circleImageView;
        Button btnLocation, btnCancel, btnConfirm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.textView_code);
            tvLicensePlates = itemView.findViewById(R.id.textView_license_plates);
            tvStatus = itemView.findViewById(R.id.textView_status);
            imgCar = itemView.findViewById(R.id.imageView_car);
            tvNameCar = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
            circleImageView = itemView.findViewById(R.id.profileCircleImageView);
            tvDistributor = itemView.findViewById(R.id.usernameTextView);
            tvFromTime = itemView.findViewById(R.id.textView_fromTime);
            tvEndTime = itemView.findViewById(R.id.textView_endTime);
            tvAddress = itemView.findViewById(R.id.textView_address);
            btnLocation = itemView.findViewById(R.id.btn_location);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
            btnConfirm = itemView.findViewById(R.id.btn_confirm);
            tvComplete = itemView.findViewById(R.id.textView_complete);
        }
    }

    void eventClickButton(ViewHolder holder, UserOrder userOrder) {
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.eventCancelOrder(userOrder.getOrder_id(), userOrder.getCar_id());
                notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.btnCancel.setVisibility(View.GONE);
                        holder.btnConfirm.setVisibility(View.GONE);

                    }
                }, 2000);

            }
        });
        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.eventCompleteOrder(userOrder.getOrder_id(), userOrder.getCar_id());
                notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.btnCancel.setVisibility(View.GONE);
                        holder.btnConfirm.setVisibility(View.GONE);
                    }
                }, 2000);

            }
        });
        holder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.eventMapView(userOrder);
            }
        });
    }
}