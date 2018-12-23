package runtime;


import java.util.List;

public class LambdaBody {
    private final Block block;
    private final Cell lambda;
    private final Boolean isBuiltIn;

    public LambdaBody(Block block, Cell lambda, boolean isBuiltIn) {
        this.block = block;
        this.lambda = lambda;
        this.isBuiltIn = isBuiltIn;
    }

    public LambdaBody(Block block, Cell lambda) {
        this(block, lambda, false);
    }

    public Block getBlock() {
        return block;
    }

    public Cell getLambda() {
        return lambda;
    }

    public Boolean getBuiltIn() {
        return isBuiltIn;
    }

    @Override
    public String toString() {
        return "LambdaBody{" +
                "lambda=" + lambda +
                ", isBuiltIn=" + isBuiltIn +
                '}';
    }

    public Value performBuildIn(List<Value> args) {
        return null;
    }
}
