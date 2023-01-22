package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{
    private final IExpression exp;

    public OpenRFileStatement(IExpression exp) { this.exp = exp; }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        IValue result = exp.eval(state.getSymTable(), state.getHeap());

        if ( !result.getType().equals(new StringType()) )
            throw new MyException(result + " not string type");
        if ( state.getFileTable().isDescDef( (StringValue)result ) )
            throw new MyException(result + " descriptor already in fileTable");

        try {
            BufferedReader br = new BufferedReader(new FileReader(( (StringValue) result).getValue() ));
            state.getFileTable().add((StringValue)result, br);
        }
        catch(IOException e) { System.out.println(e.getMessage()); }

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new OpenRFileStatement(exp.deepcopy());
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        IType type = exp.typecheck(typeEnv);

        if (!type.equals(new StringType()) )
            throw new MyException(exp + " not string type");
        return typeEnv;
    }

    public String toString() { return "Open file(" + exp + ")"; }
}
