package com.greenhuecity.itf;

import com.greenhuecity.data.model.UserOrder;

public interface OnClickButtonUserOrder {
    void eventCancelOrder(int order_id, int car_id);

    void eventCompleteOrder(int order_id, int car_id);

    void eventMapView(UserOrder userOrder);
}
