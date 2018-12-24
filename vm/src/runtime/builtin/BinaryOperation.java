package runtime.builtin;

import runtime.Block;
import runtime.Value;
import runtime.Runtime;

import java.util.List;

public abstract class BinaryOperation extends BuiltIn {
    public BinaryOperation(String declareName, Block rootBlock) {
        super(declareName, rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        if (args.size() != 2) {
            throw new RuntimeException("invalid lambda arguments "+args);
        }

        var _0 = args.get(0);
        var _1 = args.get(1);

        return this.performBinaryOperator(_0.getObject(),_1.getObject());
    }

    abstract Value performBinaryOperator(Object _0, Object _1);
}
