package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class NewStatement implements IStatement{
    String var_name;
    IExpression expression;

    public NewStatement(String var_name, IExpression expression) {
        this.var_name = var_name;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeap();
        IValue res = expression.eval(state.getSymTable(), state.getHeap());

        if(!symTable.isVarDef(var_name))
            throw new MyException("variable " + var_name + " not in symTable");
        if(!(symTable.lookUp(var_name).getType() instanceof ReferenceType))
            throw new MyException("variable " + var_name + " not instance of RefType");
        if(!res.getType().equals(((ReferenceType) symTable.lookUp(var_name).getType()).getInner()))
            throw new MyException("type of " + var_name + " does not match type of " + expression);

        Integer addr = heap.put(res);
        symTable.update(var_name, new ReferenceValue(addr, res.getType()));

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new NewStatement(var_name, expression.deepcopy());
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        IType typevar = typeEnv.lookUp(var_name);
        IType typexp = expression.typecheck(typeEnv);

        if (typevar.equals(new ReferenceType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    public String toString() { return "new(" + var_name +", " +expression+")"; }
}
