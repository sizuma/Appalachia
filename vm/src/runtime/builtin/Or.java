package runtime.builtin;


import runtime.Block;
import runtime.Value;

import java.util.List;

public class Or extends BuiltIn {
    public Or(Block rootBlock) {
        super("or", rootBlock);
    }

    @Override
    public Value performBuildIn(List<Value> args) {
        var result = false;
        for(Value value: args) {
            result = value.isTrue();
            if (result) break;
        }
        return new Value(Value.Kind.NUMBER, result ? 1.0 : 0.0);
    }
}
