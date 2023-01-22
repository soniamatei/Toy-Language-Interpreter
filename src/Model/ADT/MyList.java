package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private List<T> out;
    public  MyList() {
        out = new ArrayList<T>();
    }
    @Override
    public void add(T elem) {
            out.add(elem);
            }

    public String toString() {
            return out.toString();
            }

    public List<T> getContent() { return out; }
}
