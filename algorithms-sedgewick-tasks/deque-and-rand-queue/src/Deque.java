import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;     // beginning of the deque
    private Node<Item> last;      // ending of the deque
    private int n;                // size of the stack

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) {
            oldfirst.previous = first;
        }
        if (first.next == null) {
            last = first;
        }
        n++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        if (oldlast == null) {
            first = last;
        } else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        if (first.next != null) {
            first = first.next;
            first.previous = null;
        } else {
            first = null;
            last = null;
        }

        n--;

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Item item = last.item;

        if (last.previous != null) {
            last = last.previous;
            last.next = null;
        } else {
            first = null;
            last = null;
        }

        n--;

        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
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
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("print")) {
                for (String str : deque) {
                    StdOut.print(str);
                    StdOut.print("|");
                }
                StdOut.println();
            } else if (item.equals("removeFirst")) {
                StdOut.println(deque.removeFirst());
            } else if (item.equals("removeLast")) {
                StdOut.println(deque.removeLast());
            } else if (item.startsWith("addFirst:")) {
                deque.addFirst(item.substring("addFirst:".length()));
            } else if (item.startsWith("addLast:")) {
                deque.addLast(item.substring("addLast:".length()));
            } else if (item.equals("size")) {
                StdOut.println(deque.size());
            } else if (item.equals("end")) {
                return;
            } else {
                StdOut.println("Unknown command");
            }
        }
    }
}
