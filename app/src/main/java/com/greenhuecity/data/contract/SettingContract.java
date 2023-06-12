package com.greenhuecity.data.contract;

import com.greenhuecity.data.model.Distributors;

import java.util.List;

public interface SettingContract {
    interface IView{
        void setDataSetting(String name,String url);
        void checkDistributors(List<Distributors> list);

    }
    interface IPresenter{
        void getDataShared();
        boolean isLogged();
        void getDistributors();
        int getUsersId();


    }
}
