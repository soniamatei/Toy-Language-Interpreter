package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    private final IExpression exp;

    public CloseRFileStatement(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        IValue result = exp.eval(state.getSymTable(), state.getHeap());

        if(!result.getType().equals(new StringType()))
            throw new MyException(result + " not a string");
        if ( !state.getFileTable().isDescDef( (StringValue)result ) )
            throw new MyException(result + " descriptor not in fileTable");

        try {
            BufferedReader bf = state.getFileTable().lookUp((StringValue) result);
            bf.close();
            state.getFileTable().remove((StringValue) result);
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new CloseRFileStatement(exp.deepcopy());
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        IType type = exp.typecheck(typeEnv);

        if(!type.equals(new StringType()))
            throw new MyException(exp + " not a string");
        return null;
    }

    public String toString() { return "Close file(" + exp + ")"; }
}
