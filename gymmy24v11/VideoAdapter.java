package com.example.vaibhavchopda.gymmy24v11;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


import java.util.List;
//initializing adapters for the recycler view
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    List<YoutubeVideo> youtubeVideoList;
    public VideoAdapter() {
    }

    public VideoAdapter(List<YoutubeVideo> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.card_video,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, final int position) {
        holder.VideoWeb.loadData( youtubeVideoList.get(position).getVideoUrl(),"text/html","utf-8" );


    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
            WebView VideoWeb;


            public VideoViewHolder(View itemView) {
                super(itemView);

                VideoWeb = (WebView) itemView.findViewById(R.id.webVideoView);
                VideoWeb.getSettings().setJavaScriptEnabled(true);
                VideoWeb.setWebChromeClient(new WebChromeClient(){

                                            }
                );
            }
        }
 }
