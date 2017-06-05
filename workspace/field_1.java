class a {
    void test() {
        for (int i = 0; i <= 10; ++i) {
            if (i == 5) continue;
            if (i == 9) break;
            System.out.println(i);
            for (int j = 0; j <= 10; ++j) {
                if (j == 5) continue;
                if (j == 9) break;
                System.out.println(j);
            }
        }
    }
}

class b {
    public static void main(String[] args) {
        (new a()).test();
    }
}


