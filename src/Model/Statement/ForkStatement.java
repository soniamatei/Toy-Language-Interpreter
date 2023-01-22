package Model.Statement;

import Exception.MyException;
import Model.ADT.*;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement{

    IStatement stm;

    public ForkStatement(IStatement stm) {
        this.stm = stm;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIList<IValue> out = state.getOut();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, IValue> heap = state.getHeap();
        MyIStack<IStatement> exeStack = new MyStack<IStatement>();

        return new ProgramState(exeStack, symTable.deepcopy(), out, fileTable, heap, stm);
    }

    @Override
    public IStatement deepcopy() {
        return new ForkStatement(stm.deepcopy());
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        stm.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    public String toString() {return "fork: " + stm.toString();}
}
