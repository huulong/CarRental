package com.greenhuecity.data.remote;

import com.greenhuecity.data.model.Brands;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.LeaseCar;
import com.greenhuecity.data.model.OrderItems;
import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.data.model.Orders;
import com.greenhuecity.data.model.Powers;
import com.greenhuecity.data.model.RentManagement;
import com.greenhuecity.data.model.UpdateOrder;
import com.greenhuecity.data.model.UserOrder;
import com.greenhuecity.data.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    //Lấy tất cả xe theo thương hiệu
    @POST("arr-car.php")
    @FormUrlEncoded
    Call<List<Cars>> getCarByBrand(@Field("brands") String brands);

    //Lấy danh sách user
    @GET("arr-user.php")
    Call<List<Users>> getUsers();

    //register
    @POST("register-users.php")
    @FormUrlEncoded
    Call<Users> registerUsers(@Field("email") String email,
                              @Field("password") String password,
                              @Field("fullname") String fullName,
                              @Field("phone") String phone);

    //Lấy danh sách distributors
    @GET("arr-distributor.php")
    Call<List<Distributors>> getDistributor();

    @POST("distributors-id.php")
    @FormUrlEncoded
    Call<List<Distributors>> getDistributors(@Field("id") int id);

    //Xử lí đặt hàng
    @POST("up-orders.php")
    @FormUrlEncoded
    Call<Orders> upOrders(@Field("code") String code,
                          @Field("from_time") String from_time,
                          @Field("end_time") String end_time,
                          @Field("users_id") int users_id);

    @POST("orders-code.php")
    @FormUrlEncoded
    Call<List<Orders>> getOrders(@Field("cod") String cod);

    @POST("up-order-items.php")
    @FormUrlEncoded
    Call<OrderItems> upOrderItems(@Field("car_id") int car_id,
                                  @Field("order_id") int order_id,
                                  @Field("price") double price);

    //Xử lí đơn đặt hàng
    @POST("order-management.php")
    @FormUrlEncoded
    Call<List<OrderManagement>> getOrderManagement(@Field("id") int id);

    //update order
    @POST("update-order.php")
    @FormUrlEncoded
    Call<UpdateOrder> updateOrders(@Field("order_id") int order_id,
                                   @Field("order_status") String order_status,
                                   @Field("car_id") int car_id,
                                   @Field("car_status") String car_status);

    //add cars
    @POST("upload-cars.php")
    @FormUrlEncoded
    Call<Cars> uploadCars(@Field("car_name") String car_name,
                          @Field("price") double price,
                          @Field("description") String description,
                          @Field("license_plates") String license_plates,
                          @Field("status") String status,
                          @Field("from_time") String from_time,
                          @Field("end_time") String end_time,
                          @Field("approve") String approve,
                          @Field("power_id") int power_id,
                          @Field("brand_id") int brand_id,
                          @Field("user_id") int user_id,
                          @Field("distributor_id") int distributor_id,
                          @Field("top_speed") double top_speed,
                          @Field("horse_power") double horse_power,
                          @Field("mileage") double mileage,
                          @Field("image_data") String image_data,
                          @Field("random_photo") String random_photo);

    @GET("arr-brand.php")
    Call<List<Brands>> getBrand();

    @GET("arr-power.php")
    Call<List<Powers>> getPower();

    //xác minh đăng kí cho thuê
    @POST("arr-rental-management.php")
    @FormUrlEncoded
    Call<List<RentManagement>> getRentCars(@Field("id") int id);

    @POST("update-censored.php")
    @FormUrlEncoded
    Call<String> updateCensored(@Field("car_id") String car_id,
                                @Field("approve") String approve);

    @POST("arr-orderby-user-id.php")
    @FormUrlEncoded
    Call<List<UserOrder>> getOrderByUserIdStatus(@Field("id") int id,
                                                 @Field("stt") String stt);
    //profile
    @POST("update-users.php")
    @FormUrlEncoded
    Call<Users> updateUser(@Field("id") int id,
                           @Field("photo") String photo,
                           @Field("fullname") String fullname,
                           @Field("email") String email,
                           @Field("phone") String phone,
                           @Field("age") String age,
                           @Field("cccd") String cccd,
                           @Field("address") String address);
    @POST("get-user-by-id.php")
    @FormUrlEncoded
    Call<List<Users>> getUsersById(@Field("id") int id);
    //
    @POST("arr-lease-cars.php")
    @FormUrlEncoded
    Call<List<LeaseCar>> getLeaseCar(@Field("id") int id);
}
