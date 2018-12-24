package runtime.builtin;


import runtime.Block;
import runtime.Value;
import runtime.Runtime;

import java.util.List;

public class Or extends BuiltIn {
    public Or(Block rootBlock) {
        super("or", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        var result = false;
        for(Value value: args) {
            result = value.isTrue();
            if (result) break;
        }
        return result ? Value.True : Value.False;
    }
}
