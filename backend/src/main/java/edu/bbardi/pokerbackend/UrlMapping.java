package edu.bbardi.pokerbackend;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String POKER = API_PATH + "/poker";
    public static final String ENTITY = "/{id}";

    public static final String USER = POKER + "/user";

    //auth paths
    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign_in";
    public static final String SIGN_UP = "/sign_up";
}
