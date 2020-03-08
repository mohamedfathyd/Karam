package com.khalej.karam.model;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface apiinterface_home {

    @FormUrlEncoded
    @POST("montag/KARAM/api/login")
    Call<contact_userinfo> getcontacts_login(@Field("kayWord") String phone, @Field("password") String password);

    @GET("montag/KARAM/api/all_categories")
    Call<List<contact_category>> getcontacts_annonce();
    @GET("/montag/KARAM/api/height_sel_products")
    Call<List<contact_home>> getcontacts_first();

    @FormUrlEncoded
    @POST("montag/KARAM/api/filter_by_cat")
    Call<List<contact_home>> getSubCategory(@Field("category_id") int id);
    @FormUrlEncoded
    @POST("montag/KARAM/api/contact_us")
    Call<ResponseBody> CallUs(@Field("name") String name, @Field("email") String address,
                              @Field("subject") String subject, @Field("message") String message);


    @GET("montag/KARAM/api/aboutUs")
    Call<AboutUs> AboutUS();

    @GET("montag/KARAM/api/condtions")
    Call<AboutUs> Conditoins();

    @FormUrlEncoded
    @POST("montag/KARAM/api/register")
    Call<ResponseBody> getcontacts_newaccountCar(@Field("image") String image, @Field("name") String name, @Field("password") String password, @Field("email") String address,
                                                 @Field("phone") String phone, @Field("car_number") String car_number, @Field("car_model") String car_model,
                                                 @Field("own_id") String own_id, @Field("drive_id") String drive_id, @Field("city") int city
            , @Field("type") int type);

    @FormUrlEncoded
    @POST("montag/KARAM/api/register")
    Call<ResponseBody> getcontacts_newaccountFany(@Field("image") String image, @Field("name") String name, @Field("password") String password, @Field("email") String address,
                                                  @Field("phone") String phone, @Field("city") int city, @Field("service") String service,
                                                  @Field("type") int type, @Field("latitude") double lat, @Field("longitude") double lng);

   @POST("montag/KARAM/api/add_order")
   Call<ResponseBody> getcontacts_makeOrder(@Body Add_order add_order);


    @FormUrlEncoded
    @POST("montag/KARAM/api/my_orders")
    Call<List<Order>> getcontacts_MyOrders(@Field("user_id")int id,@Field("type")int type);

    @FormUrlEncoded
    @POST("montag/KARAM/api/canceling_order")
    Call<ResponseBody> getcontacts_CancelOrder(@Field("order_id") int order_id, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("montag/KARAM/api/my_notification")
    Call<List<notificationData>>getcontacts_Notification(@Field("user_id")int user_id);

    @GET("montag/KARAM/api/all_cities")
    Call<List<Citys>> Citys();

    @Multipart
    @POST("montag/KARAM/api/register")
    Call<ResponseBody> getcontacts_newaccount(@Part MultipartBody.Part image, @Part("name") RequestBody name, @Part("password") RequestBody password, @Part("email") RequestBody address,
                                              @Part("phone") RequestBody phone, @Part("type")RequestBody type,@Part("city")RequestBody city
                                              ,@Part("phone_code") RequestBody phone_code,@Part("is_agree") RequestBody is_agree
                                               ,@Part("notion_id") RequestBody notion_id
     );

    @Multipart
    @POST("montag/KARAM/api/register")
    Call<ResponseBody> getcontacts_newaccountCompany(@Part MultipartBody.Part image, @Part("name") RequestBody name, @Part("password") RequestBody password, @Part("email") RequestBody address,
                                              @Part("phone") RequestBody phone, @Part("type")RequestBody type,@Part("city")RequestBody city
            ,@Part("phone_code") RequestBody phone_code,@Part("is_agree") RequestBody is_agree,@Part("shop_name") RequestBody company
            ,@Part("notion_id") RequestBody numId
    );

    @Multipart
    @POST("montag/KARAM/api/register")
    Call<ResponseBody> getcontacts_newaccountProvider(@Part MultipartBody.Part image,@Part MultipartBody.Part imageId,@Part("name") RequestBody name, @Part("password") RequestBody password, @Part("email") RequestBody address,
                                                     @Part("phone") RequestBody phone, @Part("type")RequestBody type,@Part("city")RequestBody city
            ,@Part("phone_code") RequestBody phone_code,@Part("is_agree") RequestBody is_agree
            ,@Part("notion_id") RequestBody numId,@Part("latitude") RequestBody lat,@Part("longitude") RequestBody lng
    );


    @FormUrlEncoded
    @POST("montag/KARAM/api/canRest")
    Call<Reset>getcontacts_ResetPassword(@Field("kayWord")String kayWord);

}

