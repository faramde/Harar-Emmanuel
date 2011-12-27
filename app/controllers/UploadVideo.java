package controllers;

import models.Picture;
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
        Video video = Picture.findById(id);
        response.setContentTypeIfNotSet(video.video.type());
        renderBinary(video.video.get());
    }
}
