package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Member extends Model{
	public String name;
	public String address;
	public String kebele;
	public Date dateOfBirth;
	public String poBox;
	public String country;
	@Email
	public String email;	
	public String phone;
	public String fax;

	public Member(String name, String address, String kebele, Date dateOfBirth,
			String poBox, String country, String email, String phone, String fax) {
		super();
		this.name = name;
		this.address = address;
		this.kebele = kebele;
		this.dateOfBirth = dateOfBirth;
		this.poBox = poBox;
		this.country = country;
		this.email = email;
		this.phone = phone;
		this.fax = fax;
	}

	public String toString() {
		return name;
	}
	
	
}
