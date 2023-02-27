package com.example.telegrambot.generic;

import com.example.telegrambot.exceptions.ConflictException;

import java.util.regex.Pattern;

public class GenericController {
    private final static String USERNAME_WITH_HTTPS="https://t.me/";
    private final static String USERNAME_WITH_AT_SIGN="@";

    public String cleanParamOrPath(String str){
        Pattern pattern = Pattern.compile("<script[^>]*>[\\s\\S]*?</script>");
        return pattern.matcher(str).replaceAll("");
    }
    public String absoluteUserName(String str) throws ConflictException {
        str = cleanParamOrPath(str);
       if (str.contains(USERNAME_WITH_HTTPS)) return str.substring(13);
       else if(str.contains(USERNAME_WITH_AT_SIGN)) return str.substring(1);
        else
            throw new ConflictException("username_is_wrong");
    }
}
