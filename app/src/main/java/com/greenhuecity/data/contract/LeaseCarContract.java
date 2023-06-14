package com.greenhuecity.data.contract;

import com.greenhuecity.data.model.LeaseCar;

import java.util.List;

public interface LeaseCarContract {
    interface IView {
        void setDataRecyclerView(List<LeaseCar> mList);
    }

    interface IPresenter {
        void getCarList(int id);

        int getUserId();

    }
}
