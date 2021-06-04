import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tester {
    
    public static void main(String[] args) {

        PercentileCalculator nearestCalc = new NearestPercentileCalc();
        PercentileCalculator linearCalc  = new LinearPercentileCalc();

        Generator generator = new SequentialGenerator(0, 20, 4);
        DistributionTester tester = new DistributionTester(generator, nearestCalc);
        List<Integer> numbers = generator.generate();
        print(numbers, tester.calculate(numbers));
        
        generator = new RandomGenerator(10, 5, 5);
        tester = new DistributionTester(generator, linearCalc);
        numbers = generator.generate();
        print(numbers, tester.calculate(numbers));

        generator = new FibonacciGenerator(5);
        tester = new DistributionTester(generator, linearCalc);
        numbers = generator.generate();
        print(numbers, tester.calculate(numbers));

        // samo testiranje primjera iz zadatka
        List<Integer> list = new ArrayList<>(Arrays.asList(1,10,50));
        print(list, nearestCalc.calculate(list));
        print(list, linearCalc.calculate(list));
        
    }
    
    public static void print(List<Integer> numbers, List<Double> percentiles) {

        for (Integer n : numbers)
            System.out.print(n + " ");
        System.out.println();
        for (Double p : percentiles)
            System.out.print(p + " ");
        System.out.println("\n");

    }
    
}
