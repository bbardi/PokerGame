package edu.bbardi.pokerbackend;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String POKER = API_PATH + "/poker";
    public static final String ENTITY = "/{id}";
    public static final String USER_ENTITY = "/{userID}";

    public static final String USER = POKER + "/user";

    //stripe paths
    public static final String PAYMENTS = POKER + "/payments";

    //game paths
    public static final String LOBBY = POKER + "/lobby";

    //websocket paths
    public static final String WEBSOCKET = "/pokerws";

    public static final String CHAT = "/chat";
    public static final String SEND_MESSAGE = CHAT + "/send";
    public static final String RECEIVE_MESSAGE = "/receive";


    public static final String GAME = "/game";
    public static final String READY = GAME + "/ready";
    public static final String CHECK = GAME + "/check";
    public static final String CALL = GAME + "/call";
    public static final String FOLD = GAME + "/fold";
    public static final String RAISE = GAME + "/raise";

    //auth paths
    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign_in";
    public static final String SIGN_UP = "/sign_up";
}
