package com.zdv.dingdan.view;

import com.zdv.dingdan.bean.SynergyPayBackResult;
import com.zdv.dingdan.bean.WDTResponseCode;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IOrderView extends IView{

    void ResolveCustomerOrder(WDTResponseCode info);
    void ResolveSynchronizeOrder(SynergyPayBackResult info);

}
