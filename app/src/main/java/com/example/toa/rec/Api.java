package com.example.toa.rec;

/*ALEX: This class hold the URLs for the API calls. The reason we can't put it in strings is concatenation*/
public class Api {
    private static final String ROOT_URL = "http://192.168.0.12:80/RecAndroidAPI/v1/Api.php?apicall=";
    public static final String URL_CREATE_REVIEW = ROOT_URL + "createreview";
    public static final String URL_READ_EVENTS = ROOT_URL + "getevents";

}