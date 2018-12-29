package runtime.builtin;

import runtime.Block;
import runtime.Runtime;
import runtime.Value;

import java.util.List;

public class Variables extends BuiltIn {
    public Variables(Block rootBlock) {
        super("variablesIn", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        var value = args.get(0);
        var block = (Block) value.getObject();
        var names = block.getDeclared();
        var list = "["+ names.stream().reduce((l,r) -> "\""+l+"\""+", "+"\""+r+"\"").get()+"]";
        return runtime.evaluate(block, list).get(0);
    }
}
