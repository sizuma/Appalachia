package runtime.builtin;


import runtime.Block;
import runtime.Value;

import java.util.List;

public class And extends BuiltIn {
    public And(Block rootBlock) {
        super("and", rootBlock);
    }

    @Override
    public Value performBuildIn(List<Value> args) {
        var result = true;
        for(Value value: args) {
            result = value.isTrue();
            if (!result) break;
        }
        return result ? Value.True : Value.False;
    }
}
