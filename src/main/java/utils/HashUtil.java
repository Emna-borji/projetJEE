package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {

    private static final int SALT_LENGTH = 16; // Length of the salt
    private static final int ITERATIONS = 10000; // Number of iterations
    private static final int KEY_LENGTH = 256; // Key length in bits

    // Method to hash the password and return the salt:hash string
    public static String hashPassword(String password) throws Exception {
        // Generate a salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        // Create the PBEKeySpec with the password and salt
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        // Generate the hashed password
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Combine the salt and the hash and encode them to base64
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedHash = Base64.getEncoder().encodeToString(hash);

        // Return the salt and hash in the format "salt:hash"
        return encodedSalt + ":" + encodedHash;
    }

    // Method to verify the password using the stored salt and hash
    public static boolean verifyPassword(String enteredPassword, String storedSalt, String storedHash) throws Exception {
        // Decode the base64 encoded salt
        byte[] salt = Base64.getDecoder().decode(storedSalt);

        // Create the PBEKeySpec with the entered password and the stored salt
        PBEKeySpec spec = new PBEKeySpec(enteredPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        // Generate the hash for the entered password
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Encode the hash and compare it with the stored hash
        String encodedHash = Base64.getEncoder().encodeToString(hash);
        return encodedHash.equals(storedHash);
    }
}
