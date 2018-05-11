package com.example.toa.rec;

/*ALEX: This class hold the URLs for the API calls. The reason we can't put it in strings is concatenation*/
public class Api {
    private static final String ROOT_URL = "http://10.188.11.98/RecAndroidAPI/v1/Api.php?apicall=";
    public static final String URL_CREATE_REVIEW = ROOT_URL + "createreview";
    public static final String URL_READ_EVENTS = ROOT_URL + "getevents";
    public static final String URL_READ_ALL_EVENTS = ROOT_URL + "getallevents";
    public static final String URL_GET_USER = ROOT_URL + "getuser";
    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

}