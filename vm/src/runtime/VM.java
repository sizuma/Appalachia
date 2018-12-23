package runtime;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class VM {

    public void exec(BufferedReader reader) throws IOException {
        var parser = new Parser();
        List<Cell> statements = parser.parse(reader);
        statements.forEach(System.out::println);
        System.out.println();
        var runtime = new Runtime(true);
        var rootBlock = new Block(Optional.empty());

        statements.forEach(statement -> {
            runtime.evaluate(rootBlock, statement);
        });
    }

    public void exec(File file) throws IOException {
        this.exec(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
    }
}
