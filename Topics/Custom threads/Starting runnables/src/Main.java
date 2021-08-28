class Starter {

    public static void startRunnables(Runnable[] runnables) {
        for (var runnable: runnables) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}