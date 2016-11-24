package firstgen.hopelesswar.desktop;

import java.io.File;
import java.io.IOException;

/**
 * Created by Olva on 7/21/16.
 */

public final class JavaProcess {

    private JavaProcess() {}

    public static int exec(Class c) throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = c.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className);

        Process process = builder.start();
        process.waitFor();
        return process.exitValue();
    }

}

