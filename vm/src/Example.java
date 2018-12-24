import runtime.VM;

import java.io.*;

public class Example {
    public static void main(String...args) throws IOException,InterruptedException {
        var vm = new VM(false);
        vm.execSTree(new File("../tmp.txt"));
    }
}
