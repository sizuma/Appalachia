package runtime.builtin;

import runtime.*;
import runtime.Runtime;

import java.util.List;

public class If extends BuiltIn {
    public If(Block rootBlock) {
        super("if", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        var condition = args.get(0);
        if (condition.isTrue()) {
            var lambda = (LambdaBody)args.get(1).getObject();
            return lambda.run(runtime);
        } else {
            if (args.size() < 3) return Value.nop;
            var lambda = (LambdaBody)args.get(2).getObject();
            return lambda.run(runtime);
        }
    }
}
