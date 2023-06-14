package com.greenhuecity.data.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.model.Cars;

import java.util.List;

public interface RentailMnDetailContract {
    interface IView{
        void notificationUpdate(String mess);
    }
    interface IPresenter{
        void censorshipUpdate(String id, String approve, String mess);
    }
}
