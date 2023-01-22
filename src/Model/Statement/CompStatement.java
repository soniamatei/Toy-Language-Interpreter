package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;

public class CompStatement implements IStatement{
    private final IStatement first;
    private final IStatement second;

    public CompStatement(IStatement f, IStatement s) {

        first = f;
        second = s;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public IStatement deepcopy() {
        return new CompStatement(first.deepcopy(), second.deepcopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{

        MyIStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);

        return null;
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String,IType> typeEnv) throws MyException { return second.typecheck(first.typecheck(typeEnv)); }
}
