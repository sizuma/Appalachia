package runtime;

import java.io.*;
import java.util.List;

public class VM {

    public void exec(BufferedReader reader) throws IOException {
        var parser = new Parser();
        List<Cell> statements = parser.parse(reader);

        var runtime = new Runtime(true);
        statements.stream().map(Cell::toString).forEach(runtime::log);
        var rootBlock = new RootBlock();

        statements.forEach(statement -> {
            runtime.evaluate(rootBlock, statement);
        });
    }

    public void exec(File file) throws IOException {
        this.exec(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
    }
}
