import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayLinkedList<E> implements MyList<E> {
    private Node<E> head, tail;
    private int size;

    public TwoWayLinkedList() {
    }

    public TwoWayLinkedList(E[] objects) {
        for (E object : objects) {
            add(object);
        }
    }

    @Override
    public void add(E e) {
        addLast(e);
    }

    @Override
    public void add(int index, E e) {
        if (index == 0) {
            addFirst(e);
        } else if (index >= size) {
            addLast(e);
        } else {
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<>(e);
            current.next.previous = current;
            current.next.next = temp;
            temp.previous = current.next;
            size++;
        }
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        if (head != null) {
            head.previous = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return head.element;
        } else if (index == size - 1) {
            return tail.element;
        } else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.element;
        }
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<E> previous = head;
            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }
            Node<E> current = previous.next;
            previous.next = current.next;
            current.next.previous = previous;
            size--;
            return current.element;
        }
    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node<E> temp = head;
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
            size--;
            if (head == null) {
                tail = null;
            }
            return temp.element;
        }
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            Node<E> temp = head;
            head = tail = null;
            size = 0;
            return temp.element;
        } else {
            Node<E> current = head;
            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }
            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E e) {
        for (Node<E> current = head; current != null; current = current.next) {
            if (e.equals(current.element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(E e) {
        int index = 0;
        for (Node<E> current = head; current != null; current = current.next) {
            if (e.equals(current.element)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        int lastIndex = -1;
        int index = 0;
        for (Node<E> current = head; current != null; current = current.next) {
            if (e.equals(current.element)) {
                lastIndex = index;
            }
            index++;
        }
        return lastIndex;
    }

    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            E oldValue = current.element;
            current.element = e;
            return oldValue;
        }
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new LinkedListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index);
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E element) {
            this.element = element;
        }
    }

    private class LinkedListIterator implements ListIterator<E> {
        private Node<E> current = head;
        private int index = 0;

        public LinkedListIterator() {
        }

        public LinkedListIterator(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Invalid index");
            }
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = current.element;
            current = current.next;
            index++;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (current == null) {
                current = tail;
            }
