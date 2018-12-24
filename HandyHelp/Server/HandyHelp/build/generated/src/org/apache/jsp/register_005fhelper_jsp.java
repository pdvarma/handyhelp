package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import org.json.JSONObject;
import commons.Database;
import java.util.ArrayList;
import java.sql.Connection;
import commons.Initializer;
import constants.Constants;

public final class register_005fhelper_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

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
	
      out.write('\n');
      out.write('	');
      out.print(object.toString() );
      out.write('\n');
      out.write('	');

	return;
}
PreparedStatement statement = connection.prepareStatement("INSERT INTO helpers (full_name,email,contact,password,service_id) VALUES (?,?,?,?)");
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

      out.write('\n');
      out.print(object.toString() );
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
