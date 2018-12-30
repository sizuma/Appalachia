package cli;

import log.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptionParser {
    public static Options parse(List<String> args) {
        var option = new Options();
        while (args.size() > 0) {
            var arg = args.get(0).strip();
            args.remove(0);
            switch (arg) {
                case "--log-level":
                    var nextArgs = args.get(0);
                    args.remove(0);
                    option.setLevel(Logger.Level.valueOf(nextArgs.toUpperCase()));
                    break;
                case "--compiled":
                    option.setCompiled(true);
                    break;
                default:
                    option.addFile(new File(arg));
            }
        }
        return option;
    }

    public static Options parse(String... args) {
        var list = new ArrayList<String>(args.length);
        Collections.addAll(list, args);
        return OptionParser.parse(list);
    }
}
