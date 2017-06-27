package com.example.arron.gdgm.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseActivity;

/**
 * Created by Arron on 2017/4/17.
 */

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private FrameLayout frameLayout;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private String[] itemsName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsName = getResources().getStringArray(R.array.mainItemsName);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (tabLayout.getTabCount()> 0) {
            tabLayout.getTabAt(0).select();
        }
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        initTab();
        tabLayout.addOnTabSelectedListener(this);
    }

    private void initTab() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.mainItemsIcon);
        Class[] fragmentClasses = {NewsFragment.class, CourseFragment.class, GradeFragment.class, LibraryFragment.class, MoreFragment.class};
        if (itemsName.length != typedArray.length()) {
            throw new IllegalStateException("The items name length must same with icons ");
        }
        for (int i = 0; i < itemsName.length; i++) {
            try {
                tabLayout.addTab(tabLayout.newTab().setIcon(typedArray.getResourceId(i, 0)).setTag(fragmentClasses[i].newInstance()));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        typedArray.recycle();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Fragment fragment = (Fragment) tab.getTag();
        switchFragment(fragment);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Fragment fragment = (Fragment) tab.getTag();
        hideFragment(fragment);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Fragment fragment = (Fragment) tab.getTag();
        switchFragment(fragment);
    }

    private void switchFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentByTag == null) {
            fragmentTransaction.add(frameLayout.getId(), fragment, fragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
        if (fragmentByTag == null) {
            return;
        }
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

}
