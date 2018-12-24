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
String fullname = request.getParameter("fullname");
String email = request.getParameter("email");
String contact=request.getParameter("contact");
String password=request.getParameter("password");
if(email == null) {
	email = "";
	System.out.print(email);
	
}
if(password == null) {
	password = "";
}
if(email.length() <= 0 || password.length() <= 0) {
	object.put("status", "Invalid Input");
	%>
	<%=object.toString() %>
	<%
	return;
}
PreparedStatement statement = connection.prepareStatement("INSERT INTO users (full_name,email,contact,password) VALUES (?,?,?,?)");
statement.setString(1, fullname);
statement.setString(2, email);
statement.setString(3, contact);
statement.setString(4, password);
int status = statement.executeUpdate();
if(status > 0) {
	statement = connection.prepareStatement("SELECT user_id FROM users WHERE email=? AND password=?");
	statement.setString(1, email);
	statement.setString(2, password);
	ResultSet result = statement.executeQuery();
	
	if(result.next()) {
		object.put("status", "success");
		object.put("user_id", result.getString("user_id"));
	} else {
		object.put("status", "Failed to register. Try again later.");
	}
} else {
	object.put("status", "Failed to register. Try again later.");
}
%>
<%=object.toString() %>