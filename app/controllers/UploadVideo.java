package controllers;

import models.Video;

public class UploadVideo extends Application{
   
	public static void index() {
        render();
    }

    public static void uploadVideo(Video video) {
        video.save();
        index();
    }
    public static void getVideo(long id) {
        Video video = Video.findById(id);
        response.setContentTypeIfNotSet(video.videos.type());
        renderBinary(video.videos.get());
    }
}

