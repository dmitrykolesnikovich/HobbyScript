package hobbyscript.LLVM.generator;

import hobbyscript.LLVM.env.LLVMEnv;
import hobbyscript.LLVM.instruction.IRBuilder;
import hobbyscript.LLVM.util.LLVMs;
import hobbyscript.LLVM.visitor.AstVisitor;
import hobbyscript.Literal.BoolLiteral;
import hobbyscript.Literal.IDLiteral;
import hobbyscript.Literal.NumberLiteral;
import hobbyscript.Literal.StringLiteral;
import hobbyscript.Token.HobbyToken;
import hobbyscript.Utils.logger.Logger;
import hobbyscript.ast.*;
import org.bytedeco.llvm.LLVM.LLVMValueRef;
import org.bytedeco.llvm.global.LLVM;

public class LLVMVisitor implements AstVisitor {

    public LLVMVisitor() {
        Logger.init();
    }

    @Override
    public LLVMValueRef visitorNumberLiteral(NumberLiteral literal, LLVMEnv env) {
        final boolean isInteger = literal.isInteger();
        final Number number = literal.number();
        if (isInteger) {
            return LLVMs.constInt(number.longValue());
        } else {
            return LLVMs.constDouble(number.doubleValue());
        }
    }

    @Override
    public LLVMValueRef visitorStringLiteral(StringLiteral literal, LLVMEnv env) {
        return LLVMs.constString(literal.value());
    }

    @Override
    public LLVMValueRef visitorIDLiteral(IDLiteral literal, LLVMEnv env) {
        return env.get(literal.name(), LLVMValueRef.class);
    }

    @Override
    public LLVMValueRef visitorBoolLiteral(BoolLiteral literal, LLVMEnv env) {
        return LLVMs.constBool(literal.value());
    }

    @Override
    public LLVMValueRef visitorNegativeExpr(NegativeExpr expr, LLVMEnv env) {
        final LLVMValueRef value = expr.operand().accept(this, env);
        if (LLVMs.isInteger(value)) {
            return LLVM.LLVMConstNeg(value);
        }

        return LLVM.LLVMConstFNeg(value);
    }

    @Override
    public LLVMValueRef visitorNegativeBoolExpr(NegativeBoolExpr expr, LLVMEnv env) {
        final LLVMValueRef value = expr.operand().accept(this, env);
        if (!LLVMs.isBool(value)) {
            throw new IllegalArgumentException("value : " + expr.toJson() + " should be boolean.");
        }

        return LLVM.LLVMConstNot(value);
    }

    @Override
    public Object visitorBinaryExpr(BinaryExpr expr, LLVMEnv env) {
        final String operator = expr.operator();
        switch (operator) {
            case "=":
                return visitorAssign(expr, env);
        }

        throw new UnsupportedOperationException();
    }

    private LLVMValueRef visitorAssign(BinaryExpr expr, LLVMEnv env) {
        final IRBuilder builder = env.getIrBuilder();
        final boolean isGlobal = env.isGlobal();
        final LLVMValueRef value = expr.right().accept(this, env);
        final AstLeaf left = (AstLeaf) expr.left();

        if (left.token().getTag() != HobbyToken.ID) {
            throw new RuntimeException("un supported type [class attr assign]");
        }
        final IDLiteral id = (IDLiteral) left;
        LLVMValueRef val;

        if (isGlobal) {
            val = builder.createGlobal(env, value, id.name());
        } else {
            val = builder.createAlloca(env, value, id.name());
        }

        env.put(id.name(), value);
        return val;
    }
}
