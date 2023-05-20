package algorithm;

import java.util.Objects;

public class Pair <T, S>{
    private T first;
    private S second;
    public Pair(T first, S second){
        this.first = first;
        this.second = second;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public void setSecond(S second) {
        this.second = second;
    }
    public T getFirst() {
        return first;
    }
    public S getSecond() {
        return second;
    }
    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
