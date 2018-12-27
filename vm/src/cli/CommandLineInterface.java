package cli;

import runtime.VM;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLineInterface {

    static BufferedReader stdinBufferedReader () {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) throws Exception {
        var options = OptionParser.parse(args);
        var vm = new VM(options.isShowLog());
        vm.execSTree(stdinBufferedReader());
    }

}
