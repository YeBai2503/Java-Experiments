package com.ssoserver.tool;
import com.ssoserver.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//id不可超过20000000(2*10^8)
//切记使用该类需在Tomcat中配置好jjwt和jackson的jar包，否则会报错，记住是两个！！
public class JwtBuilder {
    public static String buildJwt(int id) {
        String token = Jwts.builder()
                .setSubject(Encryptor.encrypt(id))
                .signWith(SignatureAlgorithm.HS256, User.SECRET_KEY)
                .compact();

        return token;
    }
    public static String parseJwt(String jwt) {
        String token = Jwts.parser()
                .setSigningKey(User.SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        return Integer.toString(Encryptor.decrypt(token));
    }

    public static void main(String[] args) {
        System.out.println(buildJwt(21145578));
        System.out.println(parseJwt(buildJwt(21145578)));
    }
}
