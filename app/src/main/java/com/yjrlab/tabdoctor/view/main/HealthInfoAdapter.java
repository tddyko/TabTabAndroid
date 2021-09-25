package com.yjrlab.tabdoctor.view.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.simpleimagedownloadview.SimpleImageDownloadView;
import com.yjrlab.tabdoctor.model.BannerModel;
import com.yjrlab.tabdoctor.model.MainContentModel;
import com.yjrlab.tabdoctor.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeonjukim on 2017. 6. 6..
 */

public class HealthInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 100;
    private static final int VIEW_TYPE_CONTENTS = 101;

    private final Context context;
    private List<MainContentModel> models;
    private List<BannerModel> bannerModels;

    HealthInfoAdapter(@NonNull Context context, List<MainContentModel> models) {
        this.context = context;
        if (models == null) {
            this.models = new ArrayList<>();
        } else {
            this.models = models;
        }
    }

    public void setData(List<MainContentModel> models) {
        if (models == null) {
            this.models = new ArrayList<>();
        } else {
            this.models = models;
        }
    }

    public void setBannerModels(List<BannerModel> bannerModels) {
        this.bannerModels = bannerModels;
        notifyDataSetChanged();
    }

    public void addData(List<MainContentModel> models) {
        if (models != null) {
            this.models.addAll(models);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_CONTENTS;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HealthInfoBannerViewHolder(LayoutInflater.from(context).inflate(R.layout.view_banner, parent, false));
        } else {
            return new HealthInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.view_health_information, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HealthInfoBannerViewHolder) {
            HealthInfoBannerViewHolder healthInfoBannerViewHolder = (HealthInfoBannerViewHolder) holder;
            if (bannerModels == null) {
                return;
            }

            //Set Banner
            healthInfoBannerViewHolder.convenientBanner.setPages(new CBViewHolderCreator<HealthInfoAdapter.ConvenientBannerHolderView>() {
                @Override
                public HealthInfoAdapter.ConvenientBannerHolderView createHolder() {
                    return new HealthInfoAdapter.ConvenientBannerHolderView();
                }
            }, bannerModels)
                    .setPageIndicator(new int[]{R.drawable.icon_dot_off, R.drawable.icon_dot_on})
                    .setCanLoop(false);


        } else if (holder instanceof HealthInfoViewHolder) {
            final HealthInfoViewHolder healthInfoViewHolder = (HealthInfoViewHolder) holder;
            final MainContentModel model = models.get(position - 1);
            healthInfoViewHolder.textViewInfoNum.setText(position + "");
            healthInfoViewHolder.textViewTitle.setText(model.getTitle());
            healthInfoViewHolder.textViewContents.setText(model.getShortContent());
            healthInfoViewHolder.imageView.setImageResource(R.drawable.icon_logo);
            ImageLoader.getInstance().cancelDisplayTask(healthInfoViewHolder.imageView);
            ImageLoader.getInstance().displayImage(NetworkManager.makeMainImageUrl(model.getImage()), healthInfoViewHolder.imageView);

            healthInfoViewHolder.buttonMore.setVisibility(View.VISIBLE);
            healthInfoViewHolder.buttonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    healthInfoViewHolder.buttonMore.setVisibility(View.GONE);
                    healthInfoViewHolder.textViewContents.setText(model.getContent());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (models == null) {
            return 1;
        }
        return models.size() + 1;
    }

    private class HealthInfoBannerViewHolder extends RecyclerView.ViewHolder {
        ConvenientBanner<BannerModel> convenientBanner;

        public HealthInfoBannerViewHolder(final View itemView) {
            super(itemView);
            convenientBanner = (ConvenientBanner<BannerModel>) itemView.findViewById(R.id.convenient_banner);

        }
    }

    private class HealthInfoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewInfoNum;
        SimpleImageDownloadView imageView;
        TextView textViewTitle;
        TextView textViewContents;
        ImageView buttonMore;

        public HealthInfoViewHolder(View itemView) {
            super(itemView);
            textViewInfoNum = (TextView) itemView.findViewById(R.id.textViewInfoNum);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewContents = (TextView) itemView.findViewById(R.id.textViewContents);
            imageView = (SimpleImageDownloadView) itemView.findViewById(R.id.imageView);
            buttonMore = (ImageView) itemView.findViewById(R.id.buttonMore);

        }
    }


    private class ConvenientBannerHolderView implements Holder<BannerModel> {
        private SimpleImageDownloadView imageView;

        @Override
        public View createView(Context context) {
            imageView = new SimpleImageDownloadView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, final BannerModel data) {
            imageView.setImageResource(R.drawable.icon_logo);
            //imageView.setImageURL();
            ImageLoader.getInstance().displayImage(NetworkManager.makeBannerImageUrl(data.getImage()), imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = bannerModels.get(position).getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    imageView.getContext().startActivity(intent);
                }
            });
        }


    }
}
