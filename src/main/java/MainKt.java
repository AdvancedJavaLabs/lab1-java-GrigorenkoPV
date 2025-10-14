import org.itmo.Graph;
import org.itmo.RandomGraphGenerator;

import java.util.Random;

public class MainKt {
    public static void main(String[] args) {
        final RandomGraphGenerator generator = new RandomGraphGenerator();
        final Random random = new Random(4);
        for (int i = 0; i < 1000; i++) {
            final Graph graph = generator.generateGraph(random, 1_000_000, 1_000_000);
            graph.parallelBFS(0);
        }
    }
}
