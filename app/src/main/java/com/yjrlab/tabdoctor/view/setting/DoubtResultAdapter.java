package com.yjrlab.tabdoctor.view.setting;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;

import java.util.List;

/**
 * Created by yeonjukim on 2017. 6. 6..
 */

public class DoubtResultAdapter extends RecyclerView.Adapter<DoubtResultAdapter.DoubtResultViewholder> {
    private final Context context;
    private List<GroupedBodyPartSymptomModel> models;

    public DoubtResultAdapter(Context context, List<GroupedBodyPartSymptomModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public DoubtResultViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoubtResultViewholder(LayoutInflater.from(context).inflate(R.layout.view_doubt_result, parent, false));
    }

    @Override
    public void onBindViewHolder(final DoubtResultViewholder holder, int position) {
        final GroupedBodyPartSymptomModel model = models.get(position);
        holder.textViewParts.setText(model.getPobName());
        switch (model.getPobId()) {
            case 1:
            case 8:
                holder.textViewParts.setText("머리");
                break;
            case 4:
            case 9:
                holder.textViewParts.setText("목");
                break;
            case 6:
            case 11:
                holder.textViewParts.setText("팔다리어깨");
                break;
            case 5:
            case 10:
                holder.textViewParts.setText("몸");
                break;
            case 7:
                holder.textViewParts.setText("비뇨기과");
                break;
            case 12:
                holder.textViewParts.setText("산부인과");
                break;

        }

        for (BodyPartSymptom symptom : model.getBodyPartSymptoms()) {
            TextView textView = new TextView(context);
            textView.setText(symptom.getBsContent());
            textView.setTextSize(12f);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(context.getResources().getColor(R.color.white));
            holder.layoutSymptom.addView(textView);
        }

        if (position == models.size()-1) {
            holder.divider.setVisibility(View.GONE);
        }

//        holder.textViewSymptom.setText(model.getBodyPartSymptoms());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class DoubtResultViewholder extends RecyclerView.ViewHolder {
        LinearLayout layoutSymptom;
        TextView textViewParts;
        View divider;

        public DoubtResultViewholder(View itemView) {
            super(itemView);
            layoutSymptom = (LinearLayout) itemView.findViewById(R.id.layoutSymptom);
            textViewParts = (TextView) itemView.findViewById(R.id.textViewParts);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}
