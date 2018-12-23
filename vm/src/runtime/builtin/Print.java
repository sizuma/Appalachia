package runtime.builtin;

import runtime.Block;
import runtime.Value;

import java.util.List;

public class Print extends BuiltIn {
    public Print(Block rootBlock) {
        super("print", rootBlock);
    }

    @Override
    public Value performBuildIn(List<Value> args) {
        for(int index=0; index<args.size(); index++) {
            var arg = args.get(index);
            System.out.print(arg.getObject());
            if (index < args.size()-1) {
                System.out.print(",");
            }
        }

        System.out.println();
        return Value.nop;
    }
}
