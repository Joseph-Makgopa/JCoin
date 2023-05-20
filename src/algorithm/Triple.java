package algorithm;

import java.util.Objects;

public class Triple <T,S,U>{
    private T first;
    private S second;
    private U third;
    public Triple(T first, S second, U third){
        this.first = first;
        this.second = second;
        this.third = third;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public void setSecond(S second) {
        this.second = second;
    }
    public void setThird(U third) {
        this.third = third;
    }
    public T getFirst() {
        return first;
    }
    public S getSecond() {
        return second;
    }
    public U getThird() {
        return third;
    }
    @Override
    public String toString() {
        return "Triple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(first, triple.first) && Objects.equals(second, triple.second) && Objects.equals(third, triple.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}
