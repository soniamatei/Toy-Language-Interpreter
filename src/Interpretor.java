import Commands.ExitCommand;
import Commands.RunExample;
import Controller.Controller;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import Exception.MyException;
import View.TextMenu;

import java.io.BufferedReader;
import java.io.BufferedReader;

public class Interpretor {

    public static void main(String[] args) throws MyException {

//        int v;
//        Ref int a;
//        v=10;
//        new(a,22);
//        fork(wH(a,30);v=32;print(v);print(rH(a)));
//        print(v);
//        print(rH(a))
    IStatement ex9 = new CompStatement(
            new VarDeclStatement("v",new IntType()),
            new CompStatement(
                    new VarDeclStatement("a",new ReferenceType(new IntType()) ),
                    new CompStatement(
                            new AssignStatement("v", new ValueExpression(new IntValue(10))),
                            new CompStatement(
                                    new NewStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompStatement(
                                            new ForkStatement(
                                                    new CompStatement(
                                                            new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                            new CompStatement(
                                                                    new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                    new CompStatement(
                                                                            new PrintStatement(new VarExpression("v")),
                                                                            new PrintStatement(
                                                                                    new HeapReadExpression(new VarExpression("a"))
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompStatement(
                                                    new PrintStatement(new VarExpression("v")),
                                                    new PrintStatement(
                                                            new HeapReadExpression(new VarExpression("a"))
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );
    MyIStack<IStatement> stack9 = new MyStack<>();
    MyIDictionary<String, IValue> dict9 = new MyDictionary<>();
    MyIList<IValue> list9 = new MyList<>();
    IFileTable<StringValue, BufferedReader> fileTable9 = new FileTable<>();
    IHeap<Integer, IValue> heap9 = new Heap();
    ProgramState programState9 = new ProgramState(stack9, dict9, list9, fileTable9, heap9, ex9);
    IRepository repo9 = new Repository(programState9, "file_out/out3.txt");
    Controller ctrl9 = new Controller(repo9);




//        try {
//            ctrl.allStep();
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
    TextMenu menu = new TextMenu();
    menu.addCommand(new ExitCommand("0", "exit"));
    menu.addCommand(new RunExample("1",ex9.toString(),ctrl9));
    menu.show();
    }
}


