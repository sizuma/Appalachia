import runtime.VM;

import java.io.*;

public class Test {
    private static final String srcDirectory = "./test";

    public static void main(String...args) throws IOException {
        var testDirectory = new File(Test.srcDirectory);

        for(String testFile:testDirectory.list()) {
            var testFilePath = srcDirectory +"/"+ testFile;
            doTest(testFilePath);
        }
    }

    public static void doTest(String srcPath) {
        try {
            System.out.println("test: "+srcPath);
            var reader = new BufferedReader(new InputStreamReader(new FileInputStream(srcPath)));
            var vm = new VM(true);
            vm.interpret(reader);
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
