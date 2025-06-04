import java.util.ArrayList;

public class QueueExample {
    private ArrayList<String> queue;

    public QueueExample() {
        this.queue = new ArrayList<>();
    }

    // Add an element to the queue
    public void add(String element) {
        queue.add(element);
    }

    // Remove an element from the queue
    public String remove() {
        if (queue.isEmpty()) {
            return null;
        }
        String element = queue.get(0);
        queue.remove(0);
        return element;
    }

    // Get the front element of the queue
    public String peek() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.get(0);
    }

    // Check if the queue is empty
 public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return queue.size();
    }

    public static void main(String[] args) {
        QueueExample q = new QueueExample();
        q.add("25");
        q.add("7");
        q.add("12");
        q.add("67");
        q.add("2");

        System.out.println(q.peek()); // Apple
        System.out.println(q.size()); // 3
        System.out.println(q.remove()); // Apple
        System.out.println(q.isEmpty()); // false
    }
}

