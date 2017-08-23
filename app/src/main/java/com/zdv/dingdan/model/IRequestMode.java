package com.zdv.dingdan.model;


import com.zdv.dingdan.bean.WDTResponseCode;
import com.zdv.dingdan.bean.SynergyMallResponse;
import com.zdv.dingdan.bean.SynergyPayBack;
import com.zdv.dingdan.bean.SynergyPayBackResult;
import com.zdv.dingdan.bean.WandiantongLoginInfo;
import com.zdv.dingdan.bean.xml_check_info_root;
import com.zdv.dingdan.bean.xml_login_info_root;
import com.zdv.dingdan.bean.xml_pay_info_root;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xyl on 2017/4/6.
 */

public interface IRequestMode {

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Order&a=SearchOrder")
    Observable<WDTResponseCode> QueryOrder(@Field("secret") String secret,@Field("ucode") String ucode, @Field("code") String code);

    @POST("zsap_pos?sap-client=800&method=ZCOD_PAYMENT_RET")
    Observable<SynergyPayBackResult> SendPay(@Body SynergyPayBack synergyPayBack);

    @GET("synergyMallServcie/order/getDetailByScmForZDW.jhtml?")
    Observable<SynergyMallResponse> SendPayToMall(@Query("ddh") String ddh);

    @FormUrlEncoded
    @POST("App/Appinterface/login2/")
    Observable<xml_login_info_root> Login(@Field("user_login") String user_login, @Field("user_pass") String user_pass);

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=Login")
    Observable<WandiantongLoginInfo> LoginWDT(@Field("username") String username, @Field("password") String password, @Field("company_id") String company_id);

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Ordercomment&a=AddOrderFeedback")
    Observable<WDTResponseCode> SychronzeWDT(@Field("secret") String secret, @Field("ucode") String ucode, @Field("ocode") String ocode
    , @Field("paytype") String paytype, @Field("payprice") String payprice, @Field("dealtype") String dealtype,
     @Field("pcode") String pcode, @Field("receive") String receive, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("Admin/Pospay/{type}/")
    Observable<xml_pay_info_root> Pay(@Path("type") String type, @Field("username") String username, @Field("password") String password, @Field("numscreen") String numscreen, @Field("code") String code);

    @FormUrlEncoded
    @POST("Admin/Pospay/{type}/")
    Observable<xml_check_info_root> CheckPayA(@Path("type") String type, @Field("username") String username, @Field("password") String password, @Field("order_no") String order_no);

    @FormUrlEncoded
    @POST("Admin/Pospay/{type}/")
    Observable<xml_check_info_root> CheckPayB(@Path("type") String type, @Field("username") String username, @Field("password") String password, @Field("auth_code") String auth_code);

}
