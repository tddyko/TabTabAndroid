package com.yjrlab.tabdoctor.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.OnBottomEventListener;
import com.yjrlab.tabdoctor.model.HealthDicModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.enums.HealthSearchField;
import com.yjrlab.tabdoctor.network.enums.HealthSortField;
import com.yjrlab.tabdoctor.network.enums.SortMethod;
import com.yjrlab.tabdoctor.network.service.UserService;

import java.util.List;

public class MainHealthDicFragment extends Fragment implements View.OnClickListener, SearchDiseaseAdapter.OnDiseaseClickListener {

    private View rootView;
    private String title;
    private int page;
    private RecyclerView recyclerView;
    private EditText editTextDisease;
    private ImageView imageViewSearch;
    private SearchDiseaseAdapter diseaseSearchAdapter;
    private LinearLayout layoutInfo;
    private TextView textViewInfo1;
    private TextView textViewInfo2;
    private TextView textViewInfo3;
    private TextView textViewInfo4;
    private int totalCount = 0;

    // newInstance constructor for creating fragment with arguments
    public static MainHealthDicFragment newInstance(int page, String title) {
        MainHealthDicFragment mainHomeFragment = new MainHealthDicFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        mainHomeFragment.setArguments(args);
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


        rootView = inflater.inflate(R.layout.fragment_health_dic, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        diseaseSearchAdapter = new SearchDiseaseAdapter(getContext(), null);
        diseaseSearchAdapter.setOnDiseaseClickListener(this);
        recyclerView.setAdapter(diseaseSearchAdapter);

        recyclerView.addOnScrollListener(new OnBottomEventListener() {
            @Override
            protected void onBottom() {
                if (totalCount > 10) {
                    nextDic();
                }
            }
        });

        layoutInfo = (LinearLayout) rootView.findViewById(R.id.layoutInfo);

        editTextDisease = (EditText) rootView.findViewById(R.id.editTextDisease);
        editTextDisease.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    imageViewSearch.callOnClick();
                }
                return false;
            }
        });
        imageViewSearch = (ImageView) rootView.findViewById(R.id.imageViewSearch);
        imageViewSearch.setOnClickListener(this);

        textViewInfo1 = (TextView) rootView.findViewById(R.id.textViewInfo1);
        textViewInfo2 = (TextView) rootView.findViewById(R.id.textViewInfo2);
        textViewInfo3 = (TextView) rootView.findViewById(R.id.textViewInfo3);
        textViewInfo4 = (TextView) rootView.findViewById(R.id.textViewInfo4);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewSearch:
                if (TextUtils.isEmpty(editTextDisease.getText())) {
                    return;
                }
                searchDic(editTextDisease.getText().toString());
                ((MainActivity) getActivity()).hideKeyboard();
                break;
        }
    }

    private int readPage = 1;
    private String keyword = "";

    private void searchDic(final String keyword) {
        readPage = 1;
        this.keyword = keyword;
        NetworkManager.retrofit
                .create(UserService.class)
                .getHealthDics(HealthSortField.NAME, SortMethod.ASC, HealthSearchField.NAME, keyword, readPage, NetworkManager.FLAG_PAGE_OFFSET)
                .enqueue(new NetworkCallback<List<HealthDicModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<HealthDicModel> content) {
                        super.onSuccess(content);
                        recyclerView.setVisibility(View.VISIBLE);
                        layoutInfo.setVisibility(View.GONE);
                        diseaseSearchAdapter.setKeyword("\"" + keyword + "\"", getTotalCount(), content);
                        totalCount = getTotalCount();
                    }
                });
    }

    private void nextDic() {
        readPage++;

        NetworkManager.retrofit
                .create(UserService.class)
                .getHealthDics(HealthSortField.NAME, SortMethod.ASC, HealthSearchField.NAME, keyword, readPage, NetworkManager.FLAG_PAGE_OFFSET)
                .enqueue(new NetworkCallback<List<HealthDicModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<HealthDicModel> content) {
                        super.onSuccess(content);
                        recyclerView.setVisibility(View.VISIBLE);
                        layoutInfo.setVisibility(View.GONE);
                        diseaseSearchAdapter.addData(content);
                    }

                    @Override
                    protected void onFailure(String msg) {
                        //super.onFailure(msg);
                    }


                });
    }

    @Override
    public void onClickDisease(HealthDicModel model) {
        layoutInfo.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        NetworkManager.retrofit
                .create(UserService.class)
                .getHealthDic(model.getCode())
                .enqueue(new NetworkCallback<HealthDicModel>(getContext()) {
                    @Override
                    protected void onSuccess(HealthDicModel content) {
                        super.onSuccess(content);
                        textViewInfo1.setText(content.getName());
                        textViewInfo2.setText(content.getSymptom());
                        textViewInfo3.setText(content.getMedicalTitle());
                        textViewInfo4.setText(content.getContent());
                    }
                });

    }

    public void onReselected() {
        editTextDisease.setText("");
        searchDic(editTextDisease.getText().toString());
        ((MainActivity) getActivity()).hideKeyboard();
    }

}
