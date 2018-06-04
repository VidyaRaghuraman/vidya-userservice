package com.data;


public class Resources {
    public static String AuthRes(){
        String authres="/auth/authenticate";
        return authres;

    }

    public static String AuthCheckRes(){
        String authcheckres="/auth/check";
        return authcheckres;

    }

    public static String GetProfileRes(){
        String getprofileres="/profiles/";
        return getprofileres;

    }

    public static String GetUserMainRes(){
        String getusermainres="/users/me/profiles/main";
        return getusermainres;

    }

    public static String GetUserMeRes(){
        String getusermeres="/users/me";
        return getusermeres;

    }

    public static String PutProfileRes(){
        String putprofileres="/profiles/";
        return putprofileres;

    }

    public static String PutUsersMeRes(){
        String putusersmeres="/profiles/";
        return putusersmeres;

    }
}
