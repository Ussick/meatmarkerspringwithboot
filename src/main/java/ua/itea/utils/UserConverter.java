package ua.itea.utils;

import org.springframework.stereotype.Component;
import ua.itea.dto.UserDto;
import ua.itea.model.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserConverter {

    public UserDto convertUser(User user) {
        UserDto result = new UserDto();
        result.setLogin(user.getLogin());
        result.setName(user.getName());
        result.setPassword(getSaltedHashedPassword(user.getPassword()));
        result.setRegion(user.getRegion());
        result.setGender(user.getGender());
        result.setComment(user.getComment());
        return result;
    }
    private String getSaltedHashedPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String passphrase = "myspecialspice" + password;
        md.update(passphrase.getBytes(StandardCharsets.UTF_8));
        return String.format("%064x", new BigInteger(1, md.digest()));
    }
}
