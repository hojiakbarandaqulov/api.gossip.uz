package api.giybat.uz.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    public static boolean isPhone(String phone) {
        String phoneRegex = "^998\\d{9}$";
        return Pattern.matches(phoneRegex, phone);
    }
}
