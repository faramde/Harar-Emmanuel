package controllers;


import models.Video;
import play.*;
import play.mvc.*;

public class DisplayVideos extends Application{
   
	public static void index() {
        render();
    }

    public static void uploadVideo(Video picture) {
        picture.save();
        index();
    }
    public static void getVideo(long id) {
        Video picture = Video.findById(id);
        response.setContentTypeIfNotSet(picture.videos.type());
        renderBinary(picture.videos.get());
    }
}

