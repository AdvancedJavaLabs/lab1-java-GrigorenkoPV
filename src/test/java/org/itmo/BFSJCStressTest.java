
package org.itmo;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.Random;

@JCStressTest
@State
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "Everything is ok")
@Outcome(id = "-2", expect = Expect.FORBIDDEN, desc = "Exception thrown")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "Test not run")
public class BFSJCStressTest {
    private int result = -1;

    @Actor
    public void actor() {
        this.result = run();
    }

    private static int run() {
        final Random r = new Random(42);
        final int startVertex = 0;
        final int vert = 1000;
        final int edges = 10_000;
        final Graph graph = new RandomGraphGenerator().generateGraph(r, vert, edges);
        final boolean[] expected = graph.bfs(startVertex);
        final boolean[] actual;
        try {
            actual = graph.parallelBFS(startVertex).toArray();
        } catch (Exception e) {
            return -2;
        }
        if (actual.length != expected.length) {
            return -3;
        }
        int result = 0;
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != expected[i]) {
                result++;
            }
        }
        return result;
    }

    @Arbiter
    public void arbiter(I_Result result) {
        result.r1 = this.result;
    }
}