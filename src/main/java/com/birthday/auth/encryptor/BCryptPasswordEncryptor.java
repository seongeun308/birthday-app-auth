package com.birthday.auth.encryptor;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncryptor implements PasswordEncryptor {

    @Override
    public String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
