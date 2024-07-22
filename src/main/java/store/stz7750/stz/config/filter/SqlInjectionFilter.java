package store.stz7750.stz.config.filter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * packageName    : stz-store.stz7750.stz.config.filter
 * fileName       : SqlInjectionFilter
 * author         : stz
 * date           : 7/22/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/22/24        stz       최초 생성
 */
public class SqlInjectionFilter {

    private static final List<String> SQL_KEYWORDS = Arrays.asList(
            "SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "CREATE", "ALTER", "TRUNCATE",
            "GRANT", "REVOKE", "COMMIT", "ROLLBACK", "SAVEPOINT", "SET", "DECLARE"
    );

    public static boolean containsSQLKeywords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String upperInput = input.toUpperCase();
        for (String keyword : SQL_KEYWORDS) {
            Pattern pattern = Pattern.compile("\\b" + keyword + "\\b");
            if (pattern.matcher(upperInput).find()) {
                return true;
            }
        }
        return false;
    }
}

