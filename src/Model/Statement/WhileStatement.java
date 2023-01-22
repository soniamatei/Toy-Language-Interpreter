package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class WhileStatement implements IStatement{
    private final IExpression exp;
    private final IStatement stm;

    public WhileStatement(IExpression exp, IStatement stm) {

        this.exp = exp;
        this.stm = stm;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        IHeap<Integer, IValue> heap = state.getHeap();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue res = exp.eval(symTable, heap);

        if(res.getType().equals(new BoolType())) {
            if (((BoolValue) res).getValue()) {
                state.getExeStack().push(this.deepcopy());
                state.getExeStack().push(stm);
            }
        }
        else throw new MyException("expression " + exp + " not boolean");
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new WhileStatement(exp.deepcopy(), stm.deepcopy());
    }

    @Override
    public ITypeEnv<String, IType> typecheck(ITypeEnv<String, IType> typeEnv) throws MyException {

        IType typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
                stm.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of WHILE has not the type bool");
    }

    public String toString () {
        return "While(" + exp + ") " + stm;
     }
}
