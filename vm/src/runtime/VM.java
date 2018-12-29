package runtime;

import java.io.*;
import java.util.List;

public class VM {
    private final File jarFile = new File(System.getProperty("java.class.path"));
    private final File vmDirectory = jarFile.toPath().toAbsolutePath().getParent().toFile();
    private final File stdlibDir = new File(vmDirectory, "stdlib");
    private final File tempDir = new File(vmDirectory, ".tmp");

    private boolean logging;

    private final Runtime runtime;
    private final RootBlock rootBlock;
    private final Block stdlibBlock;
    private final Block userBlock;

    public VM(boolean logging) throws IOException, InterruptedException {
        this.logging = logging;
        this.runtime = new Runtime(this, this.logging);
        this.rootBlock = new RootBlock();
        this.stdlibBlock = rootBlock.newChildBlock();
        this.userBlock = this.stdlibBlock.newChildBlock();
        this.prepareTempDirectory();
        this.loadStdlib();
    }

    public VM() throws IOException, InterruptedException {
        this(false);
    }

    public void execTree(File treeFile, Block block) throws IOException {
        var parser = new Parser();
        List<Cell> statements = parser.parse(treeFile);
        statements.stream().map(Cell::toString).forEach(runtime::log);

        statements.forEach(statement -> {
            runtime.evaluate(block, statement);
        });
    }

    public void execTree(File treeFile) throws IOException {
        this.execTree(treeFile, this.userBlock);
    }

    public File preprocess(File src) throws IOException, InterruptedException {
        var preprocessor = new Preprocessor(this);
        var outputFileName = src.getName()+".preprocessed";
        var output = new File(this.tempDir, outputFileName);
        preprocessor.redirectToFile(src, output);
        return output;
    }

    public File compile(File src) throws IOException, InterruptedException {
        var compiler = new Compiler(this);
        var outputFileName = src.getName()+".compiled";
        var output = new File(this.tempDir, outputFileName);
        compiler.redirectToFile(src, output);
        return output;
    }

    public void interpret(File srcFile, Block block) throws IOException,InterruptedException {
        var compiledFile = this.compile(this.preprocess(srcFile));
        this.execTree(compiledFile, block);
    }
    public void interpret(File srcFile) throws IOException,InterruptedException {
        this.interpret(srcFile, this.userBlock);
    }

    private void prepareTempDirectory() {
        if(this.tempDir.isDirectory()) return;
        tempDir.mkdir();
    }

    private void loadStdlib() throws IOException, InterruptedException {
        var logging = this.logging;
        this.logging = false;
        for(File file: this.stdlibDir.listFiles()) {
            this.interpret(file, this.stdlibBlock);
        }
        this.logging = logging;
    }

    public File getVmDirectory() {
        return vmDirectory;
    }

    public File getTempDir() {
        return tempDir;
    }

    public Block getUserBlock() {
        return userBlock;
    }
}
