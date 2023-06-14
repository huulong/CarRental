package com.greenhuecity.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.itf.UpdateOrderIF;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderManagementAdapter extends RecyclerView.Adapter<OrderManagementAdapter.ViewHolder> {
    private List<OrderManagement> mList;
    private Context mContext;
    OrderManagement orderManagement;

    UpdateOrderIF orderIF;

    public void setUpdateOrderIF(UpdateOrderIF orderIF) {
        this.orderIF = orderIF;
    }


    public OrderManagementAdapter(List<OrderManagement> list, Context context) {
        mList = list;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_order_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        OrderManagement orderManagement = mList.get(position);

        Glide.with(mContext).load(orderManagement.getCar_photo()).into(holder.imgCar);
        if (orderManagement.getPhoto() != null)
            Glide.with(mContext).load(orderManagement.getPhoto()).into(holder.circleImageView);
        holder.tvNameCar.setText(orderManagement.getCar_name());
        holder.tvCode.setText(orderManagement.getCode());
        holder.tvFromTime.setText(orderManagement.getFrom_time());
        holder.tvEndTime.setText(orderManagement.getEnd_time());
        holder.tvPlates.setText(orderManagement.getLicense_plates());
        holder.tvUserName.setText(orderManagement.getFullname());
        holder.tvStatus.setText(orderManagement.getStatus());
        holder.tvPrice.setText(currencyFormatter.format(orderManagement.getRental_costs()));

        eventClickButton(holder, orderManagement);

    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameCar, tvPrice, tvCode, tvFromTime, tvEndTime, tvPlates, tvUserName, tvStatus, tvComplete;
        Button btnConfirm, btnRefuse, btnComplete, btnCancel;
        private ImageView imgCar;
        private CircleImageView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCar = itemView.findViewById(R.id.imageView_car);
            tvNameCar = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
            circleImageView = itemView.findViewById(R.id.profileCircleImageView);
            tvUserName = itemView.findViewById(R.id.usernameTextView);
            tvCode = itemView.findViewById(R.id.textView_code);
            tvFromTime = itemView.findViewById(R.id.textView_fromTime);
            tvEndTime = itemView.findViewById(R.id.textView_endTime);
            tvPlates = itemView.findViewById(R.id.textView_license_plates);
            tvStatus = itemView.findViewById(R.id.textView_status);
            btnConfirm = itemView.findViewById(R.id.button_confirm);
            btnRefuse = itemView.findViewById(R.id.button_refuse);
            tvComplete = itemView.findViewById(R.id.textView_complete);
            btnComplete = itemView.findViewById(R.id.button_complete);
            btnCancel = itemView.findViewById(R.id.button_cancel);
        }
    }

    private void eventClickButton(ViewHolder holder, OrderManagement orderManagement) {
        String status = orderManagement.getStatus();
        if (status.equals("Đã hoàn thành") || status.equals("Bị hủy")) {
            holder.tvComplete.setVisibility(View.VISIBLE);
            holder.tvComplete.setText(status.equals("Đã hoàn thành") ? "Complete" : "Bị hủy");
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnRefuse.setVisibility(View.GONE);
        } else if (status.equals("Đang được thuê")) {
            holder.btnComplete.setVisibility(View.VISIBLE);
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnRefuse.setVisibility(View.GONE);
        }else if(status.equals("Đã xác nhận")){
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnRefuse.setVisibility(View.GONE);
        }

        holder.btnConfirm.setOnClickListener(view2 -> orderIF.updateCofirm(orderManagement.getCar_id(), orderManagement.getOrder_id()));
        holder.btnRefuse.setOnClickListener(view1 -> orderIF.updateRefuse(orderManagement.getCar_id(), orderManagement.getOrder_id()));
        holder.btnComplete.setOnClickListener(view -> orderIF.updateComplete(orderManagement.getCar_id(), orderManagement.getOrder_id()));
        holder.btnCancel.setOnClickListener(view -> orderIF.updateCancel(orderManagement.getCar_id(), orderManagement.getOrder_id()));

    }
}