package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video implements Comparable<Video>{

  private final String title;
  private final String videoId;
  private final List<String> tags;
  
  private String currentState;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }
  
  //comparable to compare transactions and sort them from lower to higher
    @Override
    public int compareTo(Video video) {
         return this.getTitle().compareToIgnoreCase(video.getTitle());
    }
    
    public void setCurrentState(String state){
       this.currentState= state;
    }
    
    public String getCurrentState(){
       return this.currentState;
    }
    
    public String toString(){
        return "title "+title+", video_id:" +videoId;
      
    }
}
