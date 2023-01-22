package Model.ADT;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import Exception.MyException;

public interface MyIStack<T> {
    void push(T e);
    T pop() throws MyException;
    boolean empty();
    Stack<T> getContent();
}
