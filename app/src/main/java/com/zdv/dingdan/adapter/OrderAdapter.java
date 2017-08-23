package com.zdv.dingdan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;
import com.zdv.dingdan.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/7/1 14:46
 */

public class OrderAdapter extends BaseAdapter {
    ArrayList<HashMap<String,String>> items;
    OrderClickInterface listener;
    Context context;
    public OrderAdapter(Context context_, ArrayList<HashMap<String,String>> items_){
        items = items_;
        context = context_;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    public void setOnListener(OrderClickInterface listener){
        this.listener = listener;
    }
    @Override
    public HashMap<String,String> getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        HashMap<String,String> item = items.get(position);
        ViewHolder viewHolder;
        if(view ==null) {
            viewHolder = new ViewHolder();
            view = initView(item,viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.name.setText(item.get("name"));
            viewHolder.price.setText(item.get("price"));
            viewHolder.unit.setText(item.get("unit"));
            viewHolder.count.setText(item.get("count"));
            viewHolder.total.setText(item.get("total"));

        }
        RxView.clicks(viewHolder.count).subscribe(s -> edit(position));
        return view;
    }

    private View initView(HashMap<String,String> item, ViewHolder viewHolder){
        View view = View.inflate(context, R.layout.listheader,null);
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        viewHolder.price = (TextView) view.findViewById(R.id.price);
        viewHolder.unit = (TextView) view.findViewById(R.id.unit);
        viewHolder.count = (TextView) view.findViewById(R.id.count);
        viewHolder.total = (TextView) view.findViewById(R.id.total);

        viewHolder.name.setText(item.get("name"));
        viewHolder.price.setText(item.get("price"));
        viewHolder.unit.setText(item.get("unit"));
        viewHolder.count.setText(item.get("count"));
        viewHolder.total.setText(item.get("total"));

        view.setTag(viewHolder);
        return view;
    }

    private void edit(int position) {
       KLog.v("position_adapter"+position);
       listener.OnEdit(position);
    }

    class ViewHolder{
        TextView name,price,unit,count,total;
    }

    public interface OrderClickInterface{
       void OnEdit(int position);
    }
}
