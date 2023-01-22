package GUI;

import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Exception.MyException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class Controller1 implements Initializable {
    @FXML
    private ListView<String> programs;
    private List<IStatement> codedPrograms;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programs.getItems().add("int a;\nint b;\na=2+3*5;\nb=a+1;\nPrint(b)");
        programs.getItems().add("bool a;\nint v;\na=true;\n(If a Then v=2 Else v=3);\nPrint(v)");
        programs.getItems().add("int v;\nv=4;\n(while (v>0) \nprint(v);\nv=v-1);\nprint(v)");
        programs.getItems().add("string varf;\nvarf=\"test.in\";\nopenRFile(varf);\nint varc;\nreadFile(varf,varc);\nprint(varc);\nreadFile(varf,varc);\nprint(varc);\ncloseRFile(varf)");
        programs.getItems().add("int v;\nRef int a;v=10;\nnew(a,22);\nfork(wH(a,30);\nv=32;\nprint(v);\nprint(rH(a)));\nprint(v);\nprint(rH(a))");
    }

    private void createPrograms() {
        IStatement program0 = new CompStatement( new VarDeclStatement("a",new IntType()),
                new CompStatement(new VarDeclStatement("b",new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithExpression(1,new ValueExpression(new IntValue(2)),new
                                ArithExpression(3,new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignStatement("b",new ArithExpression(1,new VarExpression("a"), new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VarExpression("b"))))));

        IStatement program1 = new CompStatement(new VarDeclStatement("a",new BoolType()),
            new CompStatement(new VarDeclStatement("v", new IntType()),
                    new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompStatement(new IfStatement(new VarExpression("a"),new AssignStatement("v",new ValueExpression(new
                                    IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                    VarExpression("v"))))));

        IStatement program2 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))), new CompStatement(
                new WhileStatement(new RelationalExpression(new VarExpression("v"), new ValueExpression(new IntValue(0)), 5), new CompStatement(new PrintStatement(
                        new VarExpression("v")), new AssignStatement("v", new ArithExpression(2, new VarExpression("v"), new ValueExpression(new IntValue(1)))))),
                new PrintStatement(new VarExpression("v")))));

        IStatement program3 = new CompStatement(new VarDeclStatement("varf", new StringType()), new CompStatement(new AssignStatement("varf", new ValueExpression(new StringValue("src/input.txt"))),
                new CompStatement(new OpenRFileStatement(new VarExpression("varf")), new CompStatement(new VarDeclStatement("varc", new IntType()), new CompStatement(
                        new ReadFileStatement(new VarExpression("varf"), "varc"), new CompStatement(new PrintStatement(new VarExpression("varc")), new CompStatement(
                        new ReadFileStatement(new VarExpression("varf"), "varc"), new CompStatement(new PrintStatement(new VarExpression("varc")), new CloseRFileStatement(
                        new VarExpression("varf"))))))))));

        IStatement program4 = new CompStatement(new VarDeclStatement("v",new IntType()), new CompStatement(
                        new VarDeclStatement("a",new ReferenceType(new IntType()) ),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))), new CompStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(new ForkStatement(
                                                        new CompStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompStatement(new PrintStatement(new VarExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VarExpression("a"))))))), new CompStatement(new PrintStatement(new VarExpression("v")),
                                                        new PrintStatement(new HeapReadExpression(new VarExpression("a")))))))));

        codedPrograms = new ArrayList<>();
        codedPrograms.add(program0);
        codedPrograms.add(program1);
        codedPrograms.add(program2);
        codedPrograms.add(program3);
        codedPrograms.add(program4);
        ProgramState.resetNumberOfThreads();
    }
    @FXML
    private void onListClicked(MouseEvent event) throws IOException, MyException {

        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

            int index = programs.getSelectionModel().getSelectedIndex();
            if (index == -1)
                return;

            Stage currentStage = (Stage) ((ListView<String>)event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("sample2.fxml")));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            String css = Objects.requireNonNull(getClass().getResource("app.css")).toExternalForm();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            scene.getStylesheets().add(css);
            stage.show();
            createPrograms();

            Controller2 ctrl = fxmlLoader.getController();
            ctrl.setProgram(codedPrograms.get(index), stage);
        }
    }

    @FXML
    private void onButtonCloseClicked(MouseEvent event) {
        System.exit(0);
    }
}
