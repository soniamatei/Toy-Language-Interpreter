package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Exception.MyException;
import Model.Value.ReferenceValue;

public class HeapReadExpression implements IExpression{
    private IExpression ex;
    public HeapReadExpression(IExpression ex) {
        this.ex = ex;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, IHeap<Integer,IValue> hp) throws MyException {

        IValue res = ex.eval(tbl,hp);

        if(!(res instanceof ReferenceValue))
            throw new MyException("value of expr " + ex + " is not refvalue");
        if(!hp.isVarDef(((ReferenceValue)res).getAddr()))
            throw new MyException("the result of exp " + ex+ " has not a key in heap");

        return hp.lookUp(((ReferenceValue)res).getAddr());
    }

    @Override
    public IExpression deepcopy() {
        return new HeapReadExpression(ex.deepcopy());
    }

    public String toString() { return "WriteHeap(" + ex + ")"; }

    @Override
    public IType typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        IType typ=ex.typecheck(typeEnv);

        if (typ instanceof ReferenceType reft) {
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }
}
