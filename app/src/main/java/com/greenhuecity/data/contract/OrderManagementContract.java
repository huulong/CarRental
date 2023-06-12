package com.greenhuecity.data.contract;



import android.content.Context;

import com.greenhuecity.data.model.OrderManagement;

import java.util.List;

public interface OrderManagementContract {
    interface IView{
        void setDataRecyclerViewOrderManagement(List<OrderManagement> mList);
    }
    interface IPresenter{
        void getOrderManagementList(int id);
        int getUsersId();
        void updateStatusOrder(int order_id,String order_status,int car_id,String car_status);
    }
}
