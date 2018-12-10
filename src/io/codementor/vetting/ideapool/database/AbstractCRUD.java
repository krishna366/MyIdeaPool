package io.codementor.vetting.ideapool.database;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AbstractCRUD {
    private static final String DB_URL = "jdbc:mysql://159.89.148.225:3306/ideapool";
    private static final String USER = "root";
    private static final String PASS = "31156ac043670e3568e4d49353c6f68e73ea242deaf4a896";
    private static final Sql2o sql2o = new Sql2o(DB_URL, USER, PASS);
    
    static {
    	Map<String, String> colMaps = new HashMap<String,String>();
    	colMaps.put("refresh_token", "refreshToken");
    	colMaps.put("avatar_url", "avatarUrl");
    	colMaps.put("created_by", "createdBy");
    	colMaps.put("created_at", "createdAt");
    	colMaps.put("created_by", "createdBy");
    	colMaps.put("last_updated_by", "lastUpdatedBy");
    	colMaps.put("last_modified_at", "lastUpdatedAt");
    	
    	sql2o.setDefaultColumnMappings(colMaps);
    }
    
    protected static Connection getOpenedConnection(){
        return sql2o.open();
    }

    protected static Date parseDate(String dateToBeParsed){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(dateToBeParsed);
        } catch (ParseException e) {
            return null;
        }
    }
}
