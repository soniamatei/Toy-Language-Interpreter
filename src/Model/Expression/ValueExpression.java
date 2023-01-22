package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.MyException;

public class ValueExpression implements IExpression{
    private final IValue e;

    public ValueExpression(IValue e) {
        this.e = e;
    }
    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl,  IHeap<Integer,IValue> hp) throws MyException { return e; }

    public String toString() {
        return String.format("%s", e);
    }

    @Override
    public IExpression deepcopy() {
        return new ValueExpression(e.deepcopy());
    }

    @Override
    public IType typecheck(ITypeEnv<String,IType> typeEnv) throws MyException { return e.getType(); }
}
