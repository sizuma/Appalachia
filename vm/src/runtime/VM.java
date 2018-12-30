package runtime;

import log.Console;
import log.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VM {
    private final File jarFile = new File(System.getProperty("java.class.path"));
    private final File vmDirectory = jarFile.toPath().toAbsolutePath().getParent().toFile();
    private final File stdlibDir = new File(vmDirectory, "stdlib");
    private final File tempDir = new File(vmDirectory, ".tmp");

    private Logger logger;

    private final Runtime runtime;
    private final RootBlock rootBlock;
    private final Block stdlibBlock;
    private final Block userBlock;

    public VM(Logger logger) {
        this.logger = logger;
        this.runtime = new Runtime(this);
        this.rootBlock = new RootBlock();
        this.stdlibBlock = rootBlock.newChildBlock();
        this.userBlock = this.stdlibBlock.newChildBlock();
        this.prepareTempDirectory();
        this.loadStdlib();
    }

    public VM() {
        this(new Console());
    }

    public void execTree(File treeFile, Block block) {
        var parser = new Parser();
        List<Cell> statements;
        try {
            statements = parser.parse(treeFile);
        } catch (Exception e) {
            this.logger.error(e.toString());
            statements = new ArrayList<>();
        }
        for(int line=0; line < statements.size(); line++) {
            var statement = statements.get(line);
            this.logger.trace("exec line: "+(line+1)+" "+statement);
            try {
                runtime.evaluate(block, statement);
            } catch (Exception e) {
                this.logger.error(e.toString());
            }
        }
    }

    public void execTree(File treeFile) {
        this.execTree(treeFile, this.userBlock);
    }

    public File preprocess(File src) {
        try {
            var preprocessor = new Preprocessor(this);
            var outputFileName = src.getName()+".preprocessed";
            var output = new File(this.tempDir, outputFileName);
            preprocessor.redirectToFile(src, output);
            return output;
        } catch (Exception e) {
            this.logger.error(e.toString());
            return null;
        }
    }

    public File compile(File src) {
        try {
            var compiler = new Compiler(this);
            var outputFileName = src.getName()+".compiled";
            var output = new File(this.tempDir, outputFileName);
            compiler.redirectToFile(src, output);
            return output;
        } catch (Exception e) {
            this.logger.error(e.toString());
            return null;
        }
    }

    public void interpret(File srcFile, Block block) {
        var compiledFile = this.compile(this.preprocess(srcFile));
        this.execTree(compiledFile, block);
    }
    public void interpret(File srcFile) {
        this.interpret(srcFile, this.userBlock);
    }

    private void prepareTempDirectory() {
        if(this.tempDir.isDirectory()) return;
        tempDir.mkdir();
    }

    private void loadStdlib() {
        for(File file: this.stdlibDir.listFiles()) {
            this.interpret(file, this.stdlibBlock);
        }
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

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
