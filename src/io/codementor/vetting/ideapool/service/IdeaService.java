package io.codementor.vetting.ideapool.service;

import java.util.List;
import io.codementor.vetting.ideapool.model.Idea;
import io.codementor.vetting.ideapool.model.User;

public interface IdeaService {
	
	public Idea createIdea(String content,int impact,int ease,int confidence,User u);
	public Idea updateIdea(String id,String content,int impact,int ease,int confidence,User u);
	public void deletIdea(String id);
	public List<Idea> getIdeas(int page,User u);
	public Idea getIdea(String id);
}
