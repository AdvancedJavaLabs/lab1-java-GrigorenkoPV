import org.itmo.Graph;
import org.itmo.RandomGraphGenerator;

import java.util.Random;

public class MainKt {
    public static void main(String[] args) {
        final RandomGraphGenerator generator = new RandomGraphGenerator();
        final Random random = new Random(4);
        for (int i = 0; i < 5; i++) {
            System.err.println("Warmup " + i);
            generator.generateGraph(random, 100_000, 10_000_000);
        }
        final int[] sizes = {10, 100, 1000, 10_000, 10_000, 50_000, 100_000};
        for (final int size : sizes) {
            for (int i = 0; i <= size / 5; i += Math.max(1, size / 50)) {
                final int numEdges = i * i;
                if (numEdges < size) {
                    continue;
                }
                if (numEdges > size * (size - 1) / 2) {
                    break;
                }
                if (numEdges > 50_000_000) {
                    break;
                }
                System.out.print(size + " " + numEdges + " ");
                System.out.flush();
                final Graph graph = generator.generateGraph(random, size, numEdges);
                final long start = System.nanoTime();
                graph.parallelBFS(0);
                final long end = System.nanoTime();
                final long duration = end - start;
                System.out.println(duration);
            }
        }
    }
}
