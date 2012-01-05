package controllers;


import play.*;
import play.mvc.*;

public class UploadVideo extends Application{
   
	public static void index(String urlname) {
		//String videosrc = "http://www.youtube.com/embed/oMYXYRCRSqI?rel=0";
		String videosrc = urlname;
        render(videosrc);
    }
    
}

