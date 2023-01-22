package Model.Value;

import Model.Type.IType;

public interface IValue {
    IType getType();
    String toString();
    IValue deepcopy();
    boolean equals(Object another);
}
