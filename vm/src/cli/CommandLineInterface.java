package cli;

import runtime.VM;

public class CommandLineInterface {
    public static void main(String[] args) throws Exception {
        var options = OptionParser.parse(args);
        var vm = new VM(options.isShowLog());

        options.getFiles().forEach(file -> {
            try {
                if (options.isCompiled()) {
                    vm.execTree(file);
                } else {
                    vm.interpret(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
