package com.lde.travel.manager.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lde.util.validation.Past;

@Entity
@Table(name="POST")
@NamedQueries({
	@NamedQuery(name="post.findAll", query = "select p from Post p"),// order by c.lastName, c.middleName, c.firstName"),
	@NamedQuery(name="post.findById", query = "select p from Post p where p.id = :id"),
})
public class Post {
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="POST_ID", nullable = false, insertable = true, updatable = true)
	private int id;
	
	@Column(nullable = false, length=45) 
	@NotNull @Size(min = 1, max = 45, message = "{post.author.size}")
	private String author;
	
	@Column(columnDefinition = "DATETIME NOT NULL") @NotNull
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime postDate; //datetime 2015-12-29 12:00:00
	//LocalDate real_date; //date 2014-01-05
	
	@Column(columnDefinition = "DATE NOT NULL") 
	@NotNull @Past(message="{post.startDateCountry.past}")
//	@Temporal(TemporalType.DATE)
	private LocalDate startDateCountry;
	
	@Column(columnDefinition = "DATE NOT NULL") @NotNull
//	@Temporal(TemporalType.DATE)
	private LocalDate endDateCountry;
	
	@Size(max = 240, message = "{post.flickrAddress.size}")
	private String flickrAddress;
	
	// can have several content due to different translations/Locale
	@OneToMany(mappedBy = "parent",cascade=CascadeType.ALL, orphanRemoval=true) 
	@NotNull @Valid()
	private List<PostI18nContent> contentList = new ArrayList<>();
	
	public Post() {	}
	
	public Post(String author, LocalDateTime postDate, LocalDate startDateCountry, LocalDate endDateCountry, String flickr) {
		this.author = author;
		this.postDate = postDate;
		this.startDateCountry = startDateCountry;
		this.endDateCountry = endDateCountry;
		this.flickrAddress = flickr;
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
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the postDate
	 */
	public LocalDateTime getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the startDateCountry
	 */
	public LocalDate getStartDateCountry() {
		return startDateCountry;
	}

	/**
	 * @param startDateCountry the startDateCountry to set
	 */
	public void setStartDateCountry(LocalDate startDateCountry) {
		this.startDateCountry = startDateCountry;
	}

	/**
	 * @return the endDateCountry
	 */
	public LocalDate getEndDateCountry() {
		return endDateCountry;
	}

	/**
	 * @param endDateCountry the endDateCountry to set
	 */
	public void setEndDateCountry(LocalDate endDateCountry) {
		this.endDateCountry = endDateCountry;
	}

	/**
	 * @return the flickrAddress
	 */
	public String getFlickrAddress() {
		return flickrAddress;
	}

	/**
	 * @param flickrAddress the flickrAddress to set
	 */
	public void setFlickrAddress(String flickrAddress) {
		this.flickrAddress = flickrAddress;
	}
	
	/**
	 * @return the contentList
	 */
	public List<PostI18nContent> getContentList() {
		return contentList;
	}

	public void addPostContent(PostI18nContent postContent) {
		if(this.contentList != null && postContent != null) {
			postContent.setParent(this);
			contentList.add(postContent);
		}
	}
	
	public void removePostContent(PostI18nContent content) {
		if(this.contentList != null && content != null) {
			this.contentList.remove(content);
		}
	}
	
}
