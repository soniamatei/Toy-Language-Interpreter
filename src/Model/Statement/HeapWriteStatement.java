package Model.Statement;
import Exception.MyException;
import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.HeapReadExpression;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class HeapWriteStatement implements IStatement{
    private final String var_name_heap_addr;
    private final IExpression exp;

    public HeapWriteStatement(String var_name, IExpression exp) {

        this.var_name_heap_addr = var_name;
        this.exp = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeap();

        if(!symTable.isVarDef(var_name_heap_addr))
            throw new MyException("var " + var_name_heap_addr + " is not defined in symtable");
        if(!(symTable.lookUp(var_name_heap_addr).getType() instanceof ReferenceType))
            throw new MyException("var " + var_name_heap_addr + " is not defined in symtable");
        int addr = ((ReferenceValue)symTable.lookUp(var_name_heap_addr)).getAddr();
        if(!heap.isVarDef(addr))
            throw new MyException("the addr " + addr +" from var " + var_name_heap_addr + " is not defined in heap");
        IValue res = exp.eval(symTable, heap);
        if(!res.getType().equals(((ReferenceType)(symTable.lookUp(var_name_heap_addr)).getType()).getInner()))
            throw new MyException("the type of " + exp +" doesn't match type of " + var_name_heap_addr);
        heap.update(addr, res);
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new HeapWriteStatement(var_name_heap_addr, exp.deepcopy());
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        IType typeexp = exp.typecheck(typeEnv);
        IType typevar = typeEnv.lookUp(var_name_heap_addr);

        if(!typevar.equals(new ReferenceType(typeexp)))
            throw new MyException("the type of " + exp +" doesn't match type of " + var_name_heap_addr);
        return typeEnv;
    }

    public String toString() { return "WriteHeap(" + var_name_heap_addr + ", " + exp +")";}
}
