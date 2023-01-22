package GUI;

import Controller.Controller;
import Model.ADT.*;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import Exception.MyException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    @FXML
    private Label noThreadsLabel;
    @FXML
    private ListView<Integer> threads;
    @FXML
    private ListView<IStatement> executionStack;
    @FXML
    private ListView<StringValue> fileTableList;
    @FXML
    private ListView<IValue> outputList;
    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTable;
    @FXML
    private TableView<Map.Entry<String, IValue>> symbolTableTable;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> heapKey;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, IValue> heapValue;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symTableKey;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, IValue> symTableValue;
    @FXML
    private Button nextButton;
    @FXML
    private Button backButton;
    private MyIList<IValue> output;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, IValue> heap;
    private ProgramState programState;
    private IRepository repo;
    private Controller ctrl;
    private List<ProgramState> allPrograms;
    private Stage currentStage;
    private int noThreads;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setProgram(IStatement program, Stage currentStage) throws MyException {

        this.currentStage = currentStage;

        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        output = new MyList<>();
        fileTable = new FileTable<>();
        heap = new Heap();
        programState = new ProgramState(stack, symTable, output, fileTable, heap, program);
        repo = new Repository(programState, "file_out/out3.txt");
        ctrl = new Controller(repo);

        allPrograms = ctrl.getAllPrograms();
        noThreads = allPrograms.size();
        noThreadsLabel.setText("no. threads:    " + noThreads);

        threads.getItems().setAll(allPrograms.stream().map(ProgramState::getId).toList());

        symTableKey.setCellValueFactory(entryStringCellDataFeatures -> new ReadOnlyObjectWrapper<>(entryStringCellDataFeatures.getValue().getKey()));
        symTableValue.setCellValueFactory(entryIValueCellDataFeatures -> new ReadOnlyObjectWrapper<>(entryIValueCellDataFeatures.getValue().getValue()));
        heapKey.setCellValueFactory(entryIntegerCellDataFeatures -> new ReadOnlyObjectWrapper<>(entryIntegerCellDataFeatures.getValue().getKey()));
        heapValue.setCellValueFactory(entryIValueCellDataFeatures -> new ReadOnlyObjectWrapper<>(entryIValueCellDataFeatures.getValue().getValue()));

        nextButton.setDisable(true);
        backButton.setVisible(false);

        symbolTableTable.setSelectionModel(null);
        heapTable.setSelectionModel(null);
    }

    @FXML
    private void OnButtonBackClicked(MouseEvent event) throws Exception {

        App newApp = new App();
        newApp.start(new Stage());
        currentStage.close();
    }

    @FXML
    private void OnButtonNextClicked(MouseEvent event) throws MyException {

        ctrl.oneStepForAllProgramsGUI();

        List<ProgramState> currentAllPrograms = ctrl.getAllPrograms();

        if (!currentAllPrograms.equals(allPrograms)) {

            noThreads = ctrl.getAllPrograms().size();
            noThreadsLabel.setText("no. threads:    " + noThreads);
            allPrograms = currentAllPrograms;
            threads.getItems().setAll(allPrograms.stream().map(ProgramState::getId).toList());
        }
        if (allPrograms.size() == 0) {
            heapTable.getItems().clear();
            fileTableList.getItems().clear();
            outputList.getItems().setAll(output.getContent());
            nextButton.setDisable(true);
            backButton.setVisible(true);
        }
        else {
            updateCommonParts();
        }
        updateSpecificParts();
    }

    private void updateCommonParts() {

        heapTable.getItems().setAll(heap.getContent().entrySet());
        fileTableList.getItems().setAll(fileTable.getContent().keySet());
    }

    private void updateSpecificParts() {

        ProgramState program = getSelectedProgram();
        if (program == null) {

            executionStack.getItems().clear();
            symbolTableTable.getItems().clear();
            return;
        }

        symbolTableTable.getItems().setAll(program.getSymTable().getContent().entrySet());
        executionStack.getItems().setAll(program.getExeStack().getContent());
    }

    private ProgramState getSelectedProgram() {

        int index = threads.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            nextButton.setDisable(true);
            return null;
        }
        return allPrograms.get(index);
    }

    @FXML
    private void onThreadClicked(MouseEvent event) {

        nextButton.setDisable(false);
        updateSpecificParts();
    }

    @FXML
    private void onButtonCloseClicked(MouseEvent event) {
        System.exit(0);
    }
}
