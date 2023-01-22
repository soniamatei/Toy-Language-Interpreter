package Model.Type;

import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType {

    @Override
    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IType deepcopy() {
        return new StringType();
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }
}
