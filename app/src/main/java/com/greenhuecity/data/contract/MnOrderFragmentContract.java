package com.greenhuecity.data.contract;

import com.greenhuecity.data.model.OrderManagement;

import java.util.List;

public interface MnOrderFragmentContract {
    interface IView{
        void setDataRecyclerViewOrderManagement(List<OrderManagement> mList);
    }
    interface IPresenter{
        void getOrderManagementList(int id,String stt);
        int getUsersId();

        void updateStatusOrder(int order_id,String order_status,int car_id,String car_status);
    }
}
