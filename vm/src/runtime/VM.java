package runtime;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class VM {
    private final String stdlibDir = "./stdlib";

    private boolean logging;
    private final RootBlock rootBlock;
    private final Block userBlock;

    public VM(boolean logging) throws IOException, InterruptedException {
        this.logging = logging;
        this.rootBlock = new RootBlock();
        this.userBlock = this.rootBlock.newChildBlock();
        this.loadStdlib();
    }

    public VM() throws IOException, InterruptedException {
        this(false);
    }

    public void execSTree(BufferedReader reader, Block block) throws IOException {
        var parser = new Parser();
        List<Cell> statements = parser.parse(reader);

        var runtime = new Runtime(this.logging);
        statements.stream().map(Cell::toString).forEach(runtime::log);

        statements.forEach(statement -> {
            runtime.evaluate(block, statement);
        });
    }

    public void execSTree(BufferedReader reader) throws IOException {
        this.execSTree(reader, this.userBlock);
    }

    public void execSTree(File file) throws IOException {
        this.execSTree(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
    }

    public void interpret(BufferedReader reader, Block block) throws IOException,InterruptedException {
        var compiler = new Compiler();
        var sTree = compiler.redirection(reader);
        var sTreeStream = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(sTree.getBytes(StandardCharsets.UTF_8))));
        this.execSTree(sTreeStream, block);
    }

    public void interpret(BufferedReader reader) throws IOException,InterruptedException {
        this.interpret(reader, this.userBlock);
    }
    private void loadStdlib() throws IOException, InterruptedException {
        var stdlibDirFile = new File(this.stdlibDir);
        var logging = this.logging;
        this.logging = false;
        for(File file: stdlibDirFile.listFiles()) {
            this.interpret(new BufferedReader(new InputStreamReader(new FileInputStream(file))), this.rootBlock);
        }
        this.logging = logging;
    }
}
