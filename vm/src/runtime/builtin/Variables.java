package runtime.builtin;

import runtime.Block;
import runtime.Parser;
import runtime.Runtime;
import runtime.Value;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Variables extends BuiltIn {
    public Variables(Block rootBlock) {
        super("variablesIn", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        var vm = runtime.getVm();
        var value = args.get(0);
        var block = (Block) value.getObject();
        var names = block.getDeclared();
        var list = "["+ names.stream().reduce((l,r) -> "\""+l+"\""+", "+"\""+r+"\"").get()+"]";
        var tempFile = new File(vm.getVmDirectory(), "variable.list.src");

        try {
            var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
            writer.write(list);
            writer.close();

            var src = vm.compile(vm.preprocess(tempFile));
            var parser = new Parser();
            var parsed = parser.parse(src);
            return runtime.evaluate(vm.getUserBlock(), parsed.get(0));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
