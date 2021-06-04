import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

public class NearestPercentileCalc implements PercentileCalculator {

    @Override
    public List<Double> calculate(List<Integer> numbers) {
        List<Double> percentiles = new ArrayList<>();
        int n = numbers.size();
        Collections.sort(numbers);

        for (int p = 10; p <= 90; p+=10) {
            double n_p = (double)n * p / 100.0;
            int index = (int) Math.ceil(n_p);
            double percentile = numbers.get(index-1);
            percentiles.add(percentile);
        }
        return percentiles;
    }

}
