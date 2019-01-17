package com.example.serokorean.Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.serokorean.BuildConfig;
import com.example.serokorean.Fragments.FragmentContent;
import com.example.serokorean.Interface.NavigationManager;
import com.example.serokorean.MainActivity;
import com.example.serokorean.MainPage;
import com.example.serokorean.R;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private MainPage mainPage;

    public static FragmentNavigationManager getmInstance(MainPage mainPage){
        if(mInstance == null) mInstance = new FragmentNavigationManager();

        mInstance.configure(mainPage);
        return mInstance;
    }

    private void configure(MainPage mainPage) {
        mainPage = mainPage;
        mFragmentManager = mainPage.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {

        showFragment(FragmentContent.newInstance(title), false);

    }

    private void showFragment(FragmentContent fragmentContent, boolean b) {
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container, fragmentContent);
        ft.addToBackStack(null);
        if(b || !BuildConfig.DEBUG){
            ft.commitAllowingStateLoss();
        }
        else{
            ft.commit();
        }

        fm.executePendingTransactions();
    }
}
