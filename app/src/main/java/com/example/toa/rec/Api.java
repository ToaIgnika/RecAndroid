package com.example.toa.rec;

/*ALEX: This class hold the URLs for the API calls. The reason we can't put it in strings is concatenation*/
public class Api {
    private static final String ROOT_URL = "http://phf.jhh.mybluehost.me/RecAndroidAPI/v1/Api.php?apicall=";
    public static final String URL_CREATE_REVIEW = ROOT_URL + "createreview";
    public static final String URL_READ_EVENTS = ROOT_URL + "getevents";
    public static final String URL_READ_ALL_EVENTS = ROOT_URL + "getallevents";

}