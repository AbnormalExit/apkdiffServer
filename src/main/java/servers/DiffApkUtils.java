package servers;

/**
 * Created by sxshi on 2017-12-27.
 */
public class DiffApkUtils {

    static {
        System.loadLibrary("apkdiff");
    }

    public static native void diff(String oldApkPath, String newApkPath, int fileNums);
}
