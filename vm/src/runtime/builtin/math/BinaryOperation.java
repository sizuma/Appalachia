package runtime.builtin.math;

import runtime.Block;
import runtime.Value;
import runtime.builtin.BuiltIn;
import runtime.Runtime;

import java.util.List;

public abstract class BinaryOperation extends BuiltIn {
    public BinaryOperation(String declareName, Block rootBlock) {
        super(declareName, rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        if (args.size() != 2) {
            throw new RuntimeException("invalid lambda arguments");
        }

        var _0 = args.get(0);
        var _1 = args.get(1);

        if (_0.getKind() != Value.Kind.NUMBER || _1.getKind() != Value.Kind.NUMBER) {
            throw new RuntimeException("type mismatch. binary operator " + this.getDeclareName() + " require 2 numbers");
        }
        return this.performBinaryOperator((Double) _0.getObject(), (Double) _1.getObject());
    }

    abstract Value performBinaryOperator(Double _0, Double _1);
}
