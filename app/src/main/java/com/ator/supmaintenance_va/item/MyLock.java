package com.ator.supmaintenance_va.item;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class MyLock {
    public static final Lock lock = new ReentrantLock();
    public static MyLock  instance = null;

    public static MyLock getInstance(){
        if (instance == null){
            instance = new MyLock();
        }
        return instance;
    }

    public MyLock(){

    }
}
