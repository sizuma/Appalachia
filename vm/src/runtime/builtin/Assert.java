package runtime.builtin;


import runtime.Block;
import runtime.Value;

public class Assert extends BinaryOperation {
    public Assert(Block rootBlock) {
        super("assert", rootBlock);
    }

    @Override
    Value performBinaryOperator(Object _0, Object _1) {
        if (_0 == _1) return Value.True;
        var isEqual = _0.equals(_1);
        if (!isEqual) throw new RuntimeException("assertion error: "+_0 + " != " + _1);
        return Value.True;
    }
}
