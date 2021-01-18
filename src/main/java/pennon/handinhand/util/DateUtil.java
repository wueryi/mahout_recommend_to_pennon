package pennon.handinhand.util;

/**
 * @author jishuai
 */
public class DateUtil {
    public static int currentTimestamp() {
        return new Long(System.currentTimeMillis() / 1000).intValue();
    }
}
