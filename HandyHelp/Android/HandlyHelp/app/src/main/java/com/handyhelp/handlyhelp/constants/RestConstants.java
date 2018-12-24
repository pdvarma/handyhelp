package com.handyhelp.handlyhelp.constants;

public interface RestConstants {

    String tag = "HandyHelp";
    String SITE = "http://192.168.0.5:8080/HandyHelp/";
    String SITE1="http://";
    String SITE2=":8080/HandyHelp/";
    String FULLNAME = "fullname";
    String PASSWORD = "password";
    String EMAIL="email";
    String CONTACT="contact";
    String STATUS = "status";
    String SUCCESS = "success";
    String USER_ID="user_id";
    String HELPER_ID="helper_id";
    String SERVICE_ID="service_id";
    String RATING = "rating";
    String LATITUDE="latitude";
    String LONGITUDE="longitude";



//    URL for Services

    String REGISTER_USER="register_user.jsp";
    String REGISTER_HELPER="register_helper.jsp";
    String LOGIN_USER="login_user.jsp";
    String LOGIN_HELPER="login_helper.jsp";
    String GET_HELPERS = "getAllHelpers.jsp";
    String GET_SERVICES = "getAllServices.jsp";
    String GET_HELPER_WITH_SERVICE_ID = "getHelpersWithService.jsp";
    String BOOKED_APPOINTMENT_USERS = "userBookedAppListing.jsp";
    String BOOKED_APPOINTMENT_HELPERS = "helperBookedAppListing.jsp";
    String UPDATE_LOCATION = "updateLocation.jsp";
    String BOOK_APPOINTMENT = "bookAppointment.jsp";

}
