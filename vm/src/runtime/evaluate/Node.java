package runtime.evaluate;

import runtime.*;
import runtime.Runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

    static public Value evaluateNode(Runtime runtime, Block block, Cell cell) {
        assert cell.getKind() == Cell.Kind.NODE;
        var action = cell.getCarString();
        var cdr = cell.getCdrCell();
        switch (action) {
            case "=":
                return Node.assign(runtime, block, cdr);
            case "block":
                return Node.block(runtime, block, cdr);
            case "lambda":
                return Node.lambda(runtime, block, cdr);
            case "call":
                return Node.call(runtime, block, cdr);
            case "if":
                return Node.ifNode(runtime, block, cdr);
        }

        return Value.nop;
    }

    static Value assign(Runtime runtime, Block block, Cell cons) {
        assert cons.getKind() == Cell.Kind.CONS;
        var car = cons.getCarCell();
        var cdr = cons.getCdrCell();

        assert car.getKind() == Cell.Kind.LEAF;
        assert car.getCar().equals("ID");
        var variableName = car.getCdrString();
        var variableValue = runtime.evaluate(block, cdr);
        block.declareVariable(variableName, variableValue);
        runtime.log("declare variable " + variableName + ":" + variableValue + " in " + block);
        return Value.nop;
    }

    static Value block(Runtime runtime, Block block, Cell cons) {
        var newBlock = block.newChildBlock();
        if (cons.getKind() == Cell.Kind.LEAF) return Leaf.evaluateLeaf(runtime, newBlock, cons);
        else if (cons.getKind() == Cell.Kind.NODE) return Node.evaluateNode(runtime, newBlock, cons);
        return Node.depthFirstVisit(runtime, newBlock, cons);
    }

    static private Value depthFirstVisit(Runtime runtime, Block block, Cell cons) {
        var car = cons.getCarCell();
        var cdr = cons.getCdrCell();

        if (car.getKind() == Cell.Kind.CONS) {
            Node.depthFirstVisit(runtime, block, car);
        } else {
            runtime.evaluate(block, car);
        }
        return runtime.evaluate(block, cdr);
    }

    static Value lambda(Runtime runtime, Block block, Cell cons) {
        return new Value(Value.Kind.LAMBDA, new LambdaBody(block, cons));
    }

    static Value call(Runtime runtime, Block block, Cell cons) {
        var car = cons.getCarCell();
        var name = car.getCdrString();
        var cdr = cons.getCdrCell();
        var mayLambda = block.getVariable(name);
        if (mayLambda.isEmpty()) throw new RuntimeException(name + " is not declared");
        var lambda = mayLambda.get();
        if (lambda.getKind() != Value.Kind.LAMBDA) throw new RuntimeException(name + " is not lambda");
        var body = (LambdaBody) lambda.getObject();
        var inLambdaBlock = body.getBlock().newChildBlock();

        var actualArgs = Node.evaluateArgs(runtime, block, cdr);
        if (body.getBuiltIn()) {
            return body.performBuildIn(actualArgs);
        }
        var parameters = Node.listLeave(body.getLambda().getCarCell())
                .stream()
                .filter(leaf -> !leaf.getCar().equals("NOP"))
                .collect(Collectors.toList());
        if (actualArgs.size() != parameters.size()) {
            throw new RuntimeException("invalid lambda arguments");
        }

        for(int index=0; index<actualArgs.size(); index++) {
            var arg = actualArgs.get(index);
            var param = parameters.get(index);
            var paramName = param.getCdrString();
            inLambdaBlock.declareVariable(paramName.strip(), arg);
        }

        return runtime.evaluate(inLambdaBlock, (Cell) body.getLambda().getCdr());
    }

    private static List<Cell> listLeave(Cell cell) {
        switch (cell.getKind()) {
            case LEAF:
                return List.of(cell);
            case NODE:
                return listLeave((Cell) cell.getCdr());
            case CONS:
                var list = new ArrayList<Cell>();
                list.addAll(listLeave(cell.getCarCell()));
                list.addAll(listLeave(cell.getCdrCell()));
                return list;
        }
        return List.of();
    }

    private static List<Value> evaluateArgs(Runtime runtime, Block block, Cell cell) {
        switch (cell.getKind()) {
            case LEAF:
                var leafValue = Leaf.evaluateLeaf(runtime, block, cell);
                return leafValue.equals(Value.nop) ? List.of() : List.of(leafValue);
            case NODE:
                var nodeOperator = (String)cell.getCar();
                nodeOperator = nodeOperator.strip();
                if (nodeOperator.equals(",")) {
                    return Node.evaluateArgs(runtime, block, (Cell) cell.getCdr());
                }
                return List.of(Node.evaluateNode(runtime, block, cell));
            case CONS:
                var list = new ArrayList<Value>();
                list.addAll(Node.evaluateArgs(runtime, block, cell.getCarCell()));
                list.addAll(Node.evaluateArgs(runtime, block, cell.getCdrCell()));
                return list;
        }
        return List.of();
    }

    static Value ifNode(Runtime runtime, Block block, Cell cell) {
        var car = cell.getCarCell();
        var cdr = cell.getCdrCell();

        var condition = runtime.evaluate(block, car);

        if (condition.isTrue()) {
            return runtime.evaluate(block, cdr.getCarCell());
        } else {
            return runtime.evaluate(block, cdr.getCdrCell());
        }
    }
}
