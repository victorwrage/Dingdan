package com.zdv.dingdan.present;


import com.zdv.dingdan.bean.CheckPayInfo;
import com.zdv.dingdan.bean.LoginInfoRequest;
import com.zdv.dingdan.bean.PayInfo;
import com.zdv.dingdan.bean.SynergyPayBack;


/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:46
 */

public interface IRequestPresent {
    void QueryOder(String secret,String ucode,String code);
    void SendPay(SynergyPayBack synergyPayBack);
    void SendToMall(String ddh);
    void Login(LoginInfoRequest request);

    void LoginWDT(String username, String password,String company_id);
    void SynchronizeWDT(String secret,String ucode, String ocode
            ,String paytype,String payprice, String dealtype,
                       String pcode,String receive,String remark);


    void Pay(String path, PayInfo info);
    void CheckPay(String path, CheckPayInfo info);

}
