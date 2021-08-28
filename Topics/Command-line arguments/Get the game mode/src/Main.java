class Problem {
    public static void main(String[] args) {
        String result = "default";
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0 && args[i].equals("mode")) {
                result = args[i + 1];
                break;
            }
        }
        System.out.println(result);
    }
}