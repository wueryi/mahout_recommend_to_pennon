package pennon.handinhand.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author Administrator
 */
public class JwtUtil {
    private static final long EXP_TIME_LENGTH = 7200 * 1000;
    private static final String JWT_SECRET = "123456";

    private static SecretKey getSecret() {
        byte[] encodedKey = Base64.encodeBase64(JWT_SECRET.getBytes());
        System.out.println(Arrays.toString(encodedKey));
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static String getToken() {
        try {
            // 设置签发算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            // 生成密钥
            SecretKey key = getSecret();
            // 设置私有声明
            Map<String, Object> claims = new HashMap<>(16);
            claims.put("userId", 1);
            claims.put("username", "test_username");
            // 记录生成JWT的时间
            long nowMillis = System.currentTimeMillis();
            Date nowTime = new Date(nowMillis);
            // 设置过期时间
            long expMillis = nowMillis + EXP_TIME_LENGTH;
            Date expTime = new Date(expMillis);
            // 创建token构建器实例
            JwtBuilder jwtBuilder = Jwts.builder()
                    // 设置自己的私有声明
                    .setClaims(claims)
                    // 设置该token的Id，用于防止token重复
                    .setId(UUID.randomUUID().toString())
                    // 设置签发者
                    .setIssuer("TEST-ISS")
                    // 设置签发时间
                    .setIssuedAt(nowTime)
                    // 设置过期时间
                    .setExpiration(expTime)
                    // 设置token的签发对象
                    .setSubject("TEST_SUB")
                    // 设置签发算法和密钥
                    .signWith(signatureAlgorithm, key);
            return jwtBuilder.compact();
        } catch (Exception e) {
            e.printStackTrace();
            return "生成token失败";
        }
    }

    public static Claims parseToken(String token) {
        SecretKey key = getSecret();
        //获取token中的声明部分
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();
    }
}
