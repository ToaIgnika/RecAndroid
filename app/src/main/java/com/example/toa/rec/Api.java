package com.example.toa.rec;

/**
 * This class hold the URLs for the API calls. You will need to change ROOT_URL depending on where this is hosted.
 **/
public class Api {
    //private static final String ROOT_URL = "http://phf.jhh.mybluehost.me/RecAndroidAPI/v1/Api.php?apicall=";
    private static final String ROOT_URL = "http://192.168.0.12/RecAndroidAPI/v1/Api.php?apicall=";
    //private static final String ROOT_URL = "http://192.168.1.64/RecAndroidAPI/v1/Api.php?apicall=";
    public static final String URL_CREATE_REVIEW = ROOT_URL + "createreview";
    public static final String URL_READ_EVENTS = ROOT_URL + "getevents";
    public static final String URL_READ_ALL_EVENTS = ROOT_URL + "getallevents";
    public static final String URL_GET_USER = ROOT_URL + "getuser";
    public static final String URL_GET_USER_EVENTS = ROOT_URL + "getuserevents";
    public static final String URL_MAIL_USER_EVENTS = ROOT_URL + "mailuserevents";
    public static final String URL_RESET_PIN = ROOT_URL + "resetpin";
    public static final String URL_REMOVE_EVENT = ROOT_URL + "removeevent";
    public static final String URL_GET_CLASSES = ROOT_URL + "getclasses";
    public static final String URL_GET_INSTRUCTORS = ROOT_URL + "getinstructors";
    public static final String URL_REGISTER_CLASS = ROOT_URL + "regclass";
    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;
}