class Problem {
    public static void main(String[] args) {
        int result = -1;
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("test")) {
                result = i;
            }
        }
        System.out.println(result);
    }
}