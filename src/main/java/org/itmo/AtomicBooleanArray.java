package org.itmo;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class AtomicBooleanArray {
    private final AtomicIntegerArray inner;

    public AtomicBooleanArray(int size) {
        inner = new AtomicIntegerArray(size);
    }

    public boolean getAndSet(int i, boolean value) {
        return inner.getAndSet(i, value ? 1 : 0) != 0;
    }

    public boolean[] toArray() {
        final boolean[] result = new boolean[inner.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = inner.get(i) != 0;
        }
        return result;
    }
}
