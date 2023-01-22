package Controller;

import Model.ADT.Heap;
import Model.ADT.IHeap;
import Model.ADT.MyIStack;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Repository.IRepository;
import Exception.MyException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private final IRepository repo;
    private ExecutorService executorService;

    public Controller (IRepository repo) throws MyException {

        this.repo = repo;
        this.repo.getPrgList().get(0).typeCheck();
    }

    public void oneStepForAllPrg(List<ProgramState> allPrograms) throws MyException {

        // run concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        // prepare the list of callables
        List<Callable<ProgramState>> callList = allPrograms.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        // start the execution of the callables
        // it returns the list of new created PrgStates (threads)
        List<ProgramState> newPrgList;
        try {
            newPrgList = executorService.invokeAll(callList). stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }
        // add the new created threads to the list of existing threads
        allPrograms.addAll(newPrgList);
        //------------------------------------------------------------------------------

        // after the execution, print the PrgState List into the log file
        allPrograms.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
        // save the current programs in the repository
        repo.setPrgList(allPrograms);
    }

    public void oneStepForAllProgramsGUI() throws MyException {
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> allPrograms = removeCompletedPrg(repo.getPrgList());

        if (allPrograms.size() == 0)
            return;

        oneStepForAllPrg(allPrograms);

        allPrograms = removeCompletedPrg(repo.getPrgList());

        IHeap<Integer, IValue> newHeap = conservativeGarbageCollector(allPrograms);
        allPrograms.forEach(program -> {program.getHeap().setContent(newHeap.getContent());});

        executorService.shutdownNow();
        repo.setPrgList(allPrograms);
    }

    public void allStep() throws MyException {

        executorService = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> allPrograms = removeCompletedPrg(repo.getPrgList());
        while(allPrograms.size() > 0){
            oneStepForAllPrg(allPrograms);
            allPrograms = removeCompletedPrg(repo.getPrgList());

            IHeap<Integer, IValue> newHeap = conservativeGarbageCollector(allPrograms);
            allPrograms.forEach(program -> {program.getHeap().setContent(newHeap.getContent());});
        }
        executorService.shutdownNow();

        // update the repository state
        repo.setPrgList(allPrograms);
    }

    private List<ProgramState> removeCompletedPrg(List<ProgramState> allPrograms) {
        return allPrograms.stream()
                .filter(p -> !p.isCompleted())
                .collect(Collectors.toList());
    }

    private IHeap<Integer, IValue> conservativeGarbageCollector(List<ProgramState> allPrograms) {
        HashMap<Integer, IValue> newMap = new HashMap<>();
        IHeap<Integer, IValue> newHeap = new Heap();

        for (ProgramState program: allPrograms)
        {
            HashMap<Integer, IValue> map = safeGarbageCollector(
            getAddrFromSymTable(program.getSymTable().getContent().values()), getAddrFromHeap(program.getHeap().getContent().values()),
            program.getHeap().getContent());

            map.forEach((key, value) -> {
                if (!newMap.containsKey(key))
                    newMap.put(key, value);
            });
        }

        newHeap.setContent(newMap);
        return newHeap;
    }

    HashMap<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer,IValue>
            heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new));}
    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<IValue> heap){
        return heap.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    public List<ProgramState> getAllPrograms() {
        return this.repo.getPrgList();
    }
}
