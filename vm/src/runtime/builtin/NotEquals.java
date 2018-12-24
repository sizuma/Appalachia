package runtime.builtin;


import runtime.Block;
import runtime.Value;

public class NotEquals extends BinaryOperation {
    public NotEquals(Block rootBlock) {
        super("!=", rootBlock);
    }

    @Override
    Value performBinaryOperator(Object _0, Object _1) {
        return new Value(Value.Kind.NUMBER, !_0.equals(_1) ? 1.0 : 0.0);
    }
}
