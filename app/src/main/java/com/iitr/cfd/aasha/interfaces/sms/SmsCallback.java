package com.iitr.cfd.aasha.interfaces.sms;

/**
 * Created by Harjot on 04-Mar-17.
 */

public interface SmsCallback<T>{

    void onReceive(T t);

}
