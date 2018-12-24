package runtime;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class VM {
    private final boolean logging;
    private final Block rootBlock;

    public VM(boolean logging) {
        this.logging = logging;
        this.rootBlock = new RootBlock();
    }

    public VM() {
        this(false);
    }

    public void execSTree(BufferedReader reader) throws IOException {
        var parser = new Parser();
        List<Cell> statements = parser.parse(reader);

        var runtime = new Runtime(this.logging);
        statements.stream().map(Cell::toString).forEach(runtime::log);

        statements.forEach(statement -> {
            runtime.evaluate(this.rootBlock, statement);
        });
    }

    public void execSTree(File file) throws IOException {
        this.execSTree(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
    }

    public void interpret(BufferedReader reader) throws IOException,InterruptedException {
        var compiler = new Compiler();
        var sTree = compiler.compile(reader);
        var sTreeStream = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(sTree.getBytes(StandardCharsets.UTF_8))));
        this.execSTree(sTreeStream);
    }
}
