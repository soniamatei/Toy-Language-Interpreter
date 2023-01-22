package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;

public class PrintStatement implements IStatement{
    private final IExpression exp;

    public PrintStatement(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public String toString(){ return "print(" + exp.toString() +")"; }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        state.getOut().add(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new PrintStatement(exp.deepcopy());
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String,IType> typeEnv) throws MyException{

        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
