import runtime.VM;

import java.io.*;

public class Test {
    private static final String srcDirectory = "./test";

    public static void main(String...args) throws Exception {
        var testDirectory = new File(Test.srcDirectory);

        for(String testFile:testDirectory.list()) {
            var testFilePath = srcDirectory +"/"+ testFile;
            doTest(testFilePath);
        }
    }

    public static void doTest(String srcPath) throws Exception {
        System.out.println("test: "+srcPath);
        var reader = new BufferedReader(new InputStreamReader(new FileInputStream(srcPath)));
        var vm = new VM(true);
        vm.interpret(reader);
        System.out.println("success");
    }
}
