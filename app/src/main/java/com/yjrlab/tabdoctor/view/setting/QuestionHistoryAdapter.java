package com.yjrlab.tabdoctor.view.setting;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.model.SelfDiagnosisModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yeonjukim on 2017. 6. 6..
 */

public class QuestionHistoryAdapter extends RecyclerView.Adapter<QuestionHistoryAdapter.QuestionHistoryViewHolder> {

    public static final String INTENT_NUM = "num";
    public static final String INTENT_HISTORY_LIST = "history";
    private final Context context;
    private List<SelfDiagnosisModel> models;

    QuestionHistoryAdapter(@NonNull Context context, List<SelfDiagnosisModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public QuestionHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.view_question_history, parent, false));
    }

    public void setOffset(List<SelfDiagnosisModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(QuestionHistoryViewHolder holder, final int position) {
        final SelfDiagnosisModel model = models.get(position);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionHistoryDetailActivity.class);
                intent.putExtra(INTENT_NUM, position);
                intent.putExtra(INTENT_HISTORY_LIST, new ArrayList<>(models));
                context.startActivity(intent);
            }
        });

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(model.getRegDate());
            String formatDate = new SimpleDateFormat("yyyy.MM.dd").format(date);
            holder.textViewDate.setText(formatDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textViewQuestionPart.setText(model.getPobName());
        holder.textViewQuestionStatus.setText(model.flagName());

    }

    @Override
    public int getItemCount() {
        if (models == null) {
            return 0;
        } else {
            return models.size();
        }
    }


    class QuestionHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        TextView textViewQuestionPart;
        TextView textViewQuestionStatus;

        LinearLayout layout;

        public QuestionHistoryViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewQuestionPart = (TextView) itemView.findViewById(R.id.textViewQuestionPart);
            textViewQuestionStatus = (TextView) itemView.findViewById(R.id.textViewQuestionStatus);

        }
    }
}
