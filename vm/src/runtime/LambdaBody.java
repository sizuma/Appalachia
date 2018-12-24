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

    public Value performBuildIn(Runtime runtime, List<Value> args) {
        return null;
    }

    public Value run(Runtime runtime) {
        var lambdaNode = new Cell("lambda", this.getLambda(), Cell.Kind.NODE);
        var lambdaArgs = new Cell(lambdaNode, new Cell("nop", null, Cell.Kind.LEAF), Cell.Kind.CONS);
        var callCell = new Cell("call", lambdaArgs, Cell.Kind.NODE);
        return runtime.evaluate(this.getBlock(), callCell);
    }
}
