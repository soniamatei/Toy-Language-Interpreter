package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType{

    @Override
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public IType deepcopy() {
        return new BoolType();
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
