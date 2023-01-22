package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;

public class NOpStatement implements IStatement{
    private final IStatement s;

    public NOpStatement(IStatement s) { this.s = s; }

    public String toString() {
        return s.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new NOpStatement(s);
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
