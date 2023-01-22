package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Exception.MyException;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class ArithExpression implements IExpression{
    private IExpression e1;
    private IExpression e2;
    private int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExpression( int op, IExpression ex1, IExpression ex2) {

        e1 = ex1;
        e2 = ex2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl,  IHeap<Integer,IValue> hp) throws MyException {

        IValue v1,v2;
        v1 = e1.eval(tbl,hp);

        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,hp);

            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;

                int n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();

                switch (op) {
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4:
                        if (n2 == 0) throw new MyException("division by zero");
                        else return new IntValue(n1 / n2);
                    default:
                        throw new MyException("not good operand");
                }
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    public String toString() {

        String ops = switch (op) {
            case 1 -> "+";
            case 2 -> "-";
            case 3 -> "*";
            case 4 -> "/";
            default -> null;
        };
        return String.format("%s%s%s", e1, ops, e2);
    }

    @Override
    public IExpression deepcopy() {
        return new ArithExpression( op, e1.deepcopy(), e2.deepcopy());
    }

    @Override
    public IType typecheck(ITypeEnv<String,IType> typeEnv) throws MyException{

        IType typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
}
