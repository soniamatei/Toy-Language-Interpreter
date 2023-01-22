package Model.Statement;

import Model.ADT.ITypeEnv;
import Model.ADT.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStatement implements IStatement{
    private final String id;
    private final IExpression exp;

    public AssignStatement(String id, IExpression exp) {

        this.exp = exp;
        this.id = id;
    }

    @Override
    public String toString(){ return id + "=" + exp.toString(); }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, IValue> symTbl = state.getSymTable();

        if ( symTbl.isVarDef(id) ) {
            IValue val = exp.eval(symTbl, state.getHeap());
            IType typId = ( symTbl.lookUp(id) ).getType();
            if ( val.getType().equals(typId) )
                symTbl.update(id, val);
            else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression "+ exp +" do not match");
        }
        else
            throw new MyException("the used variable" + id + " was not declared before");

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new AssignStatement(id, exp.deepcopy());
    }

    @Override
    public ITypeEnv<String,IType> typecheck(ITypeEnv<String,IType> typeEnv) throws MyException {

        IType typevar = typeEnv.lookUp(id);
        IType typexp = exp.typecheck(typeEnv);

        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}
