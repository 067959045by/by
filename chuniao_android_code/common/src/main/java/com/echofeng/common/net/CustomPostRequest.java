package com.echofeng.common.net;

import com.net.core.callback.CallBack;
import com.net.core.callback.CallBackProxy;
import com.net.core.callback.CallClazzProxy;
import com.net.core.request.PostRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * <p>描述：扩展PostRequest请求，解决自定义ApiResult重复写代理的问题</p>
 */
public class CustomPostRequest extends PostRequest {
    public CustomPostRequest(String url) {
        super(url);
    }

    /*public PostRequest upJson(Object objBean) {//若果你的是obj,你想很容易转json,可以自己新增一个方法obj转json的
        return super.upJson(StringUtil.getJson(objBean));
    }*/

    /**
     * 覆写execute方法指定，代理用HttpApiResult
     *
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T> Observable<T> execute(Type type) {
        return super.execute(new CallClazzProxy<Result<T>, T>(type) {
        });
    }
    @Override
    public <T> Observable<T> execute(Class<T> clazz) {
        return super.execute(new CallClazzProxy<Result<T>, T>(clazz) {
        });
    }
    @Override
    public <T> Disposable execute(CallBack<T> callBack) {
        return super.execute(new CallBackProxy<Result<T>, T>(callBack) {
        });
    }
}
