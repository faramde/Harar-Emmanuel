package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Video extends Model{
	public String name;
	public String videosrc;


	public Video(String videosrc, String name) {
		super();
		this.videosrc = videosrc;
		this.name = name;
	}


	@Override
	public String toString() {
		return "Video [videosrc=" + videosrc + "]";
	}

	
}
