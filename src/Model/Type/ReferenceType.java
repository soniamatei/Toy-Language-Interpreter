package Model.Type;

import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class ReferenceType implements IType{
    private final IType inner;
    public ReferenceType(IType inner) { this.inner=inner; }

    public IType getInner() { return inner; }

    public boolean equals(Object another){

        if (another instanceof ReferenceType)
            return inner.equals(((ReferenceType) another).getInner());
        else
            return false;
    }
    public String toString() { return "Ref(" +inner.toString()+")";}

    @Override
    public IType deepcopy() {
        return new ReferenceType(inner.deepcopy());
    }

    public IValue defaultValue() { return new ReferenceValue(0,inner); }
}
