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
String service_id = request.getParameter("service_id");

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
PreparedStatement statement = connection.prepareStatement("INSERT INTO helpers (full_name,email,contact,password,service_id) VALUES (?,?,?,?,?)");
statement.setString(1, fullname);
statement.setString(2, email);
statement.setString(3, contact);
statement.setString(4, password);
statement.setString(5, service_id);
int status = statement.executeUpdate();
if(status > 0) {
	statement = connection.prepareStatement("SELECT helper_id FROM helpers WHERE email=? AND password=?");
	statement.setString(1, email);
	statement.setString(2, password);
	ResultSet result = statement.executeQuery();
	
	if(result.next()) {
		object.put("status", "success");
		object.put("helper_id", result.getString("helper_id"));
	} else {
		object.put("status", "Failed to register. Try again later.");
	}
} else {
	object.put("status", "Failed to register. Try again later.");
}
%>
<%=object.toString() %>