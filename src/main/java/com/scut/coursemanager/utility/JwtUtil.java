package com.scut.coursemanager.utility;/*

 */


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    // jwt中的secret设置，应该就是密钥
    private String SECRET_KEY = "secret";
    /**
    * @Description:生成token
    * @Param: [subject, claims]
    * @return: java.lang.String
    * @Date: 2020/11/4
    */
    public String generateToken(String subject, Map<String, Object> claims) {
        return createToken(claims, subject);
    }

    /**
    * @Description:拿出所有的claims
    * @Param: [token]
    * @return: io.jsonwebtoken.Claims
    * @Date: 2020/11/5
    */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    /**
     * @Description: 将拿出对应claim功能抽象出来
     * @Param: [token, claimsResolver] claimsResolver claim解析函数
     * @return: T
     * @Date: 2020/11/6
     */
    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /**
    * @Description:拿出subject,这里是username
    * @Param: [token]
    * @return: java.lang.String
    * @Date: 2020/11/5
    */
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
    * @Description:拿出token过期时间
    * @Param: [token]
    * @return: java.util.Date
    * @Date: 2020/11/6
    */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
    * @Description:获取身份
    * @Param: [request]
    * @return: java.lang.String
    * @Date: 2020/11/6
    */
    public String extractIdentitySubject(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if(authHeader !=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }

        Claims claims = extractAllClaims(token);
        return claims.get("identity").toString();
    }
    /**
    * @Description:获取uid
    * @Param: [request]
    * @return: java.lang.String
    * @Date: 2020/11/6
    */
    public String extractUidSubject(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if(authHeader !=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }

        Claims claims = extractAllClaims(token);
        return claims.get("uid").toString();
    }
    /**
    * @Description:检验token是否过期
    * @Param: [token]
    * @return: java.lang.Boolean
    * @Date: 2020/11/6
    */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /**
    * @Description:检验token是否合法
    * @Param: [token, subject]
    * @return: java.lang.Boolean
    * @Date: 2020/11/6
    */
    public Boolean validateToken(String token, String subject) {
        final String username = extractSubject(token);
        return (username.equals(subject) && !isTokenExpired(token));
    }

    /**
    * @Description:创建token
    * @Param: [claims, subject]
    * @return: java.lang.String
    * @Date: 2020/11/4
    */
    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+30*24*60*60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)//设置签名使用的签名算法和签名使用的秘钥
                .compact();
    }
}
