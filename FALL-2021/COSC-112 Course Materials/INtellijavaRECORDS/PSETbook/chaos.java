/* 1.3.45 Chaos. Write a program to study the following simple model for population growth, which might
be applied to study fish in a pond, bacteria in a test tube, or any of a host of similar situations. We
suppose that the population ranges from 0 (extinct) to 1 (maximum population that can be sustained). If
the population at time t is x, then we suppose the population at time t + 1 to be r x (1–x), where the
argument r, known as the fecundity parameter, controls the rate of growth. Start with a small
population—say, x = 0.01—and study the result of iterating the model, for various values of r. For which
values of r does the population stabilize at x = 1 – 1/r? Can you say anything about the population when
r is 3.5? 3.8? 5? */

public class chaos {
    public static void main(String[] args) {
        double r = Double.parseDouble(args[0]);
        double population = 0.01;
        double populationStep = population;
        long t = 0;
        do {
            population = populationStep;
            populationStep = r * population * (1 - population);
            for (int i = 0; i < (int) (populationStep * 10); i++) {
                System.out.println("*");
                t++;
            }
        }
        while (populationStep > 0 && population < 1 && population != populationStep);
        System.out.println("Algorithm finished at time " + t + " with population" + population);
    }
}
