package com.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class VideoPlayer {

    private final VideoLibrary videoLibrary;

    private Video currentVideo;

    //  private ArrayList<VideoPlaylist> videoPlayLists;
    private HashMap<String, VideoPlaylist> videoPlayLists;

    public VideoPlayer() {
        this.videoLibrary = new VideoLibrary();
        // this.videoPlayLists = new ArrayList<>();
        this.videoPlayLists = new HashMap<>();
    }

    private void setCurrentVideo(Video video) {
        video.setCurrentState("playing");
        this.currentVideo = video;
    }

    private Video getCurrentVideo() {
        return this.currentVideo;
    }

    public void numberOfVideos() {
        System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
    }

    private String formatVideoMessage(Video video) {
        return video.getTitle() + " (" + video.getVideoId() + ')' + " [" + String.join(" ", video.getTags()) + ']';
    }

    public void showAllVideos() {
        //System.out.println("showAllVideos needs implementation");
        System.out.println("Here's a list of all available videos:");
        List<Video> videos = videoLibrary.getVideos();
        Collections.sort(videos);

        for (Video video : videos) {
            // String output =video.getTitle()+""+(video.getVideoId())+[video.getTags()];
            System.out.println(video.getTitle() + " (" + video.getVideoId() + ')' + " [" + String.join(" ", video.getTags()) + ']');
        }

    }

    public void playVideo(String videoId) {
        Video video = this.videoLibrary.getVideo(videoId);
        playVideo(video);
    }

    public void playVideo(Video video) {
        if (video != null) {
            if (getCurrentVideo() != null) {
                System.out.println("Stopping video: " + this.currentVideo.getTitle());//stopping current video first
            }

            setCurrentVideo(video);
            System.out.println("Playing video: " + video.getTitle());
        } else {
            System.out.println("Cannot play video: Video does not exist");
        }
    }

    public void stopVideo() {
        Video currentVideo = getCurrentVideo();
        if (currentVideo != null) {
            System.out.println("Stopping video: " + currentVideo.getTitle());
            this.currentVideo = null;
        } else {
            System.out.println("Cannot stop video: No video is currently playing.");
        }
    }

    public void playRandomVideo() {
//        Video currentVideo = getCurrentVideo();
//        if (currentVideo != null) {
//            System.out.println("Stopping video: " + currentVideo.getTitle());
//        }
        Random rand = new Random();
        List<Video> videos = videoLibrary.getVideos();
        if (videos.size() > 0) {
            Video randomVideo = videos.get(rand.nextInt(videos.size()));
            playVideo(randomVideo);
        } else {
            System.out.println("No videos available.");
        }

    }

    public void pauseVideo() {
        Video video = getCurrentVideo();
        if (video != null) {
            String currentState = video.getCurrentState();
            if (currentState == "paused") {
                System.out.println("Video already paused: " + video.getTitle());
            } else {
                video.setCurrentState("paused");
                System.out.println("Pausing video: " + video.getTitle());
            }
        } else {
            System.out.println("Cannot pause video: No video is currently playing.");
        }
    }

    public void continueVideo() {
        Video video = getCurrentVideo();
        if (video != null) {
            String currentState = video.getCurrentState();
            if (currentState == "paused") {
                video.setCurrentState("playing");
                System.out.println("Continuing video: " + video.getTitle());
            } else {
                System.out.println("Cannot continue video: Video is not paused");
            }
        } else {
            System.out.println("Cannot continue video: No video is currently playing.");
        }
    }

    public void showPlaying() {
        Video video = getCurrentVideo();
        if (video != null) {
            String currentState = video.getCurrentState();
            String outputMessage = formatVideoMessage(video);
            if (currentState == "paused") {
                outputMessage = outputMessage + " - PAUSED";
            }
            System.out.println("Currently playing: " + outputMessage);
        } else {
            System.out.println("No video is currently playing");
        }
    }

    public void createPlaylist(String playlistName) {
        String validatedName = validatePlaylistName(playlistName);
        if (validatedName != null) {
            this.videoPlayLists.put(validatedName, new VideoPlaylist(validatedName));
            System.out.println("Successfully created new playlist: " + playlistName);
        }
    }

    private String validatePlaylistName(String playlistName) {
        if (playlistName.trim().isEmpty()) {
            System.out.println("Please provide playlistname");
            return null;
        } else {
            playlistName = playlistName.toLowerCase();

            playlistName.replaceAll("\\s+", "_");
            // videoPlayLists.put("test", new VideoPlaylist("test"));
            //videoPlayLists.put("test1", new VideoPlaylist("test1"));

            // iterating it using forEach.
            //videoPlayLists.forEach((key,value) -> System.out.println(key + " = " + value.toString()));
            for (Map.Entry<String, VideoPlaylist> entry : this.videoPlayLists.entrySet()) {
                // System.out.println(entry.getKey().toLowerCase() + " " + playlistName.toLowerCase());
                String key = entry.getKey().toLowerCase();
                if (key == playlistName) {
                    System.out.println("Cannot create playlist: A playlist with the same name already exists");
                    playlistName = null;
                    break;

                }
            }
            return playlistName;
        }

    }

    private Map<String, VideoPlaylist> getVideoPlaylists() {
        return this.videoPlayLists;
    }

    VideoPlaylist getVideoPlaylist(String playlistName) {
        return this.videoPlayLists.get(playlistName.toLowerCase());
    }

    public void addVideoToPlaylist(String playlistName, String videoId) {

        VideoPlaylist playlist = getVideoPlaylist(playlistName);
        if (playlist == null) {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
            return;
        }
        Video video = videoLibrary.getVideo(videoId);
        if (video == null) {
            System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
            return;
        }

        for (Map.Entry<String, Video> entry : playlist.getVideos().entrySet()) {
            if (entry.getKey().toLowerCase() == video.getVideoId().toLowerCase()) {
                System.out.println("Cannot add video to " + playlistName + ": Video already added");
                return;
            }
        }
        playlist.addVideo(video);
        System.out.println("Added video to " + playlistName + ": " + video.getTitle());
    }

    public Map<String, VideoPlaylist> sortbykey(Map<String, VideoPlaylist> videoPlaylists) {
        // TreeMap to store values of HashMap by sorting
        TreeMap<String, VideoPlaylist> sorted = new TreeMap<>(videoPlaylists);

        return sorted;
    }

    public void showAllPlaylists() {

        //createPlaylist("ab");
        // createPlaylist("abcd");
        HashMap<String, VideoPlaylist> videoPlaylists = this.videoPlayLists;
        if (videoPlaylists.size() < 1) {
            System.out.println("No playlists exist yet");
            return;
        }
        System.out.println("Showing all playlists:");
        Map<String, VideoPlaylist> sortedVideoPlaylists = sortbykey(videoPlaylists);
        for (Map.Entry<String, VideoPlaylist> entry : sortedVideoPlaylists.entrySet()) {
            System.out.println(entry.getKey());
        }

    }

    public void showPlaylist(String playlistName) {

        // createPlaylist("my_playlist");
        // addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
        VideoPlaylist playlist = getVideoPlaylist(playlistName);
        if (playlist == null) {
            System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");

            return;
        }
        HashMap<String, Video> videos = playlist.getVideos();

        System.out.println("Showing playlist: " + playlistName);
        if (videos.size() > 0) {
            for (Map.Entry<String, Video> entity : playlist.getVideos().entrySet()) {
                System.out.println(formatVideoMessage(entity.getValue()));
            }
        } else {
            System.out.println("No videos here yet");
        }

    }

    public void removeFromPlaylist(String playlistName, String videoId) {
        VideoPlaylist playlist = getVideoPlaylist(playlistName);
        if (playlist == null) {
            System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
            return;
        }
        Video video = videoLibrary.getVideo(videoId);
        if (video == null) {
            System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
            return;
        }
        Video playlistVideo = playlist.getVideo(videoId);
        if (playlistVideo == null) {
            System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
            return;
        }

        playlist.removeVideo(video);
        System.out.println("Removed video from " + playlist.getName() + ": " + video.getTitle());
    }

    public void clearPlaylist(String playlistName) {
        VideoPlaylist playlist = getVideoPlaylist(playlistName);
        if (playlist == null) {
            System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
            return;
        }
        playlist.clearVideos();
        System.out.println("Successfully removed all videos from " + playlistName);
    }

    public void deletePlaylist(String playlistName) {
        VideoPlaylist playlist = getVideoPlaylist(playlistName);
        if (playlist == null) {
            System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
            return;
        }
        videoPlayLists.remove(playlistName);
        System.out.println("Deleted playlist: " + playlistName);
    }

    public void searchVideos(String searchTerm) {
        List<Video> videos = videoLibrary.getVideos();
        List<Video> outputVideos = videos.stream()
                .filter(video -> video.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        // List<Video> outputVideos = new ArrayList<>();
//        for (Video video : videos) {
//            if (video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
//                outputVideos.add(video);
//            }
//        }

        if (outputVideos.size() > 0) {
            System.out.println("Here are the results for " + searchTerm + ':');
            int index = 0;
            for (Video video : outputVideos) {
                // String output =video.getTitle()+""+(video.getVideoId())+[video.getTags()];
                index = index + 1;
                System.out.println(index + ") " + formatVideoMessage(video));
            }
            System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
            System.out.println("If your answer is not a valid number, we'll assume it's a no.");

            var scanner = new Scanner(System.in);
            while (true) {
                var inputIndex = scanner.nextInt();
                Video video = outputVideos.get(inputIndex);
                if (video != null) {
                    playVideo(video);
                    return;
                }

            }
        } else {
            System.out.println("No search results for " + searchTerm);
        }

        //System.out.println(outputVideos);
    }

    public void searchVideosWithTag(String videoTag) {
        List<Video> videos = videoLibrary.getVideos();
        final String newVideoTag = videoTag.toLowerCase();
        List<Video> outputVideos = new ArrayList<>();
        for (Video video : videos) {
            for (String loopVideoTag : video.getTags()) {
                //System.out.println(loopVideoTag.toLowerCase() + " " + newVideoTag);
                if (loopVideoTag.toLowerCase().contains(newVideoTag)) {
                    outputVideos.add(video);
                }
            }
        }

// List<Video> outputVideos = videos.stream()
//                .filter(video -> video.getTags().stream().allMatch(loopVideoTag->loopVideoTag.toLowerCase().contains(newVideoTag)))
//                      //  .filter(car -> car.getEngines().stream().allMatch(engine -> notEmpty(engine.getParts())))
//                      //filter(videoTag-> tag.toLowerCase().contains(newVideoTag.toLowerCase()))
//                .collect(Collectors.toList());
        if (outputVideos.size() > 0) {
            System.out.println("Here are the results for " + videoTag + ':');
            int index = 0;
            for (Video video : outputVideos) {
                // String output =video.getTitle()+""+(video.getVideoId())+[video.getTags()];
                index = index + 1;
                System.out.println(index + ") " + formatVideoMessage(video));
            }
            System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
            System.out.println("If your answer is not a valid number, we'll assume it's a no.");
            try{
                  var scanner = new Scanner(System.in);

            int inputIndex = scanner.nextInt();
            int newInputIndex = (int)inputIndex;
            if(newInputIndex > 0 && newInputIndex < outputVideos.size()){
                Video video = outputVideos.get(newInputIndex);
            if (video != null) {
                playVideo(video);
                return;
            }
            else{
                return;
            }
            }
            }catch(Exception $ex){
                throw $ex;
            }
          
           

        } else {
            System.out.println("No search results for " + videoTag);
        }

        //System.out.println(outputVideos);
    }

    public void flagVideo(String videoId) {
        System.out.println("flagVideo needs implementation");
    }

    public void flagVideo(String videoId, String reason) {
        System.out.println("flagVideo needs implementation");
    }

    public void allowVideo(String videoId) {
        System.out.println("allowVideo needs implementation");
    }
}
