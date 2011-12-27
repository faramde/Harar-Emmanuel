package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;
import play.libs.*;
import play.cache.*;
import service.Aggregator;
 
import java.io.IOException;
import java.util.*;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;

import models.*;
 
public class Application extends Controller {
	
    @Before
    static void addUser() {
        User user = connected();
        if(user != null) {
            renderArgs.put("user", user);
        }
    }
        
    @Before
    static void addDefaults() {
        renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
        renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
    }
    
    static User connected() {
        if(renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        String username = session.get("user");
        if(username != null) {
            return User.find("byEmail", username).first();
        } 
        return null;
    }
    
    public static void index() throws IllegalArgumentException, FeedException, IOException {
        Post frontPost = Post.find("order by postedAt desc").first();
        List<Post> olderPosts = Post.find("order by postedAt desc").from(1).fetch(10);
		Aggregator aggregator = new Aggregator();
		List<SyndEntry> entries = aggregator.parse("www.believer.com/outreach/versetodayNIV.xml", 1);
        render(frontPost, olderPosts, entries);
    }
    
    public static void register() {
        render();
    }
    
    public static void saveUser(@Valid User user, String verifyPassword) throws IllegalArgumentException, FeedException, IOException {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        user.create();
        session.put("user", user.fullname);
        session.put("user", user.email);
        flash.success("Welcome, " + user.fullname);
        index();
    }
    
    public static void settings() {
        render();
    }
    public static void blogIndex() {
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
    
    public static void show(Long id) {
        Post post = Post.findById(id);
        String randomID = Codec.UUID();
        render(post, randomID);
    }
    
    public static void postComment(
        Long postId, 
        @Required(message="Author is required") String author, 
        @Required(message="A message is required") String content, 
        @Required(message="Please type the code") String code, 
        String randomID) 
    {
        Post post = Post.findById(postId);
        if(!Play.id.equals("test")) {
            validation.equals(code, Cache.get(randomID)).message("Invalid code. Please type it again");
        }
        if(validation.hasErrors()) {
            render("Application/show.html", post, randomID);
        }
        post.addComment(author, content);
        flash.success("Thanks for posting %s", author);
        Cache.delete(randomID);
        show(postId);
    }
    
    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#E4EAFD");
        Cache.set(id, code, "30mn");
        renderBinary(captcha);
    }
    
    public static void listTagged(String tag) {
        List<Post> posts = Post.findTaggedWith(tag);
        render(tag, posts);
    }
    
	public static void login(String username, String password) throws IllegalArgumentException, FeedException, IOException {
		User user = User.find("byEmailAndPassword", username, password).first();
		if(user != null) {
			session.put("user", user.fullname);
			flash.success("Welcome, " + user.fullname);
			Main.index();
		}
		// Oops
		flash.put("username", username);
		flash.error("Login failed");
		index();
	}
	
	public static void logout() throws IllegalArgumentException, FeedException, IOException {
		session.clear();
		index();
	}
 
}
