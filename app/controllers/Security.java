package controllers;

import java.io.IOException;

import com.sun.syndication.io.FeedException;

import models.*;

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {
        return User.connect(username, password) != null;
    }
    
    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return User.find("byEmail", connected()).<User>first().isAdmin;
        }
        return false;
    }
    
    static void onDisconnected() {
        try {
			Application.index();
		} catch (IllegalArgumentException | FeedException | IOException e) {
			e.printStackTrace();
		}
    }
    
    static void onAuthenticated() {
        Admin.index();
    }
    
}

