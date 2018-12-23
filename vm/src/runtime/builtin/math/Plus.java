package runtime.builtin.math;

import runtime.Block;
import runtime.Value;
import runtime.builtin.BuiltIn;

import java.util.List;

public class Plus extends BinaryOperation {

    public Plus(Block rootBlock) {
        super("+", rootBlock);
    }

    @Override
    Value performBinaryOperator(Double _0, Double _1) {
        return new Value(Value.Kind.NUMBER, _0+_1);
    }
}
