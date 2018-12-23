package runtime.builtin.math;

import runtime.Block;
import runtime.Value;

public class Multiple extends BinaryOperation {

    public Multiple(Block rootBlock) {
        super("*", rootBlock);
    }

    @Override
    Value performBinaryOperator(Double _0, Double _1) {
        return new Value(Value.Kind.NUMBER, _0*_1);
    }
}
