import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {    
    private int size;
    private Node front;
    private Node back;    

    // inner class for linkedlist implementation
    private class Node
    {
        private Item value;
        private Node next;
        private Node previous;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }   

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException("No Nulls allowed!");

        Node newItem = new Node();
        newItem.value = item;
        if (isEmpty()) {
            front = newItem;
            back = newItem;
        }
        else {
            front.next = newItem;
            newItem.previous = front;
            front = newItem;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException("No Nulls allowed!");

        Node newItem = new Node();
        newItem.value = item;
        if (isEmpty()) {
            front = newItem;
            back = newItem;
        }
        else {
            back.previous = newItem;
            newItem.next = back;
            back = newItem;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (!isEmpty()) {
            size--;
            Item result = front.value;
            if (size == 0)
            {
                front = null;
                back = null;
            }
            else
            {
                front = front.previous;
                front.next = null;
            }
            return result;
        }
        else
            throw new java.util.NoSuchElementException("This is empty!");
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (!isEmpty()) {
            size--;
            Item result = back.value;
            if (size == 0) {
                front = null;
                back = null;
            }
            else {
                back = back.next;
                back.previous = null;
            }
            return result;
        }
        else
            throw new java.util.NoSuchElementException("This is empty!");
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {
            private Node current = front;
            @Override
            public boolean hasNext() {
                return current == null;
            }
            @Override
            public Item next() {
                if (!hasNext()) throw new java.util.NoSuchElementException("No more items to return!");
                Item item = current.value;
                current = current.previous;
                return item;
            }
            @Override
            public void remove() {
                throw new java.lang.UnsupportedOperationException("This is not supported!");
            }
        };
        return it;
    }

    // unit testing
    public static void main(String[] args) {
    }
}