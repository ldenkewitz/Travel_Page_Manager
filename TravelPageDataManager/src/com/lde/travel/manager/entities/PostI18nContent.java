package com.lde.travel.manager.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="POST_I18N_CONTENT")
public class PostI18nContent {
//TODO: Bean-Validation isn't executed for this entity. Whats's the problem? -> manual call via validator?
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="CONTENT_ID", nullable = false, insertable = true, updatable = true)
  private int id;
  
  @ManyToOne(optional=false) 
  @NotNull
  private Post parent;
  
  @Column(nullable = false, length=90) 
  @NotNull @Size(min = 1, max = 90, message = "{postI18cContent.language.size}")
  private String language;
  
  @Column(nullable = false, length=120, unique=true) 
  @NotNull @Size(min = 1, max = 120, message = "{postI18cContent.caption.size}")
  private String caption;
	
  @Column(nullable = false, length=90) 
  @NotNull @Size(min = 1, max = 90, message = "{postI18cContent.country.size}")
  private String country;
	
	@Column(columnDefinition = "TEXT NOT NULL") // MySQL-text-data-type
	@NotNull
	private String content;

	@OneToMany(mappedBy = "parent", orphanRemoval=true, cascade=CascadeType.ALL)
	private List<PostImage> postImages = new ArrayList<>();

	public PostI18nContent() {	}

	public PostI18nContent(String language, String caption, String country, String content) {
		this.language = language;
		this.caption = caption;
		this.country = country;
		this.content = content;
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
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return the parent
	 */
	public Post getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Post parent) {
		this.parent = parent;
	}
	
	/**
	 * @return the postImages
	 */
	public List<PostImage> getPostImages() {
		return postImages;
	}

	public void addPostImage(PostImage image) {
		if(this.postImages != null && image != null) {
			image.setParent(this);
			this.postImages.add(image);
		}
	}
	
	public void removePostImage(PostImage image) {
		if(this.postImages != null && this.postImages.contains(image)) {
			this.postImages.remove(image);
		}
	}
}
