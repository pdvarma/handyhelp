<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="commons.Database"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page import="commons.Initializer"%>
<%@page import="constants.Constants"%>
<%
JSONObject object = new JSONObject();
Class.forName(Constants.className);
Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);

String email = request.getParameter("email");
String password = request.getParameter("password");

if(email == null) {
	object.put("status", "error");
    out.println(object.toString());
    return;
}
if(password == null) {
	object.put("status", "error");
    out.println(object.toString());
    return;
}
System.out.print(email+password);
if(email.length() <= 0 || password.length() <= 0) {
	object.put("status", "Invalid Input");
	%>
	<%=object.toString() %>
	<%
	return;
}
PreparedStatement statement = connection.prepareStatement("SELECT helper_id,full_name,password,contact,email FROM helpers WHERE email='"+email+"' AND password= '"+password+"'");
 	
	ResultSet result = statement.executeQuery();
	 if(result.next()) {
	
			object.put("helper_id", result.getString("helper_id"));
			object.put("full_name", result.getString("full_name"));
			object.put("contact", result.getString("contact"));
			object.put("email", result.getString("email"));
			object.put("status", "success");
		} 
	 else {
			object.put("status", "Failed to login. Try again later.");
		} 
%>
<%=object.toString() %>
