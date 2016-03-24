package com.lde.travel.manager.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import com.lde.travel.manager.entities.Post;
import com.lde.travel.manager.entities.PostI18nContent;
import com.lde.travel.manager.entities.PostImage;

@Stateful // has to be stateful for PersistenceContextType.EXTENDED
public class PostService {

	@PersistenceContext(unitName = "travel-pu", type = PersistenceContextType.EXTENDED)
	EntityManager em;
	
	// POST ENTITY ********************************
	
	public void createPost(Post post) {
		em.persist(post);
	}
	
	public void deletePost(Post post) {
//		em.merge(post);
		em.remove(post);
	}
	
	
	// POST CONTENT ENTITY ********************************
	
	public Post addPostContent(Post post, String language, String caption, String country, String content) {
		em.merge(post);
		PostI18nContent postContent = new PostI18nContent(language, caption, country, content);
		post.addPostContent(postContent);
		return post;
	}
	
	public void removePostContent(PostI18nContent postContent) {
//		em.merge(postContent);
		em.remove(postContent);
	}
	
	public void updatePostContent(PostI18nContent postContent) {
		em.merge(postContent);
	}
	
	
	// POST IMAGE ENTITY ********************************
	
	public PostI18nContent addPostImage(PostI18nContent postContent, byte[] blobImg, String title, String caption) {
		em.merge(postContent);
		PostImage image = new PostImage(blobImg, title, caption);
		postContent.addPostImage(image);
		return postContent;
		
	}

	public List<Post> findAllPosts() {
		TypedQuery<Post> query = em.createNamedQuery("post.findAll", Post.class);
		return query.getResultList();
	}
	
	public List<Post> findPostById(int id) {
		TypedQuery<Post> query = em.createNamedQuery("post.findById", Post.class).setParameter("id", id);
    return query.getResultList();
	}
}
