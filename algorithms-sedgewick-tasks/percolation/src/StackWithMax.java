
import java.util.Iterator;
import java.util.NoSuchElementException;


public class StackWithMax<Item extends Comparable<Item>> implements Iterable<Item> {
    private Node<Item> first;     // top of stack
    private int n;                // size of the stack
    Node<Item> max;

    // helper linked list class
    private static class Node<Item extends Comparable> {
        private Item item;
        private Node<Item> next;
    }

    public StackWithMax() {
        first = null;
        max = null;
        n = 0;
    }

    public Item getMax() {
        return max.item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;

        if(max == null
                || max.item.compareTo(first.item) < 0) {
            max = first;
        }
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        return item;                   // return the saved item
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item extends Comparable> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        StackWithMax<Double> stack = new StackWithMax<>();
        stack.push(0.);
        stack.push(5.5);
        stack.push(8.3);
        stack.push(3.);
        stack.push(10.0);
        stack.push(7.);
        System.out.println(stack.getMax());

    }
}