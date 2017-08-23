package com.zdv.dingdan.present;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zdv.dingdan.bean.CheckPayInfo;
import com.zdv.dingdan.bean.LoginInfoRequest;
import com.zdv.dingdan.bean.PayInfo;
import com.zdv.dingdan.bean.SynergyMallResponse;
import com.zdv.dingdan.bean.SynergyPayBack;
import com.zdv.dingdan.bean.SynergyPayBackResult;
import com.zdv.dingdan.bean.WDTResponseCode;
import com.zdv.dingdan.bean.WandiantongLoginInfo;
import com.zdv.dingdan.bean.xml_check_info_root;
import com.zdv.dingdan.bean.xml_login_info_root;
import com.zdv.dingdan.bean.xml_pay_info_root;
import com.zdv.dingdan.model.IRequestMode;
import com.zdv.dingdan.model.converter.CustomGsonConverter;
import com.zdv.dingdan.model.converter.CustomXmlConverter;
import com.zdv.dingdan.view.ILoginView;
import com.zdv.dingdan.view.IOrderView;
import com.zdv.dingdan.view.IPayView;
import com.zdv.dingdan.view.IView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/4/6.
 */

public class QueryPresent implements IRequestPresent {
    private IView iView;
    private Context context;
    private IRequestMode iRequestMode;

    private static QueryPresent instance = null;

    public void setView(Activity activity) {
        iView = (IView) activity;
    }

    public void setView(Fragment fragment) {
        iView = (IView) fragment;
    }

    private QueryPresent(Context context_) {
        context = context_;
    }

    public static synchronized QueryPresent getInstance(Context context) {
        if (instance == null) {
            return new QueryPresent(context);
        }
        return instance;
    }

    public void initRetrofit(String url, boolean isXml) {
        try {
            if (isXml) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(CustomXmlConverter.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                iRequestMode = retrofit.create(IRequestMode.class);
            } else {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(CustomGsonConverter.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                iRequestMode = retrofit.create(IRequestMode.class);
            }

        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
        }
    }

    public void initRetrofit2(String url, boolean isXml) {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                    .build();
            iRequestMode = retrofit.create(IRequestMode.class);

        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void QueryOder(String secret,String ucode,String code) {
        iRequestMode.QueryOrder(secret,ucode,code)
                .onErrorReturn(s -> new WDTResponseCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveCustomerOrder(s));
    }

    @Override
    public void SendPay(SynergyPayBack synergyPayBack) {
        iRequestMode.SendPay(synergyPayBack)
                .onErrorReturn(s -> new SynergyPayBackResult())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IPayView) iView).ResolveSynergyPayInfo(s));
    }

    @Override
    public void SendToMall(String ddh) {
        iRequestMode.SendPayToMall(ddh)
                .onErrorReturn(s -> new SynergyMallResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IPayView) iView).ResolveMallInfo(s));
    }

    @Override
    public void Login(LoginInfoRequest request) {
        iRequestMode.Login(request.getUser_login(), request.getUser_pass())
                .onErrorReturn(s -> new xml_login_info_root())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((ILoginView) iView).ResolveLoginInfo(s));
    }

    @Override
    public void LoginWDT(String username, String password, String company_id) {
        iRequestMode.LoginWDT(username, password, company_id)
                .onErrorReturn(s -> new WandiantongLoginInfo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((ILoginView) iView).ResolveLoginWDTInfo(s));
    }

    @Override
    public void SynchronizeWDT(String secret, String ucode, String ocode, String paytype, String payprice, String dealtype, String pcode, String receive, String remark) {
        iRequestMode.SychronzeWDT(secret, ucode, ocode, paytype, payprice, dealtype, pcode, receive, remark)
                .onErrorReturn(s -> new WDTResponseCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveCustomerOrder(s));
    }


    @Override
    public void Pay(String path, PayInfo info) {
        iRequestMode.Pay(path, info.getUsername(), info.getPassword(), info.getNumscreen(), info.getCode())
                .onErrorReturn(s -> new xml_pay_info_root())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IPayView) iView).ResolvePayInfo(s));
    }

    @Override
    public void CheckPay(String path, CheckPayInfo info) {
        if (info.getOrder_no().equals("")) {
            iRequestMode.CheckPayB(path, info.getUsername(), info.getPassword(), info.getOrder_no())
                    .onErrorReturn(s -> new xml_check_info_root())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> ((IPayView) iView).ResolveCheckPayInfo(s));
        } else {
            iRequestMode.CheckPayA(path, info.getUsername(), info.getPassword(), info.getOrder_no())
                    .onErrorReturn(s -> new xml_check_info_root())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> ((IPayView) iView).ResolveCheckPayInfo(s));
        }
    }


}
