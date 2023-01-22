package Repository;

import Model.ProgramState;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import Exception.MyException;

public class Repository implements IRepository{

    private String logFilePath;
    private List<ProgramState> allPrograms;

    public Repository(ProgramState programState, String logFilePath) {

        this.logFilePath = logFilePath;
        allPrograms = new LinkedList<>();
        allPrograms.add(programState);

    }

    @Override
    public void logPrgStateExec(ProgramState ps)  throws MyException {

        try {
            PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(ps.toString());
            logFile.write("\n");
            logFile.flush();
        }
        catch (IOException e) {
            throw new MyException("file path cannot be empty");
        }
    }

    public List<ProgramState> getPrgList(){
        return allPrograms;
    }

    public void setPrgList(List<ProgramState> new_list) {
        allPrograms = new_list;
    }


}
