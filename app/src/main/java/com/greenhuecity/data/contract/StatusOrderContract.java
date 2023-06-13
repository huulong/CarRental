package com.greenhuecity.data.contract;


import com.greenhuecity.data.model.UserOrder;

import java.util.List;

public interface StatusOrderContract {
    interface IView{
        void setDataRecyclerView(List<UserOrder> mList);
    }
    interface IPresenter{
        void getOrderListByStatus(String status);

    }
}
