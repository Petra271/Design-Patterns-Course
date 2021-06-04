import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerator implements Generator {

    private double mean;
    private double dev;
    private int n;

    public RandomGenerator(double mean, double dev, int n) {
        this.mean = mean;
        this.dev = dev;
        this.n = n;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> numbers = new ArrayList<>();

        Random fRandom = new Random();
        for (int i = 0; i < n; i++)
            numbers.add(getNext(fRandom));

        return numbers;
    }

    private int getNext(Random fRandom) {
        return (int) (fRandom.nextGaussian() * this.dev + this.mean);
    }

}
