package com.greenhuecity.data.contract;


import com.greenhuecity.data.model.UserOrder;

import java.util.List;

public interface StatusOrderContract {
    interface IView {
        void setDataRecyclerView(List<UserOrder> mList);
    }

    interface IPresenter {
        int getUsersId();

        void getOrderListByStatus(int id, String status);

        void updateStatusOrder(int order_id, String order_status, int car_id, String car_status);


    }
}
