package com.zdv.dingdan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.device.DeviceManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zdv.dingdan.cus_view.ProgressBarItem;
import com.zdv.dingdan.present.QueryPresent;
import com.zdv.dingdan.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends FragmentActivity {
    protected static final String PAGE_0 = "page_0";
    protected static final String PAGE_1 = "page_1";

    protected static final int RECORD_PROMPT_MSG = 0x06;
    protected static final int SCAN_CLOSED = 1020;
    protected final String SUCCESS = "200";
    protected Context context;
    ProgressDialog progressDialog;
    DeviceManager deviceManager;
    QueryPresent present;
    Utils util;

    protected final int EXIT_CONFIRM = 20;

    boolean stop = false;//网络请求标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        deviceManager = new DeviceManager();
        util = Utils.getInstance();
        present = QueryPresent.getInstance(BaseActivity.this);
    }


    protected void showWaitDialog(String tip){
        ProgressBarItem.show(BaseActivity.this,tip,false,null);
    }
    protected void hideWaitDialog() {
        ProgressBarItem.hideProgress();
    }


    /**
     *
     */
    protected void onProgressDissmiss() {
        stop = true;
    }




    protected void showDialog(int type, String title, String tip, String posbtn, String negbtn) {
        AlertDialog dialog = null;
        if (negbtn == null) {
            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .create();
        } else {
            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .setNegativeButton(negbtn, (dia, which) -> cancel(type, dia)).create();
        }
        dialog.setCancelable(false);
        dialog.show();

    }

    protected void showEditDialog(String count) {
        final EditText inputServer = new EditText(BaseActivity.this);
        inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        inputServer.setText(count);
        inputServer.selectAll();
        inputServer.setFocusable(true);
        inputServer.setFocusableInTouchMode(true);
        inputServer.requestFocus();

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("请输入数量").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", (dialog, type) -> edit(inputServer, type, dialog));
        builder.setNegativeButton("取消", (dialog, type) -> cancelEdit(inputServer, type, dialog));
        builder.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) inputServer.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(inputServer, 0);
                           }
                       },
                500);

    }
    protected void edit(EditText inputServer, int type, DialogInterface dialog) {
        dialog.dismiss();
    }
    protected void cancelEdit(EditText inputServer, int type, DialogInterface dialog) {
        dialog.dismiss();
    }


    public String currentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    protected void confirm(int type, DialogInterface dialog) {
        dialog.dismiss();
    }

    protected void cancel(int type, DialogInterface dialog) {
        dialog.dismiss();
    }
}
