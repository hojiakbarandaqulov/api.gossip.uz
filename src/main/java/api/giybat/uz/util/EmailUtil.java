package api.giybat.uz.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
    public static boolean isEmail(String phone) {
        String phoneRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(phoneRegex, phone);
    }
}
