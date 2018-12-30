package runtime;

import runtime.evaluate.Node;
import runtime.evaluate.Leaf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

public class Runtime {
    private final VM vm;

    public Runtime(VM vm) {
        this.vm = vm;
    }

    public Value evaluate(Block block, Cell cell) {
        var result = Value.nop;
        switch (cell.getKind()) {
            case NODE:
                result = Node.evaluateNode(this, block, cell);
                break;
            case LEAF:
                result = Leaf.evaluateLeaf(this, block, cell);
                break;
        }
        this.getVm().getLogger().trace("evaluate "+cell);
        this.getVm().getLogger().trace("result "+result);
        return result;
    }

    public List<Value> evaluate(Block block, String src) {
        var fileName = "interpret@"+src.hashCode()+".src";
        var tempFile = new File(this.getVm().getTempDir(), fileName);
        try {
            var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
            writer.write(src);
            writer.close();
            var compiled = vm.compile(vm.preprocess(tempFile));
            var parser = new Parser();
            var cells = parser.parse(compiled);
            return cells.stream().map(cell -> this.evaluate(block, cell)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public VM getVm() {
        return vm;
    }
}
