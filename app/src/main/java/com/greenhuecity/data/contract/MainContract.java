package com.greenhuecity.data.contract;

import com.greenhuecity.data.model.SlidePopup;

import java.util.List;

public interface MainContract {
    interface IView{
        void setDataListPopUp(List<SlidePopup> mList);
        void setEventButtonPopUp();
    }
    interface IPresenter{
        void getListSlidePopUp();
    }
}
