package ip.gr1.TechnologyRelatedBlogs.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


import ip.gr1.TechnologyRelatedBlogs.Exception.SpringBlogException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
    private KeyStore keyStore;
    @PostConstruct
    public  void init(){
        try {
            keyStore = keyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/trb.jks");
            keyStore.load(resourceAsStream, "password".toCharArray());
        }catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw  new SpringBlogException("Exeption occured while loading Keystore");
        }
    }
    public  String generateToken(Authentication authentication)
    {
        
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();

    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("trb", "password".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }
    }

    public  boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt) ;
    return true;
    }

    private PublicKey getPublicKey() {
        try{
        return  keyStore.getCertificate("trb").getPublicKey();
        }catch (KeyStoreException e) {
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                        .setSigningKey(getPublicKey())
                        .parseClaimsJws(token)
                        .getBody();
        return claims.getSubject();
    }
}
