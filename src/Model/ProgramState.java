package Model;

import Model.ADT.*;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;
import Exception.MyException;

import java.io.BufferedReader;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, IValue> heap;
    private IStatement originalProgram;
    private final int id;
    static private int numberOfThreads = 0;

    public ProgramState(MyIStack<IStatement> stack, MyIDictionary<String, IValue> sym_tbl, MyIList<IValue>
            out, IFileTable<StringValue, BufferedReader> fileTable,  IHeap<Integer, IValue> heap, IStatement program){
        exeStack = stack;
        symTable = sym_tbl;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        //recreate the entire original prg
        originalProgram = program.deepcopy();
        stack.push(program);
        this.id = incrementNumberOfThreads();

    }
    public void typeCheck() throws MyException { originalProgram.typecheck(new TypeEnv<>()); }

    public  Boolean isCompleted() {
        return exeStack.empty();
    }

    public int getId() { return id; }

    static synchronized private int incrementNumberOfThreads() { return numberOfThreads++; }
    static synchronized public void resetNumberOfThreads() { numberOfThreads = 0; }

    public ProgramState oneStep() throws MyException{

        if (isCompleted())
            throw new MyException("prgstate stack is empty");

        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public String toString() {
        return "Program " + id + ":\nExeStack:" + exeStack.toString() + "\nSymTable:" + symTable.toString() + "\nOut:"
                + out.toString() + "\nFileTable:" + fileTable.toString() + "\nHeap:" + heap.toString() + "\n";
    }
    public  MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }
    public IFileTable<StringValue, BufferedReader> getFileTable() { return fileTable; }
    public  IHeap<Integer, IValue> getHeap() { return heap; }

    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setFileTable(IFileTable<StringValue, BufferedReader> fileTable) { this.fileTable = fileTable; }

    public void setOut(MyIList<IValue> out) {
        this.out = out;
    }

    public void setHeap(IHeap<Integer, IValue> heap) {
        this.heap = heap;
    }
}