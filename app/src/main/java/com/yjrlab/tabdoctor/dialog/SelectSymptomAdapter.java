package com.yjrlab.tabdoctor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.CheckableButton;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;

import java.util.List;

/**
 * Created by yeonjukim on 2017. 6. 6..
 */

public class SelectSymptomAdapter extends RecyclerView.Adapter<SelectSymptomAdapter.SelectSymptomViewHolder> {
    private final Context context;
    private final Dialog dialog;
    private List<BodyPartSymptom> models;

    public SelectSymptomAdapter(Context context, Dialog dialog, List<BodyPartSymptom> models) {
        this.context = context;
        this.models = models;
        this.dialog = dialog;
    }

    @Override
    public SelectSymptomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectSymptomViewHolder(LayoutInflater.from(context).inflate(R.layout.view_symptoms, parent, false));
    }

    @Override
    public void onBindViewHolder(final SelectSymptomViewHolder holder, int position) {
        final BodyPartSymptom model = models.get(position);

        holder.radioButton.setChecked(((DialogSelectSymptoms) dialog).selectedSymptoms.contains(model));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.radioButton.toggle();
                holder.radioButton.callOnClick();
            }
        });

        holder.radioButton.setOnCheckedChangeWidgetListener(new CheckableButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CheckableButton buttonView, boolean isChecked) {
                if(isChecked){
                    ((DialogSelectSymptoms) dialog).selectedSymptoms.add(model);
                }else{
                    ((DialogSelectSymptoms) dialog).selectedSymptoms.remove(model);

                }
            }
        });
        holder.textViewSymptom.setText(model.getBsContent());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class SelectSymptomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSymptom;
        LinearLayout layout;
        CheckableButton radioButton;

        public SelectSymptomViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            textViewSymptom = (TextView) itemView.findViewById(R.id.textViewSymptom);
            radioButton = (CheckableButton) itemView.findViewById(R.id.radioButton);
            this.setIsRecyclable(false);
        }
    }
}
