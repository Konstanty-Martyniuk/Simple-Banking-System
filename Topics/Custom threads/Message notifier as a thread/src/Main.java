class MessageNotifier extends Thread {

    String message;
    int repeats;

    public MessageNotifier(String msg, int repeats) {
        this.message = msg;
        this.repeats = repeats;
    }

    @Override
    public void run() {
        System.out.println((message + "\n").repeat(repeats));
    }
}