package models;

import javax.persistence.Entity;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Video extends Model{
	
	public Blob videos;

	public Video(Blob videos) {
		super();
		this.videos = videos;
	}

	@Override
	public String toString() {
		return "Video [videos=" + videos + "]";
	} 
	
	
}
