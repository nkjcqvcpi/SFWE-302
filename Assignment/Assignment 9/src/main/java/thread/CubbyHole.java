package thread;

class CubbyHole {
    private final int[] buffer = new int[10];
    private int count = 0;
    private int putIndex = 0;
    private int getIndex = 0;

    public synchronized void put(int value) {
        while (count == buffer.length) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        buffer[putIndex] = value;
        putIndex = (putIndex + 1) % buffer.length;
        count++;
        notifyAll();
    }

    public synchronized int get() {
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        int value = buffer[getIndex];
        getIndex = (getIndex + 1) % buffer.length;
        count--;
        notifyAll();
        return value;
    }
}