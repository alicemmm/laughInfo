package com.perasia.laughinfo.fragment;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.perasia.laughinfo.R;

public class FragmentMainActivity extends FragmentActivity {

    private ContentFragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        FragmentManager fm = getSupportFragmentManager();
        contentFragment = (ContentFragment) fm.findFragmentById(R.id.id_fragment_container);

        if (contentFragment == null) {
            contentFragment = new ContentFragment();
            fm.beginTransaction().add(R.id.id_fragment_container, contentFragment).commit();
        }
    }
}
