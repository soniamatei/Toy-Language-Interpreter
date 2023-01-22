package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStatement implements IStatement{
    private final IExpression exp;
    private final IStatement thenS;
    private final IStatement elseS;

    public IfStatement(IExpression e, IStatement t, IStatement el) {

        exp=e;
        thenS=t;
        elseS=el;
    }

    public String toString(){ return "( IF(" + exp.toString() + ") THEN(" + thenS.toString()
            + ") ELSE(" + elseS.toString() + ") )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        IValue cond =  exp.eval(state.getSymTable(), state.getHeap());

        if (!cond.getType().equals(new BoolType()))
            throw new MyException("conditional expr is not a boolean");
        else{
            if (((BoolValue)cond).getValue())
                state.getExeStack().push(thenS);
            else state.getExeStack().push(elseS);
        }
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new IfStatement(exp.deepcopy(), thenS.deepcopy(), elseS.deepcopy());
    }

    @Override
    public ITypeEnv<String,IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException{

        IType typexp=exp.typecheck(typeEnv);

        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepcopy());
            elseS.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
