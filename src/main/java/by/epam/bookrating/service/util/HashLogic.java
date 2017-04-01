package by.epam.bookrating.service.util;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Class contains method for hashing password using sha256 encryption.
 * @author anyab
 */
public class HashLogic {
    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password); //todo добавить соль
    }
}
