package runtime.builtin;

import runtime.Block;
import runtime.Runtime;
import runtime.Value;

import java.util.List;

public class GetVariable extends BuiltIn {
    public GetVariable(Block rootBlock) {
        super("getVariable", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        var block = (Block) args.get(0).getObject();
        var key = (String) args.get(1).getObject();
        return block.getVariable(key).orElseGet(() -> Value.nop);
    }
}
