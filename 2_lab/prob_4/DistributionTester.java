import java.util.List;

public class DistributionTester {

    Generator generator;
    PercentileCalculator calculator;

    public DistributionTester(Generator generator, PercentileCalculator calculator){
        this.generator = generator;
        this.calculator = calculator;
    }

    public List<Integer> generate(){
        return this.generator.generate();
    }

    public List<Double> calculate(List<Integer> numbers){
        return this.calculator.calculate(numbers);
    }


}