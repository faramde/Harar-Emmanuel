package models;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;


import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Picture extends Model {

	public Blob image; 
}
