package com.zdv.dingdan.view;

import com.zdv.dingdan.bean.WandiantongLoginInfo;
import com.zdv.dingdan.bean.xml_login_info_root;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface ILoginView extends IView{
    /**
     * 处理登录信息
     * @param info
     */
    void ResolveLoginInfo(xml_login_info_root info);

    void ResolveLoginWDTInfo(WandiantongLoginInfo info);

}
