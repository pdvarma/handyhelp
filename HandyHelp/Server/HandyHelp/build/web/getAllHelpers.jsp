<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="org.json.JSONObject"%>
<%@page import="commons.Database"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page import="commons.Initializer"%>
<%@page import="constants.Constants"%>
<%
JSONObject object = new JSONObject();
Class.forName(Constants.className);
Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);

PreparedStatement statement = connection.prepareStatement("SELECT helper_id,full_name,email,contact,latitude,longitude,service_id,rating FROM helpers");
	ResultSet result = statement.executeQuery();
	
	JSONArray array=new JSONArray();
	JSONObject main=null;
	 while(result.next()) {
		 
             PreparedStatement statement1 = connection.prepareStatement("SELECT service_id,name FROM services WHERE service_id='"+result.getString("service_id")+"'");
	ResultSet result1 = statement1.executeQuery();

	 while(result1.next()) {
		 
             
				 object.put("status", "success");
				 JSONObject newobject=new JSONObject();
				newobject.put("helper_id", result.getString("helper_id"));
				newobject.put("fullname", result.getString("full_name"));
				newobject.put("email", result.getString("email"));
                                newobject.put("contact", result.getString("contact"));
                                newobject.put("latitude", result.getString("latitude"));
                                newobject.put("longitude", result.getString("longitude"));
                                newobject.put("service_id", result.getString("service_id"));
                                newobject.put("service_name", result1.getString("name"));
                                newobject.put("rating", result.getString("rating"));
                                array.put(newobject);
				 main=new JSONObject();
				 main.put("status","success");
				 main.put("helpers",array);
				 	
         }
		}
 
	  
%>

<%=main.toString()%>
