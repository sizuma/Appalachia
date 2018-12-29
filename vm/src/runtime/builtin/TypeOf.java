package runtime.builtin;

import runtime.Block;
import runtime.Runtime;
import runtime.Value;

import java.util.List;

public class TypeOf extends BuiltIn {
    public TypeOf(Block rootBlock) {
        super("typeof", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        if (args.size() == 0) return new Value(Value.Kind.STRING, "null");
        var arg = args.get(0);
        switch (arg.getKind()) {
            case NUMBER:
                new Value(Value.Kind.STRING, "number");
                break;
            case STRING:
                new Value(Value.Kind.STRING, "string");
                break;
            case LAMBDA:
                new Value(Value.Kind.STRING, "lambda");
                break;
            case BLOCK:
                new Value(Value.Kind.STRING, "block");
                break;
            case NOP:
                new Value(Value.Kind.STRING, "null");
                break;
        }
        return new Value(Value.Kind.STRING, "null");
    }
}
