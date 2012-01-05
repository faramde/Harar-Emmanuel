package controllers;

import models.Video;


public class DisplayVideos extends Application{
    
    public static void show(Long id) {
        Video video = Video.findById(id);
        render(video);
    }
}
