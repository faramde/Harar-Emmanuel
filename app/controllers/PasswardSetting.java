package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.io.IOException;
import java.util.*;

import com.sun.syndication.io.FeedException;

import models.*;

public class PasswardSetting extends Application {
   
	public static void settings() {
        render();
    }
    
    public static void index() {
        render();
    }
    
    public static void saveSettings(String password, String verifyPassword) throws IllegalArgumentException, FeedException, IOException {
        String fullname = session.get("user");
        User user = User.find("byFullname", fullname).first();
        user.password = password;
        validation.valid(user);
        validation.required(password);
        validation.required(verifyPassword);
        validation.equals(verifyPassword, password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@settings", user, verifyPassword);
        }
        user.save();
        flash.success("Password updated");
        index();
    }
}

