package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;

import java.util.Objects;

public class StringValue implements IValue{
    private final String value;

    public StringValue(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public IValue deepcopy() {
        return new StringValue(value);
    }

    @Override
    public boolean equals(Object another) {

        if (!(another instanceof StringValue))
            return false;
        return Objects.equals(((StringValue) another).getValue(), value);
    }
}
