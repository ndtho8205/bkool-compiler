class a {
    a(x,y: int) {}

    int sum(a, b: int) {
        return a + b;
    }

    int static mul(a, b: int) {
        return a * b;
    }
}

class b {
    void static main() {
        io.writeIntLn((new a(3, 4)).sum(1, 2));
        io.writeIntLn(a.mul(1, 2));
    }
}
