import java.util.ArrayList;

class MyStack<T> {
    private ArrayList<T> list;

    public MyStack() {
        list = new ArrayList<>();
    }

    // Push element onto stack
    public void push(T value) {
        list.add(value);
    }

    // Pop element from stack
    public T pop() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return list.remove(list.size() - 1);
    }

    // Peek top element
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return list.get(list.size() - 1);
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Get size of stack
    public int size() {
        return list.size();
    }
}
