package Model.ADT;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import Exception.MyException;

public class MyStack<T> implements MyIStack<T> {
    private final Stack<T> stack;
    public MyStack() {
        stack = new Stack<T>();
    }
    @Override
    public void push(T e) {
        stack.push(e);
    }
    @Override
    public T pop() throws MyException {

        // verify the stack is empty and then throw exception
        if (stack.empty())
            throw new MyException("Empty stack");

        return stack.pop();
    }
    @Override
    public boolean empty() {
        return stack.empty();
    }
    public String toString(){
        return stack.toString();
    }
    public Stack<T> getContent() { return stack; }
}
