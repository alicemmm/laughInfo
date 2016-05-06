package com.perasia.laughinfo.reflection;


import android.util.Log;

public abstract class Person {

    String name = "";
    private int age = 0;
    public int fPubVar = 0;

    abstract void getPhone();

    public Person() {
        Log.e("tag", "Person");
    }

    int myAge() {
        return 50;
    }

    String myName() {
        return "Father";
    }

    public void callSun() {
        getPhone();
        priMethod(25);
    }

    private void priMethod(Integer a) {
        age = a;
        Log.e("tag", "I am priMethod , Dont call me! age " + age);
    }

    /**
     * @hide
     */

    public void hideMethod(String name) {
        Log.e("tag", "I am hideMethod , Dont call me! name:" + name
                + "   age:" + age);
    }


}
