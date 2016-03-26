package com.lde.travel.manager.presentation;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;

import com.lde.travel.manager.entities.Post;
import com.lde.travel.manager.entities.PostI18nContent;
import com.lde.travel.manager.service.PostService;

@Named(value="postController") @ViewScoped
public class TravelManagerController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB private PostService postService;
	
	private Post currentPost;
	
	private int id;
	private String postDate;
	private String startDateCountry;
	private String endDateCountry;
	private String language;
	
	private final static Set<String> AVAILABLE_COUNTRIES;
	private final static List<String> AVAILABLE_LANGUAGES;
	private final static DateTimeFormatter POST_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	static {
		// initializing a set of available countries
		AVAILABLE_COUNTRIES = new TreeSet<>();
		
		for (String countryCode : Locale.getISOCountries()) {
		    Locale obj = new Locale("", countryCode);
		    AVAILABLE_COUNTRIES.add(obj.getDisplayCountry());
		}
		
		// initializing a set of available countries
		AVAILABLE_LANGUAGES = new LinkedList<>();
		
		// adding the languages we actually know (better provided by an config file...)
		AVAILABLE_LANGUAGES.add(Locale.ENGLISH.getDisplayLanguage());
		AVAILABLE_LANGUAGES.add(Locale.GERMAN.getDisplayLanguage());
		AVAILABLE_LANGUAGES.add(Locale.CHINESE.getDisplayLanguage());
		AVAILABLE_LANGUAGES.add(Locale.forLanguageTag("es").getDisplayLanguage()); // Spanish
		AVAILABLE_LANGUAGES.add(Locale.forLanguageTag("sv").getDisplayLanguage()); // Swedish
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the postDate
	 */
	public String getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the startDateCountry
	 */
	public String getStartDateCountry() {
		return startDateCountry;
	}

	/**
	 * @param startDateCountry the startDateCountry to set
	 */
	public void setStartDateCountry(String startDateCountry) {
		this.startDateCountry = startDateCountry;
	}

	/**
	 * @return the endDateCountry
	 */
	public String getEndDateCountry() {
		return endDateCountry;
	}

	/**
	 * @param endDateCountry the endDateCountry to set
	 */
	public void setEndDateCountry(String endDateCountry) {
		this.endDateCountry = endDateCountry;
	}

	/**
	 * @param currentPost the currentPost to set
	 */
	public void setCurrentPost(Post currentPost) {
		this.currentPost = currentPost;
	}
	
	/**
	 * @return the currentPost
	 */
	public Post getCurrentPost() {
		return this.currentPost;
	}

	/**
	 * @return the availablecountries
	 */
	public Set<String> getAvailablecountries() {
		return AVAILABLE_COUNTRIES;
	}
	

	/**
	 * @return the availableLanguages
	 */
	public List<String> getAvailablelanguages() {
		return AVAILABLE_LANGUAGES;
	}
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Converts text to DateTime
	 * @param text - has to be in the format "yyyy-mm-dd"
	 * @return
	 */
	private LocalDate convertStringToDate(String text) {
		return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
	}
	
	private LocalDateTime convertStringToDateTime(String text) {
		return LocalDateTime.parse(text, POST_DATE_FORMATTER);
	}
	
	public String savePost() {
		if(postDate == null || postDate.trim().isEmpty()) {
			currentPost.setPostDate(LocalDateTime.now());
		} else {
			currentPost.setPostDate(convertStringToDateTime(postDate));
		}
		currentPost.setStartDateCountry(convertStringToDate(startDateCountry));
		currentPost.setEndDateCountry(convertStringToDate(endDateCountry));
		try {
			postService.createPost(this.currentPost);
			return "index.xhtml";
		} catch(EJBException ex) {
//			if(ex.getCause() instanceof ConstraintViolationException) {
//				String message = "";
//				((ConstraintViolationException)ex.getCause()).getConstraintViolations().forEach(
//						violation -> message.concat(violation.getMessage().concat("\n")) );
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
//						FacesMessage.SEVERITY_ERROR, message, null));
//				return "";
//			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Some error appeared when writing the data.", null));
			return "index.xhtml";
		}
		
	}
	
	public String linkToNewPostView() {
		return "newPost.xhtml";
	}
	
	public List<Post> retrieveAllPosts() {
		return postService.findAllPosts();
	}
	
	public void initNewPostObject() {
		this.currentPost = new Post();
		PostI18nContent postContent = new PostI18nContent();
//		postContent.addPostImage(new PostImage());
		this.currentPost.addPostContent(postContent);
		this.postDate = LocalDateTime.now().format(POST_DATE_FORMATTER);
	}
	
	public void findPostById() {
		
		if (this.id <= 0) {
      final String message = "Bad request. Please use a link from within the system.";
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
      return;
	  }
	
	  Post post = postService.findPostById(id).get(0);
	  if (post == null) {
	      final String message = "Bad request. Unknown contact detail id.";
	      FacesContext.getCurrentInstance().addMessage(null,
	          new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	  }
	  this.language = post.getContentList().get(0).getLanguage();
	  this.startDateCountry = post.getStartDateCountry().toString();
	  this.endDateCountry = post.getEndDateCountry().toString();
	  this.postDate = post.getPostDate().format(POST_DATE_FORMATTER);
	  this.currentPost = post;
	}
	
	public String forwardToEditPost(int id) {
		this.id = id;
		return "editPost.xhtml?faces-redirect=true&includeViewParams=true";
	}
	
	public String forwardToRemovePost(int id) {
		this.id = id;
		return "removePost.xhtml?faces-redirect=true&includeViewParams=true";
	}
	
	public String deletePost() {
		if(currentPost != null) {
			postService.deletePost(currentPost);
		}
		return "index.xhtml";
	}
	
//	public void addPostContent() {
//		if(this.currentPost != null) {
//			postService.addPostContent(this.currentPost, Locale.getDefault(), 
//					this.postContentCaption, "Bolivia", "text text text....blablabla...");
//		}
//	}
	
//	public void updatePostContent() {
//		if(this.lastContent != null) {
//			this.lastContent.setCaption(this.postContent);;
//			postService.updatePostContent(this.lastContent);
//		}
//	}
	
//	public void addPostImage() {
//		if(this.currentPost != null && !this.currentPost.getContentList().isEmpty() && this.postImage != null) {
//			PostI18nContent postContent = this.currentPost.getContentList().get(0);
//			PostI18nContent updated = postService.addPostImage(postContent, "lalala".getBytes(), "My nice picture", "in this picture you can see blablabla...");
//			this.currentPost.getContentList().set(0,updated);
//		}
//	}
	
//	public void deletePostContent() {
//		if(this.lastContent != null) {
//			postService.removePostContent(this.lastContent);
//			this.lastContent = null;
//		}
//		
//	}


	
	
}
