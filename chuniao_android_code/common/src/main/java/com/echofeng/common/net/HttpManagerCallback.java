package com.echofeng.common.net;

import com.net.core.exception.ApiException;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: HttpManagerCallback
 * Author: echo
 * Date: 2019/11/5 15:28
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class HttpManagerCallback {
    public abstract void onSuccess(ResultData o);

    public abstract void onError(ApiException e);

    public void onCompleted() {

    }

    public void onStart() {

    }

    public void onResp() {
    }

}
