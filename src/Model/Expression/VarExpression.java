package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.MyException;

public class VarExpression implements IExpression{
    private final String id;

    public VarExpression(String id) { this.id = id; }

    public String toString() { return id; }

    @Override
    public IExpression deepcopy() {
        return new VarExpression(id);
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl,  IHeap<Integer,IValue> hp) throws MyException {
        return tbl.lookUp(id);
    }

    @Override
    public IType typecheck(ITypeEnv<String, IType> typeEnv) throws MyException { return typeEnv.lookUp(id); }
}
