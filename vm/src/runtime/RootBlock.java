package runtime;

import runtime.builtin.*;
import runtime.builtin.math.*;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class RootBlock extends Block {
    private static final Class<?>[] builtIns = {
            Assign.class,
            Assert.class,
            Print.class,
            Equals.class,
            NotEquals.class,
            Or.class,
            And.class,
            If.class,
            TypeOf.class,

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

    @Override
    public String toString() {
        return "RootBlock{}";
    }
}
