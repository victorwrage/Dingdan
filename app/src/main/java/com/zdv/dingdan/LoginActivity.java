package com.zdv.dingdan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;
import com.zdv.dingdan.bean.LoginInfoRequest;
import com.zdv.dingdan.bean.WandiantongLoginInfo;
import com.zdv.dingdan.bean.xml_login_info_root;
import com.zdv.dingdan.utils.Constant;
import com.zdv.dingdan.utils.Utils;
import com.zdv.dingdan.utils.VToast;
import com.zdv.dingdan.view.ILoginView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateStatus;

public class LoginActivity extends BaseActivity implements ILoginView {
    private static final String COOKIE_KEY = "cookie";
    @Bind(R.id.username_edit)
    EditText username_edit;
    @Bind(R.id.password_edit)
    EditText password_edit;
    @Bind(R.id.company_edit)
    EditText company_edit;


    @Bind(R.id.cb_rem_pw)
    CheckBox cb_rem_pw;
    @Bind(R.id.button_login)
    Button button_login;

    @Bind(R.id.login_update)
    LinearLayout login_update;

    SharedPreferences sp;
    ProgressDialog progressDialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        present.initRetrofit(Constant.URL_BAIBAO, true);
        present.setView(LoginActivity.this);

        sp = getSharedPreferences(COOKIE_KEY, 0);
        util = Utils.getInstance();

        company_edit.setText(sp.getString(Constant.COMPANY, ""));
        username_edit.setText(sp.getString(Constant.USER_NAME, ""));
        password_edit.setText(sp.getString(Constant.USER_PW, ""));
        Constant.cookie.put(Constant.USER_NAME, sp.getString(Constant.USER_NAME, ""));
        Constant.cookie.put(Constant.USER_PW, sp.getString(Constant.USER_PW, ""));
        Constant.cookie.put(Constant.COMPANY, sp.getString(Constant.COMPANY, ""));
        if (!sp.getString(Constant.USER_NAME, "").equals("")) {
            cb_rem_pw.setChecked(true);
        }
    }

    /**
     * 初始化一些显示
     */
    private void initView() {
        RxView.clicks(button_login)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(s -> Login());

        RxView.clicks(login_update)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(s -> update());
        Bmob.initialize(this, Constant.PUBLIC_BMOB_KEY);
        //BmobUpdateAgent.initAppVersion(context);
        BmobUpdateAgent.setUpdateListener((updateStatus, updateInfo) -> {
            if (updateStatus == UpdateStatus.Yes) {//版本有更新

            } else if (updateStatus == UpdateStatus.No) {
                KLog.v("版本无更新");
            } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                KLog.v("请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。");
            } else if (updateStatus == UpdateStatus.IGNORED) {
                KLog.v("该版本已被忽略更新");
            } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
                KLog.v("请检查target_size填写的格式，请使用file.length()方法获取apk大小。");
            } else if (updateStatus == UpdateStatus.TimeOut) {
                KLog.v("查询出错或查询超时");
            }
        });
        BmobUpdateAgent.update(this);
    }

    private void update() {
        BmobUpdateAgent.forceUpdate(this);
    }

    /**
     * 登录账户
     */
    private void Login() {
        if (!util.isNetworkConnected(context)) {
            VToast.toast(context, "没有网络连接");
            return;
        }
        if (company_edit.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入门店号");
            return;
        }
        if (username_edit.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入用户名");
            return;
        }
        if (password_edit.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入密码");
            return;
        }
        showWaitDialog("正在登录百宝支付");
        present.initRetrofit(Constant.URL_BAIBAO, true);
        present.Login(new LoginInfoRequest(username_edit.getText().toString(), password_edit.getText().toString()));

    }

    @Override
    public void ResolveLoginInfo(xml_login_info_root info) {
        hideWaitDialog();
        if (info.xml_data == null) {
            VToast.toast(context, "网络错误");
            return;
        }
        KLog.v(info.xml_data.msg);
        if (info.xml_data.msg.equals("登录成功")) {
            showWaitDialog("正在登录万店通");
            present.initRetrofit(Constant.URL_WANDIAN, false);
            present.LoginWDT(username_edit.getEditableText().toString().trim(), password_edit.getEditableText().toString().trim()
                    , company_edit.getEditableText().toString().trim());
        }else{
            VToast.toast(context,info.xml_data.msg);
        }

    }

    @Override
    public void ResolveLoginWDTInfo(WandiantongLoginInfo info) {
        hideWaitDialog();
        KLog.v(info.toString());
        if (info.getErrcode() == null) {
            VToast.toast(context, "网络错误");
            return;
        }
        if (info.getContent() != null) {
            VToast.toast(context, "登录成功");

            SharedPreferences.Editor editor = sp.edit();
            if (cb_rem_pw.isChecked()) {
                editor.putString(Constant.USER_NAME, username_edit.getText().toString().trim());
                editor.putString(Constant.USER_PW, password_edit.getText().toString().trim());
                editor.putString(Constant.COMPANY, company_edit.getText().toString().trim());
                editor.commit();
            } else {
                editor.clear();
                editor.commit();
            }

            Constant.cookie.put(Constant.USER_NAME, username_edit.getEditableText().toString().trim());
            Constant.cookie.put(Constant.USER_PW, password_edit.getEditableText().toString().trim());
            Constant.cookie.put(Constant.COMPANY, company_edit.getEditableText().toString().trim());
            Constant.cookie.put(Constant.SECRET, util.UrlEnco(info.getSecret()));
            Constant.cookie.put(Constant.UCODE, info.getContent().getCode());
            gotoMain();
        }else{
            VToast.toast(context,info.getErrmsg());
        }
    }

    private void gotoMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}
