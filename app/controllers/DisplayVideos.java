package controllers;


import models.Video;
import play.*;
import play.mvc.*;

public class DisplayVideos extends Application{

   
	public static void index() {
		String videosrc = "http://www.youtube.com/embed/oMYXYRCRSqI?rel=0";
        render(videosrc);
    }


}

