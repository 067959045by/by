package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.hot.MostHotResponse;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import java.util.List;

public interface HotHomeModelInterface extends BaseInterface {
    void onHotHomeSuccess(MostHotResponse data);
}
