package Model.ADT;
import Exception.MyException;
import Model.Value.IValue;

import java.util.HashMap;

public interface IHeap<A, C> {
    Integer put(C value) throws MyException;
    boolean isVarDef(A key);
    void update(A key, C value);
    C lookUp(A key);
     void setContent(HashMap<Integer, IValue> map) ;
     HashMap<Integer, IValue> getContent() ;
}
