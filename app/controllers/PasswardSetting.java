package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.io.IOException;
import java.util.*;

import com.sun.syndication.io.FeedException;

import models.*;

public class PasswardSetting extends Application {
    
    
    // ~~~
    
    public static void settings() {
        render();
    }
    
    public static void index() {
        render();
    }
    public static void saveSettings(String password, String verifyPassword) throws IllegalArgumentException, FeedException, IOException {
        User connected = connected();
        connected.password = password;
        validation.valid(connected);
        validation.required(verifyPassword);
        validation.equals(verifyPassword, password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@settings", connected, verifyPassword);
        }
        connected.save();
        flash.success("Password updated");
        index();
    }
    
}

