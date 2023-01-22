package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType{

    @Override
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IType deepcopy() {
        return new IntType();
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
