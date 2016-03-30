package com.lde.travel.manager.presentation.validation;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.lde.travel.manager.entities.Post;
import com.lde.travel.manager.entities.PostI18nContent;
import com.lde.travel.manager.service.PostService;

//@FacesValidator("captionValidator") //-> as @FacesValidator it's not container managed (till JSF 2.3) -> no EJB injection
@Named(value="captionValidator")
@RequestScoped
public class FacesUniquePostCaptionValidator implements Validator {

	@EJB private PostService postService;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		// expecting the hiddenField which has two attibutes: one Integer value and one UIInput 
		int currentId = component.getAttributes().get("currentPostId") == null ? 0 : (Integer)component.getAttributes().get("currentPostId");
		UIInput captionComp = (UIInput) component.getAttributes().get("postCaption");
		String thisCaption = "";
		if( captionComp != null ) {
			thisCaption = (String) captionComp.getSubmittedValue();
		}
		
		List<Post> posts = postService.findAllPosts();
		boolean valid = true;
		for(Post post : posts) {
			for(PostI18nContent content : post.getContentList()) {
				if(currentId == 0 || !(currentId == content.getId()) ) {
					valid = valid & !thisCaption.trim().equals(content.getCaption().trim());
				}
			}
		}
		if ( !valid ) {
      throw new ValidatorException(
        new FacesMessage(
          FacesMessage.SEVERITY_ERROR,        
          "This title already exists in another post.", null));
    }
	}

}
