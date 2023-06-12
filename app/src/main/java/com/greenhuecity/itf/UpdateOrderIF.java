package com.greenhuecity.itf;

public interface UpdateOrderIF {
    void updateCofirm(int car_id, int order_id);

    void updateRefuse(int car_id, int order_id);

    void updateComplete(int car_id, int order_id);

    void updateCancel(int car_id, int order_id);
}