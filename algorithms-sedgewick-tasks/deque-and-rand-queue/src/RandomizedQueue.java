import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == a.length) {
            resize(2 * a.length);
        }
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int ri = StdRandom.uniform(n);
        Item item = a[ri];
        a[ri] = a[--n];
        a[n] = null;

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return a[StdRandom.uniform(n)];
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator<Item>(a);
    }

    private class RandomIterator<Item> implements Iterator<Item> {
        Item[] c;
        int tail;

        RandomIterator(Item[] a) {
            c = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                c[i] = a[i];
            }
            tail = n;
        }

        @Override
        public boolean hasNext() {
            return tail > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int ri = StdRandom.uniform(tail);
            Item item = c[ri];
            c[ri] = c[--tail];
            c[tail] = null;

            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("print")) {
                for (String str : queue) {
                    StdOut.print(str);
                    StdOut.print("|");
                }
                StdOut.println();
            } else if (item.equals("deq")) {
                StdOut.println(queue.dequeue());
            } else if (item.equals("smp")) {
                StdOut.println(queue.sample());
            } else if (item.startsWith("enq:")) {
                queue.enqueue(item.substring("enq:".length()));
            } else if (item.equals("size")) {
                StdOut.println(queue.size());
            } else if (item.equals("end")) {
                return;
            } else {
                StdOut.println("Unknown command");
            }
        }
    }
}
