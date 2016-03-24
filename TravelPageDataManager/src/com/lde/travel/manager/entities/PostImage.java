package com.lde.travel.manager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="POST_IMAGE")
public class PostImage {
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="IMAGE_ID", nullable = false, insertable = true, updatable = true)
	private int id;
	
  @Column(columnDefinition="mediumblob")
	private byte[] blobImg;
	private String title;
	private String caption;
	
	// TODO: change to ManyToMany relationship -> one post can have several different translations which share their images
	@ManyToOne(optional=false)
	private PostI18nContent parent;

	public PostImage() {	}
	
	public PostImage(byte[] blobImg, String title, String caption) {
		this.blobImg = blobImg;
		this.title = title;
		this.caption = caption;
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
	 * @return the blobImg
	 */
	public byte[] getBlobImg() {
		return blobImg;
	}
	/**
	 * @param blobImg the blobImg to set
	 */
	public void setBlobImg(byte[] blobImg) {
		this.blobImg = blobImg;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the parent
	 */
	public PostI18nContent getParent() {
		return parent;
	}
	/**
	 * @param parent the postContent to set
	 */
	public void setParent(PostI18nContent postContent) {
		this.parent = postContent;
	}

	
}
