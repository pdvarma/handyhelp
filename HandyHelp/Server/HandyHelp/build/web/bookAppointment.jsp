<%@page import="java.util.Date"%>
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
String helper_id = request.getParameter("helper_id");
String service_id = request.getParameter("service_id");
String paymentStatus = request.getParameter("payment");


if(user_id == null) {
	user_id = "";
	System.out.print(user_id);
	
}
if(helper_id == null) {
	helper_id = "";
}
if(user_id.length() <= 0 || helper_id.length() <= 0) {
	object.put("status", "Invalid Input");
	%>
	<%=object.toString() %>
	<%
	return;
}
PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments (user_id,helper_id,service_id,date,payment_status) VALUES (?,?,?,?,?)");
statement.setString(1, user_id);
statement.setString(2, helper_id);
statement.setString(3, service_id);
statement.setString(4, new Date().toString());
statement.setString(5, paymentStatus);
int status = statement.executeUpdate();
if(status > 0) {
		object.put("status", "Success");
	} else {
	object.put("status", "Failed to register. Try again later.");
}
%>
<%=object.toString() %>