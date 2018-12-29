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
    private final boolean logging;

    public Runtime(VM vm, boolean logging) {
        this.vm = vm;
        this.logging = logging;
    }

    public Value evaluate(Block block, Cell cell) {
        this.log("evaluate "+cell+" in "+block);

        switch (cell.getKind()) {
            case NODE:
                return Node.evaluateNode(this, block, cell);
            case LEAF:
                return Leaf.evaluateLeaf(this, block, cell);
        }
        return Value.nop;
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

    public void log(String string) {
        if (this.logging) {
            System.out.println(string);
        }
    }

    public VM getVm() {
        return vm;
    }
}
