package com.ivyiot.sdkdemo.sdk;

public interface ISdkCallback<T> {

    void onSuccess(T result);

    void onError(int errorCode);

    void onLoginError(int errorCode);
}
