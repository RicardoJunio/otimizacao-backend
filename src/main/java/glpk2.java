import org.gnu.glpk.GLPK;

import java.io.File;

public class glpk2 {

    static {
        try {
            File dll = new File("glpk_4_64.dll");
            File dll2 = new File ("glpk_4_64_java.dll");
            System.load(dll.getAbsolutePath());
            System.load(dll2.getAbsolutePath());
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String argv[])
    {

        System.out.print(GLPK.glp_version());
    }
}

