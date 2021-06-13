package com.echofeng.common.net;

import com.net.core.callback.CallBack;
import com.net.core.callback.CallBackProxy;
import com.net.core.callback.CallClazzProxy;
import com.net.core.request.GetRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * <p>描述：扩展GetResult请求，解决自定义ApiResult重复写代理的问题</p>
 */
public class CustomGetRequest extends GetRequest {
    public CustomGetRequest(String url) {
        super(url);
    }

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
