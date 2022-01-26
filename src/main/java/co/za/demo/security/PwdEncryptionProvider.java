package co.za.demo.security;


import co.za.demo.model.base.User;
import jakarta.inject.Singleton;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;


@Singleton
public class PwdEncryptionProvider {

    public boolean authenticate(char[] attemptedPassword, byte[] encryptedPassword, byte[] salt)
            throws InvalidKeySpecException {
        // Encrypt the clear-text password using the same salt that was used to
        // encrypt the original password
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

        // Authentication succeeds if encrypted password that the user entered
        // is equal to the stored hash
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    private byte[] getEncryptedPassword(char[] password, byte[] salt) {
        // check if all password rules are met
        // checkValidPassword(password);

        // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
        // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
        String algorithm = "PBKDF2WithHmacSHA1";

        // SHA-1 generates 160 bit hashes, so that's what makes sense here
        int derivedKeyLength = 160;


        int iterations = 20000;

        try {
            KeySpec spec = new PBEKeySpec(password, salt, iterations, derivedKeyLength);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            return secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException("Failed to hash password", ex);
        }
    }

    private byte[] generateSalt() {
        // VERY important to use SecureRandom instead of just Random
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error generating salt", ex);
        }
        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return salt;
    }


    public void setPassword(char[] clearPassword, User userAccount) {
        byte[] salt = generateSalt();
        userAccount.setPassword(getEncryptedPassword(clearPassword, salt));
        userAccount.setSalt(salt);
    }
}
