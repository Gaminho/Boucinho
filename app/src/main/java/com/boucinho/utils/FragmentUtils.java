package com.boucinho.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int frameLayoutId){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(frameLayoutId, fragment);
        ft.commit();
    }

}
