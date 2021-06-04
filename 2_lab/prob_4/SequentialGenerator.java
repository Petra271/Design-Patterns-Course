import java.util.ArrayList;
import java.util.List;

public class SequentialGenerator implements Generator {

    private int lowerBound;
    private int upperBound;
    private int step;

    public SequentialGenerator(int lower, int upper, int step) {
        this.lowerBound = lower;
        this.upperBound = upper;
        this.step = step;
    }

    @Override
    public List<Integer> generate() {

        List<Integer> numbers = new ArrayList<>();
        for (int i = this.lowerBound; i < this.upperBound; i += this.step)
            numbers.add(i);

        return numbers;
    }  
}
