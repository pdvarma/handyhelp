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
String user_id = request.getParameter("user_id");
PreparedStatement statement = connection.prepareStatement("SELECT user_id,full_name,email,contact,latitude,longitude FROM users WHERE user_id='"+user_id+"'");
	ResultSet result = statement.executeQuery();
	
	JSONArray array=new JSONArray();
	JSONObject main=null;
	 while(result.next()) {
		 
                        object.put("user_id", result.getString("user_id"));
			object.put("full_name", result.getString("full_name"));
			object.put("contact", result.getString("contact"));
			object.put("email", result.getString("email"));
			object.put("status", "success");
				 	
			
		}
 
	  
%>

<%=object.toString()%>
