package com.yjrlab.tabdoctor.view.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.dialog.DialogWarning;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.view.setting.ShowTypeEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.yjrlab.tabdoctor.view.LoginActivity.INTENT_WARNING;

/**
 * Created by yeonjukim on 2017. 6. 5..
 */

public class MainActivity extends TabDoctorActivity {
    /**
     * PRE_TYPE: 남자, 여자, 소아 구분 SharedPreference
     */
    private static final String PRE_TYPE = "type";
    /**
     * PRE_WARNING: DialogWarning를 true면 띄우고, false면 띄우지 않는다 SharedPreference
     */
    public static final String PRE_WARNING = "warning";
    private static final String[] titles = {"홈", "자가진단", "건강백과"};

    private MyPagerAdapter adapterViewPager;
    private CommonTabLayout tabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager vpPager;
    private boolean isShowWarning;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setUserModel();
        isShowWarning = getIntent().getBooleanExtra(INTENT_WARNING, false);
        setContentView(R.layout.activity_main);
        setTabLayout();
        showWarningDialog();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        adapterViewPager.getItem(0);
    }

    private void showWarningDialog() {
        if (isShowWarning) {
            if (getSharedPreferences(PRE_WARNING, MODE_PRIVATE)
                    .getBoolean(new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()), true)) {
                DialogWarning warning = new DialogWarning(MainActivity.this);
                warning.show();
            }
        }
    }

    private void setUserModel() {
        // TODO: 2017. 6. 7. 서버에서 UserModel 호출하여 SharedPref에 저장
        SharedPreferences preferences = getSharedPreferences(PRE_TYPE, MODE_PRIVATE);
        UserModel u = new UserModel();
        ShowTypeEnum type;
        if (u.getAge().equals("00")) {
            type = ShowTypeEnum.BABY;
        } else {
            if (u.getSex() == UserModel.GENDER_MAN) {
                type = ShowTypeEnum.MAN;
            } else if (u.getSex() == UserModel.GENDER_WOMAN) {
                type = ShowTypeEnum.WOMAN;
            }
        }

        preferences.edit()
                .putInt(PRE_TYPE, 0)
                .apply();
    }

    private void setTabLayout() {

        vpPager = (ViewPager) findViewById(R.id.viewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i]));
        }

        tabLayout = (CommonTabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabData(mTabEntities);
        tabLayout.setTextSelectColor(getResources().getColor(R.color.dahong));
        tabLayout.setTextUnselectColor(getResources().getColor(R.color.divider));
        tabLayout.setTabSpaceEqual(true);
        tabLayout.setIndicatorColor(getResources().getColor(R.color.dahong));
        tabLayout.setIndicatorHeight(3f);
        tabLayout.setIndicatorWidth(70f);
        tabLayout.setTextBold(2);
        tabLayout.setTextsize(15f);
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                vpPager.setCurrentItem(position);
                Dlog.d("reselected");
                Fragment fragment = ((MyPagerAdapter) vpPager.getAdapter()).page.get(position);
                if (fragment instanceof MainSelfDiagnosisFragment) {
                    ((MainSelfDiagnosisFragment) fragment).onReselected();
                } else if (fragment instanceof MainHealthDicFragment) {
                    ((MainHealthDicFragment) fragment).onReselected();
                }

            }
        });

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideKeyboard();
                tabLayout.setCurrentTab(position);
                Dlog.d("onPageSelected");

                Fragment fragment = ((MyPagerAdapter) vpPager.getAdapter()).page.get(position);
                if (fragment instanceof MainSelfDiagnosisFragment) {
                    ((MainSelfDiagnosisFragment) fragment).onReselected();
                } else if (fragment instanceof MainHealthDicFragment) {
                    ((MainHealthDicFragment) fragment).onReselected();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpPager.setCurrentItem(0);
    }

    public void hideKeyboard() {
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }

    }

    private long backPressTime;

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - backPressTime < 2000) {
            finish();
        } else {
            Toast.makeText(getContext(), "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            backPressTime = now;
        }
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        SparseArray<Fragment> page;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            page = new SparseArray<>();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    page.put(position, MainHomeFragment.newInstance(0, "Page # 1"));
                    break;
                case 1: // Fragment # 1 - This will show FirstFragment different title
                    page.put(position, MainSelfDiagnosisFragment.newInstance(1, "Page # 2"));
                    break;
                case 2: // Fragment # 2 - This will show SecondFragment
                    page.put(position, MainHealthDicFragment.newInstance(2, "Page # 3"));
                    break;
                default:
                    return null;
            }
            return page.get(position);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }

    public class TabEntity implements CustomTabEntity {
        public String title;

        public TabEntity(String title) {
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }

}
