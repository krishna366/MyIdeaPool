package io.codementor.vetting.ideapool.database;

import java.util.List;

import org.sql2o.Connection;

import io.codementor.vetting.ideapool.model.Idea;
import io.codementor.vetting.ideapool.model.User;

public class IdeaCRUD extends AbstractCRUD{
	
	private final static String SQL_INSERT = "INSERT INTO `ideapool`.`ideas` (`content`,`impact`,`ease`,`confidence`,`created_by`,`last_updated_by`) " +
            " VALUES(:content,:impact,:ease,:confidence,:createdBy,:lastUpdatedBy)"; 
	
	private final static String SQL_UPDATE = "UPDATE `ideapool`.`ideas` SET `content` = :content,"+
												"impact = :impact,"+
												"ease = :ease,"+
												"confidence = :confidence,"+
												"last_updated_by = :updatedBy,"+
												"last_modified_at=CURRENT_TIMESTAMP()"+
												"WHERE id = :id";
	

	private final static String SQL_DELETE = "DELETE FROM `ideapool`.`ideas` "+
												"WHERE id = :id";
	
	private final static String SQL_SELECT_SINGLE = "SELECT id,content,impact,ease,confidence,created_by,created_at,last_updated_by,last_modified_at,(impact + ease + confidence)/3 average FROM `ideapool`.`ideas` "+
											"WHERE id = :id";
	
	private final static String SQL_SELECT_MULTI = "SELECT id,content,impact,ease,confidence,created_by,created_at,last_updated_by,last_modified_at,(impact + ease + confidence)/3 average FROM `ideapool`.`ideas` "+
													"where created_by = :createdBy "+
													"order by created_at desc "+
													"LIMIT :offset,:count";
	
	
	public static String createIdea(String content,int impact, int ease,int confidence,String createdBy) {
		
		Connection connection = getOpenedConnection();
		
        try{
            
            Object insertedId = connection.createQuery(SQL_INSERT, true)
                    .addParameter("content", content)
                    .addParameter("impact", impact )
                    .addParameter("ease", ease)
                    .addParameter("confidence",confidence)
                    .addParameter("createdBy",createdBy)
                    .addParameter("lastUpdatedBy",createdBy)
                    .executeUpdate()
                    .getKey();
            	
            //connection.commit();
            return insertedId.toString();

        } finally {
            connection.close();
        }
		
	}
	
	public static String updateIdea(String id,String content,int impact, int ease,int confidence,String updatedBy) {
		
		Connection connection = getOpenedConnection();
		
        try{
            
            Object insertedId = connection.createQuery(SQL_UPDATE, true)
                    .addParameter("content", content)
                    .addParameter("impact", impact )
                    .addParameter("ease", ease)
                    .addParameter("confidence",confidence)
                    .addParameter("updatedBy",updatedBy)
                    .addParameter("id", id)
                    .executeUpdate()
                    .getKey();
            	
            //connection.commit();
            //return insertedId.toString();
            return id;

        } finally {
            connection.close();
        }
		
	}
	
	public static void deleteIdea(String id) {
		
		Connection connection = getOpenedConnection();
		
        try{
            
            connection.createQuery(SQL_DELETE, true)
                    .addParameter("id", id)
                    .executeUpdate();
            	
            //connection.commit();
            

        } finally {
            connection.close();
        }
		
	}
	
	public static Idea getIdea(String id) {
		
		Connection connection = getOpenedConnection();
		
		try {
			Idea idea = connection.createQuery(SQL_SELECT_SINGLE)
						.addParameter("id", id)
						.executeAndFetchFirst(Idea.class);
			
			return idea;
		} finally {
			connection.close();
		}
		
	}
	
	public static List<Idea> getIdeas(int page,String createdBy) {
		
		Connection connection = getOpenedConnection();
		
		try {
			
			List<Idea> ideas = connection.createQuery(SQL_SELECT_MULTI)
					.addParameter("createdBy",createdBy)
					.addParameter("offset", (page - 1) * 10)
					.addParameter("count", 10)
					.executeAndFetch(Idea.class);
		
		return ideas;
			
		} finally {
			connection.close();
		}
		
	}
	
	public static void main(String[] args) {
		/*
		String id = IdeaCRUD.createIdea("idea1", 1, 1, 1, "1");
		System.out.println("id1 = "+id);
		
		System.out.println(IdeaCRUD.getIdea(id));
		
		id = IdeaCRUD.updateIdea(id, "idea101", 10, 10, 10, "2");
		
		System.out.println(IdeaCRUD.getIdea(id));
		
		IdeaCRUD.createIdea("idea1", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea2", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea3", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea4", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea5", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea6", 1, 1, 1, "1");
		String id2 = IdeaCRUD.createIdea("idea7", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea8", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea9", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea10", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea11", 1, 1, 1, "1");
		String id3 = IdeaCRUD.createIdea("idea12", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea13", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea14", 1, 1, 1, "1");
		IdeaCRUD.createIdea("idea15", 1, 1, 1, "1");
		
		IdeaCRUD.deleteIdea(id2);
		System.out.println(IdeaCRUD.getIdea(id2));
		
		System.out.println(IdeaCRUD.getIdea(id3));
		
		List<Idea> ideas1 = IdeaCRUD.getIdeas(1);
		List<Idea> ideas2 = IdeaCRUD.getIdeas(2);
		
		for(Idea i:ideas1) System.out.println(i);
		for(Idea i:ideas2) System.out.println(i);
		
		*/
	}
	
}
