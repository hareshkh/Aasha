package com.iitr.cfd.aasha.interfaces.sms;

/**
 * Created by Harjot on 04-Mar-17.
 */

public interface SmsCallback<T>  extends BaseSmsCallback{

    void onReceive(T t);

}
