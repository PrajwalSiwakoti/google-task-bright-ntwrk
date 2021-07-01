package com.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class used to represent a Playlist
 */
class VideoPlaylist{

    private String name;

    private HashMap<String, Video> videos;

    public VideoPlaylist(String name) {
        this.name = name;
        this.videos = new HashMap<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

   
     // Function to sort map by Key
    public static Map<String,VideoPlaylist> sortbykey(Map<String,VideoPlaylist> videoPlaylists)
    {
        // TreeMap to store values of HashMap by sorting
        TreeMap<String, VideoPlaylist> sorted= new TreeMap<>(videoPlaylists);
 
      return sorted;
    }
    HashMap<String,Video> getVideos() {
        return this.videos;
    }
    
    public void addVideo(Video video){
        this.videos.put(video.getVideoId(), video);
    }
    
    public void removeVideo(Video video){
        this.videos.remove(video.getVideoId());
    }
    
     public void clearVideos(){
        this.videos.clear();
    }
    
    public Video getVideo(String videoId) {
        return this.videos.get(videoId);
    }
}
