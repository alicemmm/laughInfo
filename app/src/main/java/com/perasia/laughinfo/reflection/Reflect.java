package com.perasia.laughinfo.reflection;


import android.util.Log;

import java.lang.reflect.Field;

public class Reflect {

    public static void getInfo() {
        Reflect reflect = new Reflect();

        Class<?> temp = reflect.getClass();

        try {
            //public method
            Field[] fb = temp.getFields();
            for (int i = 0; i < fb.length; i++) {
                Class<?> cl = fb[i].getType();
                Log.e("tag", cl + "...." + fb[i].getName());
            }
        } catch (Exception e) {

        }
    }

}
