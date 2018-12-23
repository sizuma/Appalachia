import runtime.VM;

import java.io.*;

public class Example {
    public static void main(String...args) throws IOException {
        var vm = new VM();
        vm.exec(new File("../tmp.txt"));
    }
}
