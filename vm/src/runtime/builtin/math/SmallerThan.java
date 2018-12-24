package runtime.builtin.math;

import runtime.Block;
import runtime.Value;

public class SmallerThan extends BinaryOperation {

    public SmallerThan(Block rootBlock) {
        super("<", rootBlock);
    }

    @Override
    Value performBinaryOperator(Double _0, Double _1) {
        return  _0 <_1 ? Value.True : Value.False;
    }
}
