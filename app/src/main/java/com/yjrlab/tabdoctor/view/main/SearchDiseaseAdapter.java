package com.yjrlab.tabdoctor.view.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.model.HealthDicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeonjukim on 2017. 6. 6..
 */

public class SearchDiseaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEWTYPE_HEADER = 0;
    private static final int VIEWTYPE_BODY = 1;
    private final Context context;
    private List<HealthDicModel> models;
    private String keyword;
    private OnDiseaseClickListener listener;
    private int totalCount;

    SearchDiseaseAdapter(@NonNull Context context, ArrayList<HealthDicModel> models) {
        this.context = context;
        this.models = models;
    }

    public void setOnDiseaseClickListener(OnDiseaseClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_HEADER) {
            return new DiseaseSearchHeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.view_disease_search_head, parent, false));
        } else if (viewType == VIEWTYPE_BODY) {
            return new DiseaseSearchViewHolder(LayoutInflater.from(context).inflate(R.layout.view_disease_search, parent, false));
        }
        return null;
    }

    public void setKeyword(String keyword, int totalCount, List<HealthDicModel> models) {
        this.totalCount = totalCount;
        this.models = models;
        this.keyword = keyword;
        notifyDataSetChanged();
    }

    public void addData(List<HealthDicModel> models) {
        if (models != null) {
            this.models.addAll(models);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DiseaseSearchHeaderViewHolder) {
            DiseaseSearchHeaderViewHolder holder1 = (DiseaseSearchHeaderViewHolder) holder;
            if (keyword != null) {
                holder1.textViewKeyword.setText(keyword);
                holder1.textViewDiseaseNum.setText(totalCount + "건");

                if (keyword.equals("\"\"")) {
                    Dlog.d("keyword=\"\" "+keyword);
                    holder1.itemView.getLayoutParams().height=0;
                }else{
                    Dlog.d(keyword);
                    holder1.itemView.getLayoutParams().height=150;
                }
            }

        } else if (holder instanceof DiseaseSearchViewHolder) {
            final HealthDicModel model = models.get(position - 1);
            DiseaseSearchViewHolder holder1 = (DiseaseSearchViewHolder) holder;
            holder1.textViewDiseaseInfo.setText(model.getName());

            holder1.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickDisease(model);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (models == null) {
            return 0;
        }
        return models.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return VIEWTYPE_HEADER;
        return VIEWTYPE_BODY;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    private class DiseaseSearchHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDiseaseNum;
        TextView textViewKeyword;

        DiseaseSearchHeaderViewHolder(View itemView) {
            super(itemView);
            textViewDiseaseNum = (TextView) itemView.findViewById(R.id.textViewDiseaseNum);
            textViewKeyword = (TextView) itemView.findViewById(R.id.textViewKeyword);

        }
    }

    private class DiseaseSearchViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDiseaseInfo;
        LinearLayout layout;

        DiseaseSearchViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            textViewDiseaseInfo = (TextView) itemView.findViewById(R.id.textViewDiseaseInfo);

        }
    }

    interface OnDiseaseClickListener {
        void onClickDisease(HealthDicModel model);
    }
}
