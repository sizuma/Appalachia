package cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptionParser {
    public static Options parse(List<String> args) {
        var option = new Options();
        while (args.size() > 0) {
            var arg = args.get(0).strip();
            args.remove(0);
            if (arg.equals("--show-log")) option.setShowLog(true);
        }
        return option;
    }
    public static Options parse(String... args) {
        var list = new ArrayList<String>(args.length);
        Collections.addAll(list, args);
        return OptionParser.parse(list);
    }
}
