package Model.ADT;

import Model.Value.IValue;

import java.util.HashMap;
import Exception.MyException;

public class Heap implements IHeap<Integer, IValue> {
    private HashMap<Integer, IValue> map;
    private Integer free;

    public Heap() {
        map = new HashMap<>();
        free = 1;
    }

    @Override
    public Integer put(IValue value) throws MyException {

        free = findFree();
        if(free == 0)
            throw new MyException(free + " not allowed as key");
        map.put(free, value);
        return free;
    }

    @Override
    public boolean isVarDef(Integer key) {
        return map.containsKey(key);
    }

    @Override
    public void update(Integer key, IValue value) {
        map.put(key, value);
    }

    @Override
    public IValue lookUp(Integer key) {
        return map.get(key);
    }

    public String toString() {
        return map.toString();
    }

    private Integer findFree() {

        while (map.containsKey(free)) {
            free++;
        }
        return free;
    }

    public void setContent(HashMap<Integer, IValue> map) {
        this.map = map;
    }

    public HashMap<Integer, IValue> getContent() {
        return map;
    }
}