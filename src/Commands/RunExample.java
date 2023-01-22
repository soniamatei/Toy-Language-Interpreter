package Commands;

import Controller.Controller;
import Exception.MyException;

public class RunExample extends Command{
    private final Controller ctrl;

    public RunExample(String key, String desc,Controller ctr){

        super(key, desc);
        this.ctrl=ctr;
    }

    @Override
    public void execute() {

        try{
            ctrl.allStep();
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
