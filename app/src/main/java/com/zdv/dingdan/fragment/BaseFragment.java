package com.zdv.dingdan.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.zdv.dingdan.R;
import com.zdv.dingdan.cus_view.ProgressBarItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected final String SUCCESS = "200";
    protected final String COOKIE_KEY = "cookie";
    protected long isFar = 5000;//5公里
    protected String[] scopes = new String[]{"交易成功", "交易失败", "已撤销", "已冲正", "待支付"};


    public String currentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }


    protected void showWaitDialog(String tip){
        ProgressBarItem.show(getActivity(),tip,false,null);
    }
    protected void hideWaitDialog() {
        ProgressBarItem.hideProgress();
    }

    protected void startLoading(ImageView imageView) {
        imageView.setVisibility(View.VISIBLE);
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        imageView.startAnimation(rotate);
    }

    protected void stopLoading(ImageView imageView) {
        imageView.setVisibility(View.GONE);
        imageView.clearAnimation();
    }


    AlertDialog dialog;
    protected void showDialog(int type, String title, String tip, String posbtn, String negbtn) {
        dialog = null;
        if (negbtn == null) {
            dialog = new AlertDialog.Builder(getActivity()).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .create();
        } else {
            dialog = new AlertDialog.Builder(getActivity()).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .setNegativeButton(negbtn, (dia, which) -> cancel(type, dia)).create();
        }
        dialog.setCancelable(false);
        dialog.show();

    }

    protected void showEditDialog(String count) {
        final EditText inputServer = new EditText(getActivity());
        inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        inputServer.setText(count);
        inputServer.selectAll();
        inputServer.setFocusable(true);
        inputServer.setFocusableInTouchMode(true);
        inputServer.requestFocus();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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


    protected void cancel(int type, DialogInterface dia) {
        dialog.dismiss();
    }

    protected void confirm(int type, DialogInterface dia) {
        dialog.dismiss();
    }



}
