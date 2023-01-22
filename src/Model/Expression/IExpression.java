package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Exception.MyException;
import Model.Type.IType;
import Model.Value.IValue;

public interface IExpression {
    IValue eval(MyIDictionary<String,IValue> tbl,  IHeap<Integer,IValue> hp) throws MyException;
    IExpression deepcopy();
    IType typecheck(ITypeEnv<String,IType> typeEnv) throws MyException;
}
