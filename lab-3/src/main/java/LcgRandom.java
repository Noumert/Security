public class LcgRandom {
    public final long m = 4294967296L;
    private long _last;
    private final long a;
    private final long c;

    public LcgRandom(long first, long a, long c) {
        _last = first;
        this.a = a;
        this.c = c;
    }

    public int next() {
        _last = (a * _last + c) % m;
        return (int) _last;
    }
}