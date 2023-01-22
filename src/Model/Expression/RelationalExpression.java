package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Exception.MyException;
import Model.Value.IntValue;

public class RelationalExpression implements IExpression{

    private final IExpression exp1;
    private final IExpression exp2;
    private final int op;

    public RelationalExpression(IExpression exp1, IExpression exp2, int op) {

        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl,  IHeap<Integer,IValue> hp) throws MyException {

        IValue v1,v2;
        v1 = exp1.eval(tbl, hp);

        if (v1.getType().equals(new IntType())) {
            v2 = exp2.eval(tbl, hp);

            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;

                int n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();

                return switch (op) {
                    case 1 -> new BoolValue(n1 < n2);
                    case 2 -> new BoolValue(n1 <= n2);
                    case 3 -> new BoolValue(n1 == n2);
                    case 4 -> new BoolValue(n1 != n2);
                    case 5 -> new BoolValue(n1 > n2);
                    case 6 -> new BoolValue(n1 >= n2);
                    default -> throw new MyException("not good operand");
                };
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public IExpression deepcopy() {
        return new RelationalExpression(exp1.deepcopy(), exp2.deepcopy(), op);
    }

    public String toString() {

        switch (op) {
            case 1 : return exp1.toString() + "<" + exp2.toString();
            case 2 : return exp1.toString() + "<=" + exp2.toString();
            case 3 : return exp1.toString() + "==" + exp2.toString();
            case 4 : return exp1.toString() + "!=" + exp2.toString();
            case 5 : return exp1.toString() + ">" + exp2.toString();
            case 6 : return exp1.toString() + ">=" + exp2.toString();
        };

        return null;
    }

    public IType typecheck(ITypeEnv<String,IType> typeEnv) throws MyException {

        IType typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
}
