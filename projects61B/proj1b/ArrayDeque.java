public class ArrayDeque<T> implements Deque<T> {

    private T[] array;
    private int size;
    private int first;
    private int last;

    public ArrayDeque() {
        // creates an empty array.
        array = (T[]) new Object[4];
        size = 0;
        first = 2;
        last = 3;
    }

    public boolean isEmpty() {
        // returns if the list is empty.
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        // returns the size of the list.
        if (size >= 0) {
            return size;
        } else {
            return -(size);
        }
    }
    @Override
    public void addLast(T item) {
        // adds item to back of array/
        if (last >= array.length) {
            last = 0;
        }
        array[last] = item;
        last = (last + 1) % array.length;
        size += 1;
        if (size == array.length) {
            resizingArray(2.0);
            first = array.length - 1;
            last = size;
        }
    }
    @Override
    public void addFirst(T item) {
        // adds item to front of array/
        if (first < 0) {
            first = array.length - 1;
        }
        array[first] = item;
        first = ((first - 1) + array.length) % array.length;
        size += 1;
        if (size == array.length) {
            // FIX THIS
            resizingArray(2.0);
            first = array.length - 1;
            last = size;
        }
    }
    @Override
    public void printDeque() {
        int count = 0;
        while (count < size - 1) {
            System.out.println(array[count]);
            count += 1;
        }
    }
    @Override
    public T get(int index) {
        if (index > (size - 1)) {
            return null;
        }
        int realfirst = (first + 1) % array.length;
        return array[(realfirst + index) % array.length];
    }
    @Override
    public T removeLast() {
        // removes last item from the array
        if (isEmpty()) {
            return null;
        }
        if (size == 0.5 * array.length) {
            resizingArray(0.5);
            first = size - 1;
            last = array.length;
        }
        T datatype = array[(last + array.length - 1) % array.length];
        array[(array.length + (last - 1)) % array.length] = null;
        last = ((last - 1) + array.length) % array.length;
        size -= 1;
        return datatype;
    }
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (size == .5 * array.length) {
            resizingArray(0.5);
            first = array.length - 1;
            last = array.length;
        }

        T datatype = array[(first + 1) % array.length];
        array[(first + 1) % array.length] = null;
        first = (first + 1) % array.length;
        size -= 1;
        return datatype;
    }
    private void resizingArray(double rfactor) {
        T[] resized = (T[]) new Object[ (int) (array.length * rfactor)];
        if (rfactor > 1) {
            for (int i = 0; i < size; i += 1) {
                resized[i] = array[(i + 1 + first) % array.length];
            }
        } else {
            for (int i = 0; i < size; i += 1) {
                resized[i] = array[(i + 1 + first) % array.length];
            }
        }
        array = resized;
    }
}
