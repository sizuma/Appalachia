package runtime.builtin;


import runtime.Block;
import runtime.Value;

public class NotEquals extends BinaryOperation {
    public NotEquals(Block rootBlock) {
        super("!=", rootBlock);
    }

    @Override
    Value performBinaryOperator(Object _0, Object _1) {
        if (_0 == null && _1 == null) return Value.False;
        if (_0 == null) return Value.False;
        return !_0.equals(_1) ? Value.True : Value.False;
    }
}
