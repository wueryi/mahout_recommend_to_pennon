package pennon.handinhand.util;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class CommonUtil {
    public static String getBootPath() throws FileNotFoundException {

        if (isWindows()) {
            return ResourceUtils.getURL("classpath:").getPath().replace("/target/classes", "");
        }else {
            return "./";
        }
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
