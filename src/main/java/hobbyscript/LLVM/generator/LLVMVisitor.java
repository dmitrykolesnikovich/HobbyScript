package hobbyscript.LLVM.generator;

import hobbyscript.Eval.Env.Environment;
import hobbyscript.LLVM.visitor.AstVisitor;
import hobbyscript.Literal.IdLiteral;
import hobbyscript.Literal.NumberLiteral;
import hobbyscript.Utils.logger.Logger;

public class LLVMVisitor implements AstVisitor<Double> {

    private Environment env;

    public LLVMVisitor(Environment env) {
        this.env = env;
        Logger.init();
    }

    @Override
    public Double visitorNumberLiteral(NumberLiteral numberLiteral) {
        return (double) numberLiteral.eval(env);
    }

    @Override
    public Double visitorIdLiteral(IdLiteral idLiteral) {
        return (Double) idLiteral.eval(env);
    }


}
