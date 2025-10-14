package org.itmo;

import java.util.concurrent.atomic.AtomicIntegerArray;

public final class AtomicBooleanArray {
    private final AtomicIntegerArray inner;

    public AtomicBooleanArray(int size) {
        inner = new AtomicIntegerArray(size);
    }

    public boolean getAndSet(int i, boolean value) {
        return inner.getAndSet(i, value ? 1 : 0) != 0;
    }
}
