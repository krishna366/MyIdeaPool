package io.codementor.vetting.ideapool.service;

import java.util.List;

import io.codementor.vetting.ideapool.database.IdeaCRUD;
import io.codementor.vetting.ideapool.model.Idea;
import io.codementor.vetting.ideapool.model.User;

public class IdeaServiceImpl implements IdeaService{
	
	public Idea createIdea(String content,int impact,int ease,int confidence,User u) {
		
		String id = IdeaCRUD.createIdea(content, impact, ease, confidence, u.getId());
		
		return id == null ? null : IdeaCRUD.getIdea(id);
	}
	public Idea updateIdea(String id,String content,int impact,int ease,int confidence,User u) {
		
		String ideaId = IdeaCRUD.updateIdea(id,content, impact, ease, confidence, u.getId());
		
		return ideaId == null ? null : IdeaCRUD.getIdea(ideaId);
	}
	
	public void deletIdea(String id) {
		IdeaCRUD.deleteIdea(id);
	}
	public List<Idea> getIdeas(int page){
		
		return IdeaCRUD.getIdeas(page);
	}
	
}
