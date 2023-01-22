package Model.Statement;

import Model.ADT.FileTable;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{
    private final IExpression exp;
    private final String var_name;

    public ReadFileStatement(IExpression exp, String var_name) {

        this.exp = exp;
        this.var_name =var_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        IValue result = exp.eval(state.getSymTable(), state.getHeap());

        if ( !result.getType().equals(new StringType()) )
            throw new MyException(result + " is not string");
        if( !state.getSymTable().isVarDef(var_name) )
            throw new MyException(var_name + " not in symTable");
        if( !state.getSymTable().lookUp(var_name).getType().equals(new IntType()) )
            throw new MyException(var_name + " not int");

        StringValue varf = (StringValue)result;

        if(!state.getFileTable().isDescDef(varf) )
            throw new MyException(varf + " descriptor not open");
        else {
            BufferedReader bf = state.getFileTable().lookUp((StringValue) result);
            int nr;
            try {
                String s = bf.readLine();
                nr = Integer.parseInt(s);
            } catch (IOException e) {
                nr = 0;
            } catch (NumberFormatException e) {
                throw new MyException(e.getMessage());
            }
            state.getSymTable().update(var_name, new IntValue(nr));
            return null;
        }
    }

    @Override
    public IStatement deepcopy() {
        return new ReadFileStatement(exp.deepcopy(), var_name);
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        IType typevar = typeEnv.lookUp(var_name);
        IType typexp = exp.typecheck(typeEnv);

        if ( !typexp.equals(new StringType()) )
            throw new MyException(exp + " is not string");
        if( !typevar.equals(new IntType()) )
            throw new MyException(var_name + " not int");
        return typeEnv;
    }

    public String toString() { return "Read file(" + exp + ", " + var_name +")"; }
}
