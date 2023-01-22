package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Exception.MyException;

public class LogicExpression implements IExpression {
    private final IExpression e1;
    private final IExpression e2;
    private final int op;

    public LogicExpression(IExpression ex1, IExpression ex2, int op) {

        e1 = ex1;
        e2 = ex2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl,  IHeap<Integer,IValue> hp) throws MyException {

        IValue v1,v2;
        v1 = e1.eval(tbl,hp);

        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl,hp);

            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;

                boolean n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();

                return switch (op) {
                    case 1 -> new BoolValue(n1 && n2);
                    case 2 -> new BoolValue(n1 || n2);
                    default -> throw new MyException("not good operand");
                };
            }else
                throw new MyException("second operand is not a boolean");
        }else
            throw new MyException("first operand is not a boolean");
    }

    public String toString() {

        String ops = switch (op) {
            case 1 -> "and";
            case 2 -> "or";
            default -> null;
        };
        return String.format("%s%s%s", e1, ops, e2);
    }

    @Override
    public IExpression deepcopy() {
        return new LogicExpression(e1.deepcopy(), e2.deepcopy(), op);
    }

    public IType typecheck(ITypeEnv<String,IType> typeEnv) throws MyException {

        IType typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);

        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not a boolean");
        }else
            throw new MyException("first operand is not a boolean");
    }
}
