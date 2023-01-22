package Model.ADT;

import java.util.List;

public interface MyIList<T> {
    void add(T elem);
    List<T> getContent();
}
