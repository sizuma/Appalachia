package runtime;

public class Compiler extends OtherProcess {
    public Compiler(VM vm) {
        super(vm, "./compiler");
    }
}
