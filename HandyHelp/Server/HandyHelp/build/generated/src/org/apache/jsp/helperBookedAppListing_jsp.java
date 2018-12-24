package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONArray;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import org.json.JSONObject;
import commons.Database;
import java.util.ArrayList;
import java.sql.Connection;
import commons.Initializer;
import constants.Constants;

public final class helperBookedAppListing_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");

JSONObject object = new JSONObject();
Class.forName(Constants.className);
Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
String helper_id = request.getParameter("helper_id");
PreparedStatement statement = connection.prepareStatement("SELECT user_id,helper_id,service_id,date,payment_status FROM appointments WHERE helper_id='"+helper_id+"'");
	ResultSet result = statement.executeQuery();
	
	JSONArray array=new JSONArray();
	JSONObject main=null;
        while(result.next()) {
		 
            object.put("status", "success");
            JSONObject newobject=new JSONObject();
            newobject.put("helper_id", result.getString("helper_id"));
            newobject.put("user_id", result.getString("user_id"));
            newobject.put("service_id", result.getString("service_id"));
            newobject.put("date", result.getString("date"));
            newobject.put("payment_status", result.getString("payment_status"));
            
//            array.put(newobject);
//            main=new JSONObject();
//            main.put("status","success");
//            main.put("appointments",array);	
        PreparedStatement statement1 = connection.prepareStatement("SELECT user_id,full_name,email,contact FROM users WHERE user_id='"+result.getString("user_id")+"'");
	ResultSet result1 = statement1.executeQuery();

        while(result1.next()) {
		 
//            object.put("status", "success");
            JSONObject newobject1=new JSONObject();
            newobject1.put("user_id", result1.getString("user_id"));
            newobject1.put("full_name", result1.getString("full_name"));
            newobject1.put("contact", result1.getString("contact"));
            newobject1.put("email", result1.getString("email"));
            newobject1.put("status", "success");
            
//            array.put(newobject1);
//            main=new JSONObject();
//            main.put("status","success");
//            main.put("appointments",array);	
             
        PreparedStatement statement2 = connection.prepareStatement("SELECT helper_id,full_name,email,contact,rating FROM helpers WHERE helper_id='"+result.getString("helper_id")+"'");
	ResultSet result2 = statement2.executeQuery();
	
	
        while(result2.next()) {
		 
//            object.put("status", "success");
            JSONObject newobject2=new JSONObject();
            newobject2.put("user_id", result1.getString("user_id"));
            newobject2.put("full_name", result1.getString("full_name"));
            newobject2.put("contact", result1.getString("contact"));
            newobject2.put("email", result1.getString("email"));
            newobject2.put("helper_id", result2.getString("helper_id"));
            newobject2.put("full_name_helper", result2.getString("full_name"));
            newobject2.put("contact_helper", result2.getString("contact"));
            newobject2.put("email_helper", result2.getString("email"));
            newobject2.put("status", "success");
            
//            array.put(newobject2);
//            main=new JSONObject();
//            main.put("status","success");
//            main.put("appointments",array);	
            
        PreparedStatement statement3 = connection.prepareStatement("SELECT service_id,name FROM services WHERE service_id='"+result.getString("service_id")+"'");
	ResultSet result3 = statement3.executeQuery();
	
	
            while(result3.next()) {

    //            object.put("status", "success");
                JSONObject newobject3=new JSONObject();
                newobject3.put("user_id", result1.getString("user_id"));
                newobject3.put("fullname", result1.getString("full_name"));
                newobject3.put("contact", result1.getString("contact"));
                newobject3.put("email", result1.getString("email"));
                newobject3.put("helper_id", result2.getString("helper_id"));
                newobject3.put("rating", result2.getString("rating"));
                newobject3.put("full_name_helper", result2.getString("full_name"));
                newobject3.put("contact_helper", result2.getString("contact"));
                newobject3.put("email_helper", result2.getString("email"));
                newobject3.put("service_name", result3.getString("name"));
                newobject3.put("service_id", result3.getString("service_id"));
                newobject3.put("status", "success");

                array.put(newobject3);
                main=new JSONObject();
                main.put("status","success");
                main.put("appointments",array);	
                }
            }
        }
    }
 
	  

      out.write('\n');
      out.write('\n');
      out.print(main.toString());
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
