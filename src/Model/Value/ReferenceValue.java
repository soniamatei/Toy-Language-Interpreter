package Model.Value;

import Model.Type.IType;

import Model.Type.ReferenceType;

public class ReferenceValue implements IValue{
    private final int address;
    private final IType locationType;
    public ReferenceValue(int address, IType locationType) {

        this.address = address;
        this.locationType = locationType;
    }
    public int getAddr() { return address; }

    public IType getType() { return new ReferenceType(locationType); }

    public String toString() { return "RefValue(" + address + ", " + locationType + ")"; }

    @Override
    public IValue deepcopy() {
        return new ReferenceValue(address, locationType.deepcopy());
    }

    public boolean equals(Object another) {

        if (!(another instanceof ReferenceValue))
            return false;
        return ((ReferenceValue) another).getAddr() == address && ((ReferenceValue) another).locationType.equals(locationType);
    }
}
