package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;

public class IntValue implements IValue{
    private final int value;

    public IntValue(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public IValue deepcopy() {
        return new IntValue(value);
    }

    @Override
    public boolean equals(Object another) {

       if (!(another instanceof IntValue))
           return false;
       return ((IntValue)another).getValue() == value;
    }
}
