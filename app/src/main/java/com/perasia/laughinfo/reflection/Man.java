package com.perasia.laughinfo.reflection;


import android.util.Log;

import java.util.Observable;
import java.util.Observer;

public class Man extends Person implements Observer {

    private int age = 0;
    private String var1 = "I am var1";
    public int var2 = 20;

    public Man() {
        Log.e("tag", "I am Man" + var1);
        age = 20;
    }

    public int myAge() {
        return 28;
    }

    public String myName() {
        return "Jerome";
    }

    private void getName() {
        Log.e("tag", "I am Jerome");
    }

    /**
     * @hide
     */
    private void getAge() {
        Log.e("tag", "I am " + age);
    }

    @Override
    void getPhone() {
        Log.e("tag", "I am sun , My age is " + age + "___" + var2);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
