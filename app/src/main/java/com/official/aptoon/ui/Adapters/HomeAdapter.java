package com.official.aptoon.ui.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.official.aptoon.Provider.PrefManager;
import com.official.aptoon.R;
import com.official.aptoon.entity.Data;
import com.official.aptoon.entity.Notification;
import com.official.aptoon.entity.Poster;
import com.official.aptoon.ui.activities.ActorsActivity;
import com.official.aptoon.ui.activities.GenreActivity;
import com.official.aptoon.ui.activities.HomeActivity;
import com.official.aptoon.ui.activities.MyListActivity;
import com.official.aptoon.ui.activities.TopActivity;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import es.dmoral.toasty.Toasty;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private  Activity activity;
    private List<Data> dataList = new ArrayList<>();
    private SlideAdapter slide_adapter;
    private ChannelAdapter channelAdapter;
    private LinearLayoutManager linearLayoutManagerChannelAdapter;
    private ActorAdapter actorAdapter;
    private LinearLayoutManager linearLayoutManagerActorAdapter;
    private LinearLayoutManager linearLayoutManagerGenreAdapter;
    private PosterAdapter posterAdapter;
    private String TAG = "HomeAdapter";
    private String list_name;

    private String list_name_completed = "my_list_completed";
    private String list_name_watching = "my_list_watching";
    private String list_name_plan = "my_list_plan_to_watch";
    private String list_name_canceled = "my_list_canceled";


    // private Timer mTimer;
    public HomeAdapter(List<Data> dataList,  Activity activity) {
        this.dataList = dataList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 0: {
                View v0 = inflater.inflate(R.layout.item_empty, parent, false);
                viewHolder = new EmptyHolder(v0);
                break;
            }
            case 7: {
                View v1 = inflater.inflate(R.layout.item_slides, parent, false);
                viewHolder = new SlideHolder(v1);
                break;
            }
            case 2: {
                View v2 = inflater.inflate(R.layout.item_channels, parent, false);
                viewHolder = new ChannelHolder(v2);
                break;
            }
            case 3: {
                View v3 = inflater.inflate(R.layout.item_actors, parent, false);
                viewHolder = new ActorHolder(v3);
                break;
            }
            case 4: {
                View v4 = inflater.inflate(R.layout.item_genres, parent, false);
                viewHolder = new GenreHolder(v4);
                break;
            }
            case 5: {
                View v5 = inflater.inflate(R.layout.item_facebook_ads, parent, false);
                viewHolder = new FacebookNativeHolder(v5);
                break;
            }
            case 6: {
                View v6  = inflater.inflate(R.layout.item_admob_native_ads, parent, false);
                viewHolder = new AdmobNativeHolder(v6);
                break;
            }
            case 1: {
                View v7 = inflater.inflate(R.layout.item_streaming, parent, false);
                viewHolder = new StreamingHolder(v7);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder_parent, int position) {
        switch (getItemViewType(position)){
            case 7:

                final SlideHolder holder = (SlideHolder) holder_parent;

                slide_adapter = new SlideAdapter(activity, dataList.get(position).getSlides());
                holder.view_pager_slide.setAdapter(slide_adapter);
                holder.view_pager_slide.setOffscreenPageLimit(1);
                holder.view_pager_slide.setClipToPadding(false);
                holder.view_pager_slide.setPageMargin(0);
                holder.view_pager_indicator.setupWithViewPager(holder.view_pager_slide);
                holder.view_pager_slide.setCurrentItem(dataList.get(position).getSlides().size() / 2);
                slide_adapter.notifyDataSetChanged();
                break;
            case 2:
                final ChannelHolder holder_channel = (ChannelHolder) holder_parent;
                this.linearLayoutManagerChannelAdapter=  new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                this.channelAdapter =new ChannelAdapter(dataList.get(position).getChannels(),activity);
                holder_channel.recycle_view_channels_item.setHasFixedSize(true);
                holder_channel.recycle_view_channels_item.setAdapter(channelAdapter);
                holder_channel.recycle_view_channels_item.setLayoutManager(linearLayoutManagerChannelAdapter);
                channelAdapter.notifyDataSetChanged();
                holder_channel.image_view_item_channel_more.setOnClickListener(v -> {
                    ((HomeActivity) activity).goToTV();
                });
                break;
            case 3:
                final ActorHolder holder_actor = (ActorHolder) holder_parent;
                this.linearLayoutManagerActorAdapter=  new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                this.actorAdapter =new ActorAdapter(dataList.get(position).getActors(),activity);
                holder_actor.recycle_view_actors_item_actors.setHasFixedSize(true);
                holder_actor.recycle_view_actors_item_actors.setAdapter(actorAdapter);
                holder_actor.recycle_view_actors_item_actors.setLayoutManager(linearLayoutManagerActorAdapter);
                actorAdapter.notifyDataSetChanged();
                holder_actor.image_view_item_actors_more.setOnClickListener(v -> {
                    Intent intent  =  new Intent(activity.getApplicationContext(), ActorsActivity.class);
                    (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                });
                break;
            case 4:
                final GenreHolder holder_genres = (GenreHolder) holder_parent;
                holder_genres.text_view_item_genre_title.setText(dataList.get(position).getGenre().getTitle());
                holder_genres.image_view_item_genre_more.setOnClickListener(v-> {
                    if (dataList.get(position).getGenre().getId() == -1){
                        Intent intent  =  new Intent(activity.getApplicationContext(), TopActivity.class);
                        intent.putExtra("order", "rating");
                        (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                    }else if (dataList.get(position).getGenre().getId() == 0){
                        Intent intent  =  new Intent(activity.getApplicationContext(), TopActivity.class);
                        intent.putExtra("order", "views");
                        (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                    }else if (dataList.get(position).getGenre().getId() == -2){
                        Intent intent  =  new Intent(activity.getApplicationContext(), MyListActivity.class);
                        (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                    }else{
                        Intent intent  =  new Intent(activity.getApplicationContext(), GenreActivity.class);
                        intent.putExtra("genre", dataList.get(position).getGenre());
                        (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                    }

                });
                this.linearLayoutManagerGenreAdapter=  new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                if (dataList.get(position).getGenre().getId() == -2)
                    this.posterAdapter =new PosterAdapter(dataList.get(position).getGenre().getPosters(),activity,true);
                else
                    this.posterAdapter =new PosterAdapter(dataList.get(position).getGenre().getPosters(),activity);

                holder_genres.recycle_view_posters_item_genre.setHasFixedSize(true);
                holder_genres.recycle_view_posters_item_genre.setAdapter(posterAdapter);
                holder_genres.recycle_view_posters_item_genre.setLayoutManager(linearLayoutManagerGenreAdapter);
                posterAdapter.notifyDataSetChanged();

                break;
            case 6:{
                final AdmobNativeHolder holder_admob = (AdmobNativeHolder) holder_parent;

                holder_admob.adLoader.loadAd(new AdRequest.Builder().build());

                break;
            }
            case 1:{
                final StreamingHolder streamingHolder = (StreamingHolder) holder_parent;

                String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
                Uri vidUri = Uri.parse(vidAddress);
                streamingHolder.video_view_item.setVideoURI(vidUri);
                streamingHolder.video_view_item.start();
                streamingHolder.btn_show_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = (LayoutInflater) HomeActivity.getInstance().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.dialog_information_window, null);
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        HomeActivity.getInstance().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int screen_width = displayMetrics.widthPixels;
                        int width = (int)Math.floor(screen_width/2);
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        boolean focusable = true;
                        int[] pos = new int[2];
                        streamingHolder.btn_show_info.getLocationOnScreen(pos);
                        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                        TextView info_type = popupView.findViewById(R.id.info_type);
                        TextView info_episodes = popupView.findViewById(R.id.info_episodes);
                        TextView info_status = popupView.findViewById(R.id.info_status);
                        TextView info_aired = popupView.findViewById(R.id.info_aired);
                        TextView info_premiered = popupView.findViewById(R.id.info_premiered);
                        TextView info_broadcast = popupView.findViewById(R.id.info_broadcast);
                        TextView info_producers = popupView.findViewById(R.id.info_producers);
                        TextView info_licensors = popupView.findViewById(R.id.info_licensors);
                        TextView info_studio = popupView.findViewById(R.id.info_studio);
                        TextView info_source = popupView.findViewById(R.id.info_source);
                        ImageView btn_close = popupView.findViewById(R.id.btn_close);
                        info_type.setSelected(true);
                        info_episodes.setSelected(true);
                        info_status.setSelected(true);
                        info_aired.setSelected(true);
                        info_premiered.setSelected(true);
                        info_broadcast.setSelected(true);
                        info_producers.setSelected(true);
                        info_licensors.setSelected(true);
                        info_studio.setSelected(true);
                        info_source.setSelected(true);
                        btn_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });



                        popupWindow.showAtLocation(streamingHolder.itemView,0, pos[0],pos[1]+100);

                    }
                });

                streamingHolder.btn_add_to_mylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = (LayoutInflater) HomeActivity.getInstance().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View dialog_view = inflater.inflate(R.layout.dialog_add_list_window, null);
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        HomeActivity.getInstance().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int screen_width = displayMetrics.widthPixels;
                        int width = (int)Math.floor(screen_width/2);
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        boolean focusable = true;
                        int[] pos = new int[2];
                        streamingHolder.btn_show_info.getLocationOnScreen(pos);
                        Dialog dialog = new Dialog(HomeActivity.getInstance());
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Rect displayRectangle = new Rect();
                        Window window = HomeActivity.getInstance().getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                        dialog_view.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        ImageView btn_close = dialog_view.findViewById(R.id.btn_close);
                        Poster poster = new Poster();
                        poster.setId(00001);
                        poster.setClassification("Classification");
                        poster.setComment(true);
                        poster.setCover("Cover");
                        poster.setDescription("Story about how Kakashi be a Ninja");
                        poster.setDownloadas("nothing");
                        poster.setDuration("duration");
                        poster.setRating((float)6.7);
                        poster.setImdb("IMDB");
                        poster.setTitle("Naruto and Kakashi");
                        poster.setType("Movie");
                        poster.setYear("2020");
                        poster.setPlayas("MP4");
                        poster.setImage("https://firebasestorage.googleapis.com/v0/b/lancul-10966.appspot.com/o/Restaurant%2Fdownload%20(3).jpeg?alt=media&token=29eb9584-fb2f-43a3-aa41-a0b09f73304c");
                        btn_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        Spinner spin_status = dialog_view.findViewById(R.id.spin_status);
                        Spinner spin_rating = dialog_view.findViewById(R.id.spin_rating);
                        Button btn_sumit = dialog_view.findViewById(R.id.btn_sumit);
                        Button btn_cancel = dialog_view.findViewById(R.id.btn_cancel);
                        btn_sumit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (spin_status.getSelectedItemPosition()){
                                    case 0:
                                        check_and_remove(poster);
                                        addFavotite(list_name_completed, poster);
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        check_and_remove(poster);
                                        addFavotite(list_name_watching, poster);
                                        dialog.dismiss();
                                        break;
                                    case 2:
                                        check_and_remove(poster);
                                        addFavotite(list_name_plan, poster);
                                        dialog.dismiss();
                                        break;
                                    case 3:
                                        check_and_remove(poster);
                                        addFavotite(list_name_canceled, poster);
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        });
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                });
                break;
            }
        }
    }
    private void check_and_remove(Poster poster){
        List<Poster> completed_list =Hawk.get("my_list_completed");
        List<Poster> watching_list =Hawk.get("my_list_watching");
        List<Poster> plan_list =Hawk.get("my_list_plan_to_watch");
        List<Poster> canceled_list =Hawk.get("my_list_canceled");
        Integer position;
        boolean find = false;
        try{
            for (int i = 0; i < completed_list.size(); i++) {
                if (completed_list.get(i).getId().equals(poster.getId())) {
                    find = true;
                    completed_list.remove(i);
                    Hawk.put("my_list_completed",completed_list);
                }
            }

        }catch (Exception e){
            Log.e(TAG, "no in completed list");
        }
        if (!find){
            try{
                for (int i = 0; i < watching_list.size(); i++) {
                    if (watching_list.get(i).getId().equals(poster.getId())) {
                        find = true;
                        watching_list.remove(i);
                        Hawk.put("my_list_watching",watching_list);
                    }
                }

            }catch (Exception e){
                Log.e(TAG, "no in watching list");
            }
        }
        if (!find){
            try{
                for (int i = 0; i < plan_list.size(); i++) {
                    if (plan_list.get(i).getId().equals(poster.getId())) {
                        find = true;
                        plan_list.remove(i);
                        Hawk.put("my_list_plan_to_watch",plan_list);
                    }
                }


            }catch (Exception e){
                Log.e(TAG, "no in plan list");
            }
        }
        if (!find){
            try{
                for (int i = 0; i < canceled_list.size(); i++) {
                    if (canceled_list.get(i).getId().equals(poster.getId())) {
                        find = true;
                        canceled_list.remove(i);
                        Hawk.put("my_list_canceled",canceled_list);
                    }
                }


            }catch (Exception e){
                Log.e(TAG, "no in cancel list");
            }
        }

    }

    private void addFavotite(String hawk_index, Poster poster) {

        switch (hawk_index){
            case "my_list_completed":
                list_name = "Complete";
                break;
            case "my_list_watching":
                list_name = "Watching";
                break;
            case "my_list_plan_to_watch":
                list_name = "Plan to Watch";
                break;
            case "my_list_canceled":
                list_name = "Canceled";
                break;
        }

        List<Poster> favorites_list =Hawk.get(hawk_index);

        Boolean exist = false;
        if (favorites_list == null) {
            favorites_list = new ArrayList<>();
        }
        int fav_position = -1;
        for (int i = 0; i < favorites_list.size(); i++) {
            if (favorites_list.get(i).getId().equals(poster.getId())) {
                exist = true;
                fav_position = i;
            }
        }
        if (exist == false) {
            favorites_list.add(poster);
            Hawk.put(hawk_index,favorites_list);
            Toasty.info(HomeActivity.getInstance(), "This movie has been added to your "+ list_name +" list", Toast.LENGTH_SHORT).show();
        }else{
            favorites_list.remove(fav_position);
            Hawk.put(hawk_index,favorites_list);
            Toasty.warning(HomeActivity.getInstance()  , "This movie has been removed from your "+ list_name +" list", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if(dataList.get(position).getSlides() != null){
            type = 1;
        }
        if(dataList.get(position).getChannels() != null){
            type = 2;
        }
        if(dataList.get(position).getActors() != null){
            type = 3;
        }
        if(dataList.get(position).getGenre() != null){
            type = 4;
        }
        if (dataList.get(position).getViewType() == 5){
            type = 5;

        }
        if (dataList.get(position).getViewType() == 6){
            type = 6;

        }
        return type;
    }
    private class SlideHolder extends RecyclerView.ViewHolder {
        private final ViewPagerIndicator view_pager_indicator;
        private final ViewPager view_pager_slide;
        public SlideHolder(View itemView) {
            super(itemView);
            this.view_pager_indicator=(ViewPagerIndicator) itemView.findViewById(R.id.view_pager_indicator);
            this.view_pager_slide=(ViewPager) itemView.findViewById(R.id.view_pager_slide);
        }
    }

    private class ChannelHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recycle_view_channels_item;
        private final ImageView image_view_item_channel_more;
        public ChannelHolder(View itemView) {
            super(itemView);
            this.recycle_view_channels_item=(RecyclerView) itemView.findViewById(R.id.recycle_view_channels_item);
            this.image_view_item_channel_more=  (ImageView) itemView.findViewById(R.id.image_view_item_channel_more);

        }
    }

    private class StreamingHolder extends RecyclerView.ViewHolder {
        private final VideoView video_view_item;
        private final LinearLayout btn_show_info, btn_share, btn_add_to_mylist;


        public StreamingHolder(View itemView) {
            super(itemView);
            this.video_view_item = (VideoView) itemView.findViewById(R.id.myVideo);
            this.btn_show_info = (LinearLayout)itemView.findViewById(R.id.btn_info);
            this.btn_share = (LinearLayout)itemView.findViewById(R.id.btn_share);
            this.btn_add_to_mylist = (LinearLayout)itemView.findViewById(R.id.btn_add_to_list);
        }
    }

    private class ActorHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recycle_view_actors_item_actors;
        private final ImageView image_view_item_actors_more;

        public ActorHolder(View itemView) {
            super(itemView);
            this.recycle_view_actors_item_actors=  (RecyclerView) itemView.findViewById(R.id.recycle_view_actors_item_actors);
            this.image_view_item_actors_more=  (ImageView) itemView.findViewById(R.id.image_view_item_actors_more);
        }
    }
    private class GenreHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recycle_view_posters_item_genre;
        private final TextView text_view_item_genre_title;
        private final ImageView image_view_item_genre_more;

        public GenreHolder(View itemView) {
            super(itemView);
            this.recycle_view_posters_item_genre=  (RecyclerView) itemView.findViewById(R.id.recycle_view_posters_item_genre);
            this.text_view_item_genre_title=  (TextView) itemView.findViewById(R.id.text_view_item_genre_title);
            this.image_view_item_genre_more=  (ImageView) itemView.findViewById(R.id.image_view_item_genre_more);
        }
    }
    public class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }

    public  class FacebookNativeHolder extends  RecyclerView.ViewHolder {
        private final String TAG = "WALLPAPERADAPTER";
        private LinearLayout nativeAdContainer;
        private LinearLayout adView;
        private NativeAd nativeAd;
        public FacebookNativeHolder(View view) {
            super(view);
            loadNativeAd(view);
        }

        private void loadNativeAd(final View view) {
            PrefManager prefManager= new PrefManager(activity);

            nativeAd = new NativeAd(activity,prefManager.getString("ADMIN_NATIVE_FACEBOOK_ID"));
            nativeAd.setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                    Log.e(TAG, "Native ad finished downloading all assets.");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Native ad failed to load
                    Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Native ad is loaded and ready to be displayed
                    Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                    // Race condition, load() called again before last ad was displayed
                    if (nativeAd == null || nativeAd != ad) {
                        return;
                    }
                   /* NativeAdViewAttributes viewAttributes = new NativeAdViewAttributes()
                            .setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark))
                            .setTitleTextColor(Color.WHITE)
                            .setDescriptionTextColor(Color.WHITE)
                            .setButtonColor(Color.WHITE);

                    View adView = NativeAdView.render(activity, nativeAd, NativeAdView.Type.HEIGHT_300, viewAttributes);

                    LinearLayout nativeAdContainer = (LinearLayout) view.findViewById(R.id.native_ad_container);
                    nativeAdContainer.addView(adView);*/
                    // Inflate Native Ad into Container
                    inflateAd(nativeAd,view);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                    Log.d(TAG, "Native ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                    Log.d(TAG, "Native ad impression logged!");
                }
            });

            // Request an ad
            nativeAd.loadAd();
        }

        private void inflateAd(NativeAd nativeAd,View view) {

            nativeAd.unregisterView();

            // Add the Ad view into the ad container.
            nativeAdContainer = view.findViewById(R.id.native_ad_container);
            LayoutInflater inflater = LayoutInflater.from(activity);
            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
            adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, nativeAdContainer, false);
            nativeAdContainer.addView(adView);

            // Add the AdChoices icon
            LinearLayout adChoicesContainer = view.findViewById(R.id.ad_choices_container);
            AdChoicesView adChoicesView = new AdChoicesView(activity, nativeAd, true);
            adChoicesContainer.addView(adChoicesView, 0);

            // Create native UI using the ad metadata.
            AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
            TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
            MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
            TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
            TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
            TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
            Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

            // Set the Text.
            nativeAdTitle.setText(nativeAd.getAdvertiserName());
            nativeAdBody.setText(nativeAd.getAdBodyText());
            nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

            // Create a list of clickable views
            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);

            // RegisterActivity the Title and CTA button to listen for clicks.
            nativeAd.registerViewForInteraction(
                    adView,
                    nativeAdMedia,
                    nativeAdIcon,
                    clickableViews);
        }

    }
    public class AdmobNativeHolder extends RecyclerView.ViewHolder {
        private final AdLoader adLoader;
        private UnifiedNativeAd nativeAd;
        private FrameLayout frameLayout;

        public AdmobNativeHolder(@NonNull View itemView) {
            super(itemView);

            PrefManager prefManager= new PrefManager(activity);

            frameLayout = (FrameLayout) itemView.findViewById(R.id.fl_adplaceholder);
            AdLoader.Builder builder = new AdLoader.Builder(activity, prefManager.getString("ADMIN_NATIVE_ADMOB_ID"));

            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                // OnUnifiedNativeAdLoadedListener implementation.
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    // You must call destroy on old ads when you are done with them,
                    // otherwise you will have a memory leak.
                    if (nativeAd != null) {
                        nativeAd.destroy();
                    }
                    nativeAd = unifiedNativeAd;

                    UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater()
                            .inflate(R.layout.ad_unified, null);
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                }

            });

            VideoOptions videoOptions = new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build();

            NativeAdOptions adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .build();

            builder.withNativeAdOptions(adOptions);

            this.adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {


                }
            }).build();

        }
    }

    /**
     * Populates a {@link UnifiedNativeAdView} object with data from a given
     * {@link UnifiedNativeAd}.
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        com.google.android.gms.ads.formats.MediaView mediaView = adView.findViewById(R.id.ad_media);

        mediaView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                if (child instanceof ImageView) {
                    ImageView imageView = (ImageView) child;
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
            }
        });
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {

        }
    }
}
