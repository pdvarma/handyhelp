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

PreparedStatement statement = connection.prepareStatement("SELECT service_id,name FROM services");
	ResultSet result = statement.executeQuery();
	
	JSONArray array=new JSONArray();
	JSONObject main=null;
	 while(result.next()) {
		 
				 object.put("status", "success");
				 JSONObject newobject=new JSONObject();
				newobject.put("service_id", result.getString("service_id"));
				newobject.put("name", result.getString("name"));
                                array.put(newobject);
				 main=new JSONObject();
				 main.put("status","success");
				 main.put("services",array);
				 	
			
		}
 
	  
%>

<%=main.toString()%>
