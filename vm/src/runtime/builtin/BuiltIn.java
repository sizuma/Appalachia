package runtime.builtin;

import runtime.Block;
import runtime.LambdaBody;
import runtime.Value;

import java.util.List;

public abstract class BuiltIn extends LambdaBody {
    private final String declareName;
    public BuiltIn(String declareName, Block rootBlock) {
        super(rootBlock, null, true);
        this.declareName = declareName;
    }

    public String getDeclareName() {
        return declareName;
    }

    @Override
    abstract public Value performBuildIn(List<Value> args);
}
