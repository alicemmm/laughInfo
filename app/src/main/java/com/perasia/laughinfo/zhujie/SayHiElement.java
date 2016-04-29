package com.perasia.laughinfo.zhujie;


import android.util.Log;

public class SayHiElement {

    public void SayHiDefault(String name) {
        Log.e("tag", "Hi 1," + name);
    }

    @SayHiAnnotation(paramValue = "asia")
    public void SayHiAnnotation(String name) {
        Log.e("tag", "Hi 2," + name);
    }

    @SayHiAnnotation
    public void SayHiAnnotationDefault(String name) {
        Log.e("tag", "Hi 3," + name);
    }
}
