package runtime;

import runtime.builtin.BuiltIn;

import java.util.List;

public class Assign extends BuiltIn {
    public Assign(Block rootBlock) {
        super("assign", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        if (args.size() < 2) {
            throw new RuntimeException("assign require least 2 args but got"+ args);
        }

        var _0 = args.get(0);
        var _1 = args.get(1);

        if (_0.getKind() != Value.Kind.BLOCK) {
            throw new RuntimeException("assign require block on first arg but got "+ _0);
        }
        var block = (Block) _0.getObject();

        if (_1.getKind() != Value.Kind.STRING) {
            throw new RuntimeException("assign require STRING on second arg but got "+ _1);
        }
        var name = (String) _1.getObject();
        name = name.substring(1, name.length()-1);
        Value value = Value.nop;
        if (args.size() > 2) {
            value = args.get(2);
        }
        block.declareVariable(name, value);
        return Value.nop;
    }
}
