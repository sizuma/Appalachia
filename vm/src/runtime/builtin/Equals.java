package runtime.builtin;


import runtime.Block;
import runtime.Value;

public class Equals extends BinaryOperation {
    public Equals(Block rootBlock) {
        super("==", rootBlock);
    }

    @Override
    Value performBinaryOperator(Object _0, Object _1) {
        return  _0.equals(_1) ? Value.True : Value.False;
    }
}
