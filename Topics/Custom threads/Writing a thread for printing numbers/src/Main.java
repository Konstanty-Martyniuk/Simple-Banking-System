import java.util.stream.IntStream;

class NumbersThread extends Thread {
    int to;
    int from;
    @Override

    public void run() {
        IntStream.rangeClosed(from, to).forEach(System.out::println);
    }

    public NumbersThread(int from, int to) {
        this.from = from;
        this.to = to;
    }

    // you should override some method here                                                   
}