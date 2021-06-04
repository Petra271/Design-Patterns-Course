import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator implements Generator {

    private int n;

    public FibonacciGenerator(int n) {
        this.n = n;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> numbers = new ArrayList<>();

        int firstNum = 1;
        int secondNum = 1;

        for (int i = 0; i < n; i++) {
            int tmp = firstNum;
            firstNum = secondNum;
            secondNum = firstNum + tmp;
            numbers.add(tmp);
        }

        return numbers;
    }

}
