package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.simple.JSONObject;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import commons.Database;
import java.util.ArrayList;
import java.sql.Connection;
import commons.Initializer;
import constants.Constants;

public final class login_005fuser_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	
      out.write('\n');
      out.write('	');
      out.print(object.toString() );
      out.write('\n');
      out.write('	');

	return;
}
PreparedStatement statement = connection.prepareStatement("SELECT user_id,full_name,password,contact,email FROM users WHERE email='"+email+"' AND password= '"+password+"'");
 	
	ResultSet result = statement.executeQuery();
	 if(result.next()) {
	
			object.put("user_id", result.getString("user_id"));
			object.put("full_name", result.getString("full_name"));
			object.put("contact", result.getString("contact"));
			object.put("email", result.getString("email"));
			object.put("status", "success");
		} 
	 else {
			object.put("status", "Failed to login. Try again later.");
		} 

      out.write('\n');
      out.print(object.toString() );
      out.write('\n');
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
