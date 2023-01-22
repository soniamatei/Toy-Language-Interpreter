package Repository;

import Model.ProgramState;
import Exception.MyException;

import java.util.List;

public interface IRepository {
    void setPrgList(List<ProgramState> new_list);
    List<ProgramState> getPrgList();
    void logPrgStateExec(ProgramState ps) throws MyException;
}
