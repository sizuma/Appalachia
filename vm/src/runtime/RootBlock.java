package runtime;

import runtime.builtin.BuiltIn;
import runtime.builtin.Equals;
import runtime.builtin.NotEquals;
import runtime.builtin.Print;
import runtime.builtin.math.*;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class RootBlock extends Block {
    private static final Class<?>[] builtIns = {
            Print.class,
            Equals.class,
            NotEquals.class,

            Plus.class,
            Minus.class,
            Multiple.class,
            Divide.class,
            Mod.class,
            GreaterThan.class,
            SmallerThan.class,
    };
    public RootBlock() {
        super(Optional.empty());
        for(var buildInClass: builtIns) {
            try {
                Constructor constractor = buildInClass.getConstructor(Block.class);
                var buildIn = (BuiltIn)constractor.newInstance(this);
                this.declareVariable(buildIn.getDeclareName(), new Value(Value.Kind.LAMBDA, buildIn));
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }
}
