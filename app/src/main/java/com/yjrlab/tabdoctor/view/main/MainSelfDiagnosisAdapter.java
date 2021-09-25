package com.yjrlab.tabdoctor.view.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;

import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 9..
 */

public class MainSelfDiagnosisAdapter extends RecyclerView.Adapter<MainSelfDiagnosisAdapter.SelfDiagnosisHolder> {

    private Context context;
    private List<GroupedBodyPartSymptomModel> data;
    private OnDiagnosisClickListener listener;
    private String title;

    private boolean isSelectMode;

    public MainSelfDiagnosisAdapter(Context context, List<GroupedBodyPartSymptomModel> data, boolean isSelectMode) {
        this.context = context;
        this.data = data;
        this.isSelectMode = isSelectMode;
    }

    public void setData(List<GroupedBodyPartSymptomModel> data, boolean isSelectMode) {
        this.isSelectMode = isSelectMode;
        this.data = data;
        notifyDataSetChanged();
    }

    public void setData(List<GroupedBodyPartSymptomModel> data, boolean isSelectMode, String title) {
        this.title = title;
        setData(data, isSelectMode);
    }

    public List<GroupedBodyPartSymptomModel> getData() {
        return data;
    }

    public void setOnDiagnosisClickListener(OnDiagnosisClickListener listener) {
        this.listener = listener;
    }

    @Override
    public SelfDiagnosisHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_self_diagnosis, parent, false);
        return new SelfDiagnosisHolder(view);
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }

    @Override
    public void onBindViewHolder(final SelfDiagnosisHolder holder, int position) {
        final GroupedBodyPartSymptomModel model = data.get(position);
        if (isSelectMode && title != null) {
            holder.mTextViewTitle.setText(title);
        } else {
            holder.mTextViewTitle.setText(model.getPobName());
        }
        holder.mLayoutSymptom.removeAllViews();
        if (model.getBodyPartSymptoms() != null) {
            for (int i = 0; i < model.getBodyPartSymptoms().size(); i++) {
                final BodyPartSymptom bodyPartSymptom = model.getBodyPartSymptoms().get(i);
                ViewGroup symptomView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_self_diagnosis_sub, holder.mLayoutSymptom, false);
                TextView mTextViewSymptom = (TextView) symptomView.findViewById(R.id.textViewDiseaseInfo);
                mTextViewSymptom.setText(bodyPartSymptom.getBsContent());
                final RadioButton mRadioButton = (RadioButton) symptomView.findViewById(R.id.radioButtonDiseaseInfo);

                if (isSelectMode) {
                    mRadioButton.setVisibility(View.VISIBLE);
                    mTextViewSymptom.setText(bodyPartSymptom.getBsGroupCode().contains("Y") ? "예" : "아니오");
                } else {
                    mRadioButton.setVisibility(View.GONE);
                }
                if (i == (model.getBodyPartSymptoms().size() - 1)) {
                    symptomView.removeView(symptomView.findViewById(R.id.viewDivider));
                }

                mRadioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int childCount = holder.mLayoutSymptom.getChildCount();

                        for (int i = 0; i < childCount; i++) {
                            View radio = holder.mLayoutSymptom.getChildAt(i).findViewById(R.id.radioButtonDiseaseInfo);
                            if (radio != null) {
                                ((RadioButton) radio).setChecked(false);
                            }
                        }

                        ((RadioButton) v).setChecked(true);

                        if (listener != null) {
                            listener.onDiagnosisClick(bodyPartSymptom);
                        }
                    }
                });

                symptomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSelectMode) {
                            final int childCount = holder.mLayoutSymptom.getChildCount();

                            for (int i = 0; i < childCount; i++) {
                                View radio = holder.mLayoutSymptom.getChildAt(i).findViewById(R.id.radioButtonDiseaseInfo);
                                if (radio != null) {
                                    ((RadioButton) radio).setChecked(false);
                                }
                            }


                            mRadioButton.setChecked(true);
                        }
                        if (listener != null) {
                            listener.onDiagnosisClick(bodyPartSymptom);
                        }
                    }
                });

                holder.mLayoutSymptom.addView(symptomView);

            }

        }


    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    class SelfDiagnosisHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private LinearLayout mLayoutSymptom;

        SelfDiagnosisHolder(View itemView) {
            super(itemView);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            mLayoutSymptom = (LinearLayout) itemView.findViewById(R.id.layoutSymptom);
        }
    }

    interface OnDiagnosisClickListener {
        void onDiagnosisClick(BodyPartSymptom model);
    }
}
