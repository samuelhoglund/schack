import java.util.Iterator;
import java.util.NoSuchElementException;

// From https://stackoverflow.com/questions/16570091/for-loop-like-python-range-function 

class Range implements Iterable<Integer> {

    private int start = 1;  // A range starting from 1
    private int limit;

    public Range(int limit) {
        this.limit = limit;
    }

    public Range(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }

    @Override
    public Iterator<Integer> iterator() {
        final int max = limit;
        return new Iterator<Integer>() {

            private int current = start;

            @Override
            public boolean hasNext() {
                return current < max;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    return current++;   
                } else {
                    throw new NoSuchElementException("Range reached the end");
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Can't remove values from a Range");
            }
        };
    }
}
