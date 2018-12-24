package runtime.builtin.math;

import runtime.Block;
import runtime.Value;

public class GreaterThan extends BinaryOperation {

    public GreaterThan(Block rootBlock) {
        super(">", rootBlock);
    }

    @Override
    Value performBinaryOperator(Double _0, Double _1) {
        return new Value(Value.Kind.NUMBER, _0 >_1 ? 1.0 : 0.0);
    }
}
