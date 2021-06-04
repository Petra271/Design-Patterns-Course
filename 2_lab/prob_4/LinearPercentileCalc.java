import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinearPercentileCalc implements PercentileCalculator {

    @Override
    public List<Double> calculate(List<Integer> numbers) {
        List<Double> percentiles = new ArrayList<>();
        List<Double> ranks = new ArrayList<>();
        int n = numbers.size();
        Collections.sort(numbers);

        for (int i = 1; i < n + 1; i++)
            ranks.add(100.0 * ((double) i - 0.5) / n);

        for (int i = 10; i <= 90; i += 10) {
            double p = (double) i;
            if (p < ranks.get(0)) {
                percentiles.add((double) numbers.get(0));
                continue;
            }

            if (p > ranks.get(n - 1)) {
                percentiles.add((double) numbers.get(n - 1));
                continue;
            }

            if (ranks.contains(p)) {
                percentiles.add((double) numbers.get(ranks.indexOf(p)));
                continue;
            }

            for (Double rank : ranks) {
                if (Double.compare(rank, p) > 0) {
                    int index = ranks.indexOf(rank);
                    double v_i1 = (double) numbers.get(index-1);
                    double v_i2 = (double) numbers.get(index);
                    double p_i1 = ranks.get(index-1);
                    double v_p = v_i1 + (double)n * (p - p_i1) * (v_i2 - v_i1) / 100.0;
                    percentiles.add(v_p);
                    break;
                }
            }
        }
        return percentiles;
    }
}
