package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.ReferenceValue;
import Model.Value.StringValue;

public class VarDeclStatement implements IStatement{
    private final String id;
    private final IType type;

    public VarDeclStatement(String id, IType type) {

        this.id = id;
        this.type = type;
    }

    public String toString() {
        return type.toString() + ' ' + id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        if (state.getSymTable().isVarDef(id))
            throw new MyException("variable is already declared");

        if (type.equals(new IntType()))
            state.getSymTable().put(id, new IntType().defaultValue());
        else if (type.equals(new StringType()))
            state.getSymTable().put(id, new StringType().defaultValue());
        else if(type instanceof ReferenceType)
            state.getSymTable().put(id, new ReferenceType(((ReferenceType)type).getInner()).defaultValue());
        else state.getSymTable().put(id, new BoolType().defaultValue());

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new VarDeclStatement(id, type.deepcopy());
    }

    @Override
    public ITypeEnv<String,IType> typecheck(ITypeEnv<String,IType> typeEnv) throws MyException {

        typeEnv.put(id,type);
        return typeEnv;
    }
}
