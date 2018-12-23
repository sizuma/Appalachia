package runtime;


public class LambdaBody {
    private final Block block;
    private final Cell lambda;

    public LambdaBody(Block block, Cell lambda) {
        this.block = block;
        this.lambda = lambda;
    }

    public Block getBlock() {
        return block;
    }

    public Cell getLambda() {
        return lambda;
    }

    @Override
    public String toString() {
        return "LambdaBody{" +
                "lambda=" + lambda +
                '}';
    }
}
