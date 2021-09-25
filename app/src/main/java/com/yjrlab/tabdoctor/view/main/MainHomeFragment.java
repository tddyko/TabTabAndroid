package com.yjrlab.tabdoctor.view.main;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.OnBottomEventListener;
import com.yjrlab.tabdoctor.libs.simpleimagedownloadview.SimpleImageDownloadView;
import com.yjrlab.tabdoctor.model.BannerModel;
import com.yjrlab.tabdoctor.model.MainContentModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.MainService;

import java.util.List;

public class MainHomeFragment extends Fragment {

    private View rootView;

    private String title;
    private int page;
    private RecyclerView recyclerView;
    private HealthInfoAdapter healthInfoAdapter;

    // newInstance constructor for creating fragment with arguments
    public static MainHomeFragment newInstance(int page, String title) {
        MainHomeFragment mainHomeFragment = new MainHomeFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        mainHomeFragment.setArguments(args);
        Dlog.d("homefragmetn created");
        return mainHomeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            healthInfoAdapter = new HealthInfoAdapter(getContext(), null);
            recyclerView.setAdapter(healthInfoAdapter);
            recyclerView.addOnScrollListener(new OnBottomEventListener() {
                @Override
                protected void onBottom() {
                    goNextMainPage();
                }
            });
            goNextMainPage();
            getBannerPage();
        }

        return rootView;
    }

    private int mainPage = 1;

    private void goNextMainPage() {
        if (mainPage > 1
                && healthInfoAdapter.getItemCount() < NetworkManager.FLAG_PAGE_OFFSET) {
            return;
        }
        NetworkManager.retrofit
                .create(MainService.class)
                .getMainContents(mainPage, NetworkManager.FLAG_PAGE_OFFSET)
                .enqueue(new NetworkCallback<List<MainContentModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<MainContentModel> content) {
                        super.onSuccess(content);
                        healthInfoAdapter.addData(content);
                    }
                });
        mainPage++;
    }

    private void getBannerPage() {
        NetworkManager.retrofit
                .create(MainService.class)
                .getBanners()
                .enqueue(new NetworkCallback<List<BannerModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<BannerModel> content) {
                        super.onSuccess(content);
                        healthInfoAdapter.setBannerModels(content);
                    }
                });
    }

}
