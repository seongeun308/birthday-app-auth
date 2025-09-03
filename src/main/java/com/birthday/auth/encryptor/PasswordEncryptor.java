package com.birthday.auth.encryptor;

public interface PasswordEncryptor {
    String encrypt(String rawPassword);

    boolean matches(String rawPassword, String encryptedPassword);
}
