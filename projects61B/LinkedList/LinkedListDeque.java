

public class LinkedListDeque<T>  {

    private int size;
    private Link pointer;

    private class Link<T> {
        private T data;
        private Link<T> next;
        private Link<T> previous;

        private Link(T data) {
            this.data = data;
            this.next = this;
            this.previous = this;
        }
    }

    public LinkedListDeque() {
        Link<T> newlink = new Link<>(null);
        size = 0;
    }

    public boolean isEmpty() {
        // returns if the list is empty.
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void printDeque() {
        if (size == 0) {
            System.out.println("There are no items in this list.");
        }
        Link<T> leadsentinel = pointer;
        do {
            System.out.print(leadsentinel.data + " ");
            leadsentinel = leadsentinel.next;
        }
        while (leadsentinel != pointer);
    }

    public int size() {
        // returns the size of the list.
        if (size >= 0) {
            return size;
        } else {
            return -(size);
        }
    }

    public void addLast(T data) {
        // adds item to end of the list.

        Link<T> newlink = new Link<>(data);
        // new link instance.
        if (isEmpty()) {
            pointer = newlink;
            pointer.next = pointer;
            pointer.previous = pointer;
        } else {
            pointer.previous.next = newlink;
            newlink.next = pointer;
            newlink.previous = pointer.previous;
            pointer.previous = newlink;
        }
        size += 1;
    }
    public void addFirst(T data) {
        // adds item to end of the list.

        Link<T> newlink = new  Link<>(data);
        // new link instance.

        if (isEmpty()) {
            pointer = newlink;
            pointer.next = pointer;
            pointer.previous = pointer;
        } else {
            newlink.previous = pointer.previous;
            newlink.next = pointer;
            pointer.previous.next = newlink;
            pointer.previous = newlink;
            pointer = newlink;
        }
        size += 1;
    }

    public T removeFirst() {
        // removes item from front of list
        if (isEmpty()) {
            return null;
        } else {
            T datafront = (T) pointer.data;
            Link<T> last = pointer;
            pointer.next.previous = pointer.previous;
            pointer.previous.next = pointer.next;
            pointer.previous = null;
            pointer = pointer.next;
            last.next = null;
            last.previous = null;
            size -= 1;
            return (T) datafront;
        }
    }

    public T removeLast() {
        // removes item from back of list
        if (isEmpty()) {
            return null;
        } else {
            T databack = (T) pointer.previous.data;
            Link<T> last = pointer.previous;
            pointer.previous.previous.next = pointer;
            pointer.previous = pointer.previous.previous;
            last.previous = null;
            last.next = null;
            size -= 1;
            return (T) databack;
        }
    }


    public T get(int index) {
        // gets data at given index
        int count = 0;
        Link<T> getlink = pointer;
        if (index > size()) {
            return null;
        }
        do {
            if (count == index) {
                return (T) getlink.data;
            }
            count += 1;
            getlink = getlink.next;
        }
        while (getlink != pointer);

        return (T) getlink.data;
    }

    public T getRecursive(int index) {
        Link<T> getlink = pointer;
        if (index == 0) {
            return (T) getlink.data;
        }
        if (index > size()) {
            return null;
        }
        return (T) getRecursive(1, index, getlink.next);
    }
    private T getRecursive(int i, int index, Link<T> link) {
        if (i == index) {
            return link.data;
        } else {
            return getRecursive(i + 1, index, link.next);
        }
    }
}




