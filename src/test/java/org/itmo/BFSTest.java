package org.itmo;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BFSTest {

    @Test
    public void bfsTest() throws IOException {
        int[] sizes = new int[]{10, 100, 1000, 10_000, 10_000, 50_000, 100_000, 1_000_000, 2_000_000};
        int[] connections = new int[]{50, 500, 5000, 50_000, 100_000, 1_000_000, 1_000_000, 10_000_000, 10_000_000};
        Random r = new Random(42);
        try (FileWriter fw = new FileWriter("tmp/results.txt")) {
            for (int i = 0; i < sizes.length; i++) {
                System.out.println("--------------------------");
                System.out.println("Generating graph of size " + sizes[i] + " ...wait");
                Graph g = new RandomGraphGenerator().generateGraph(r, sizes[i], connections[i]);
                System.out.println("Generation completed!\nStarting bfs");
                long serialTime = executeSerialBfsAndGetTime(g);
                long parallelTime = executeParallelBfsAndGetTime(g);
                fw.append("Times for " + sizes[i] + " vertices and " + connections[i] + " connections: ");
                fw.append("\nSerial: " + serialTime);
                fw.append("\nParallel: " + parallelTime);
                fw.append("\n--------\n");
            }
            fw.flush();
        }
    }


    private long executeSerialBfsAndGetTime(Graph g) {
        long startTime = System.currentTimeMillis();
        g.bfs(0);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private long executeParallelBfsAndGetTime(Graph g) {
        long startTime = System.currentTimeMillis();
        g.parallelBFS(0);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    @Test
    public void fuzz() {
        Random r = new Random(42);
        for (int iter_ = 0; iter_ < 1000; iter_++) {
            final int size = 100 + r.nextInt(1000);
            final int connections = size - 1 + r.nextInt(Math.max(size * size / 2 - 2 * size, 0));
            System.out.println("--------------------------");
            System.out.println("Generating graph of size " + size + " ...wait");
            Graph g = new RandomGraphGenerator().generateGraph(r, size, connections);
            System.out.println("Generation completed!\nStarting bfs");
            final int startVertex = 0;
            final boolean[] expected = g.bfs(startVertex);
            final boolean[] actual = g.parallelBFS(startVertex).toArray();
            if (!Arrays.equals(expected, actual)) {
                System.err.println(g);
                System.err.println(Arrays.toString(expected));
                for (int i = 0; i < size; i++) {
                    if (expected[i] != actual[i]) {
                        System.err.println(i + ": " + expected[i] + " -> " + actual[i]);
                    }
                }
                throw new RuntimeException();
            }
        }
    }
}
