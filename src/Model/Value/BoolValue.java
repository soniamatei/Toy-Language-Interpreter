package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

public class BoolValue implements IValue{
    private final boolean value;

    public BoolValue(boolean val) {
        value = val;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() { return String.valueOf(value); }

    @Override
    public IValue deepcopy() {
        return new BoolValue(value);
    }

    @Override
    public boolean equals(Object another) {

        if (!(another instanceof BoolValue))
            return false;
        return ((BoolValue)another).getValue() == value;
    }
}
