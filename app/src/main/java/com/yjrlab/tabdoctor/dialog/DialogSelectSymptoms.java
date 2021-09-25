package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.CheckableButton;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class DialogSelectSymptoms extends DialogBase implements View.OnClickListener {


    private OnMyDialogResultJob mDialogResult;
    private String title;
    public List<BodyPartSymptom> selectedSymptoms = new ArrayList<>();
    private List<BodyPartSymptom> bodyPartSymptoms = new ArrayList<>();

    public DialogSelectSymptoms(@NonNull Context context, String title, List<BodyPartSymptom> bodyPartSymptoms) {
        super(context);
        this.title = title;
        this.bodyPartSymptoms = bodyPartSymptoms;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_symptoms);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new SelectSymptomAdapter(getContext(), this, bodyPartSymptoms));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ((TextView) findViewById(R.id.title)).setText(title);

        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonOk).setOnClickListener(this);

    }

    public void setDialogResult(DialogSelectSymptoms.OnMyDialogResultJob dialogResult) {
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResultJob {
        void finish(List<BodyPartSymptom> result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClose:
                mDialogResult.finish(null);
                dismiss();
                break;
            case R.id.buttonOk:
                if (selectedSymptoms.size() == 0) {
                    Toast.makeText(getContext(), "하나 이상의 증상을 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                mDialogResult.finish(selectedSymptoms);
                dismiss();
                break;

        }
    }


}
