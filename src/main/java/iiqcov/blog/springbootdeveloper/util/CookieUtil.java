package iiqcov.blog.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Base64;

public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie=new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name){
        Cookie[] cookies=request.getCookies();
        if (cookies==null){
            return;
        }

        for (Cookie cookie: cookies){
            if (name.equals(cookie.getName())){
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    public static String serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
        return Base64.getUrlEncoder().encodeToString(bos.toByteArray());
    }

    public static <T> T deserialize(String s, Class<T> cls) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getUrlDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return cls.cast(o);
    }
}
