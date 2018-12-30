package runtime.builtin;

import runtime.Block;
import runtime.Value;
import runtime.Runtime;

import java.util.List;

public class Print extends BuiltIn {
    public Print(Block rootBlock) {
        super("print", rootBlock);
    }

    @Override
    public Value performBuildIn(Runtime runtime, List<Value> args) {
        var builder = new StringBuilder();

        for(int index=0; index<args.size(); index++) {
            var arg = args.get(index);
            builder.append(arg);
            if (index < args.size()-1) {
                builder.append(",");
            }
        }
        runtime.getVm().getLogger().info(builder.toString());
        return Value.nop;
    }
}
