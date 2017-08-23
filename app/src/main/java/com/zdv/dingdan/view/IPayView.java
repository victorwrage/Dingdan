package com.zdv.dingdan.view;


import com.zdv.dingdan.bean.SynergyMallResponse;
import com.zdv.dingdan.bean.SynergyPayBackResult;
import com.zdv.dingdan.bean.xml_check_info_root;
import com.zdv.dingdan.bean.xml_pay_info_root;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IPayView extends IView{
    /**
     * 支付提交
     * @param info
     */
    void ResolveMallInfo(SynergyMallResponse info);
    /**
     * 支付提交
     * @param info
     */
    void ResolvePayInfo(xml_pay_info_root info);
    /**
     * 支付状态
     * @param info
     */
    void ResolveCheckPayInfo(xml_check_info_root info);
    /**
     * 返回提交的支付状态
     * @param info
     */
    void ResolveSynergyPayInfo(SynergyPayBackResult info);
}
