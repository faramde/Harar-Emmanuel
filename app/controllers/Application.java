package controllers;

import java.io.IOException;
import java.util.List;

import models.Post;
import models.User;
import play.Play;
import play.cache.Cache;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.libs.Codec;
import play.libs.Images;
import play.mvc.Before;
import play.mvc.Controller;

import com.sun.syndication.io.FeedException;
 
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
        String email = session.get("user");
        if(email != null) {
            return User.find("byEmail", email).first();
        } 
        return null;
    }
    
    public static void blogIndex() {
        Post frontPost = Post.find("order by postedAt desc").first();
        List<Post> olderPosts = Post.find("order by postedAt desc").from(1).fetch(10);
        render(frontPost, olderPosts);
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
    

    public static void index() throws IllegalArgumentException, FeedException, IOException  {
        render();
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
