package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    String toString();
    IStatement deepcopy();

    ITypeEnv<String, IType> typecheck(ITypeEnv<String,IType> typeEnv) throws MyException;
}
