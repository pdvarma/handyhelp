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
String id = request.getParameter("helper_id");
String latitude = request.getParameter("latitude");
String longitude = request.getParameter("longitude");
String acc = "ACCEPTED";
if(id == null) {
	id = "";
	System.out.print(id);
	
}
if(id.length() <= 0 ) {
	object.put("status", "Invalid Input");
	%>
	<%=object.toString() %>
	<%
	return;
}
String sql="UPDATE helpers SET latitude=?,longitude=? WHERE helper_id='"+id+"'";
PreparedStatement statement = connection.prepareStatement(sql);
statement.setString(1, latitude);
statement.setString(2, longitude);
int status = statement.executeUpdate();
if(status>0)
{		
		object.put("status", "success");	
	
}
%>
<%=object.toString() %>