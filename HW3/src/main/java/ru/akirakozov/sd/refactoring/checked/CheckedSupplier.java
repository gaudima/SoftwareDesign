package ru.akirakozov.sd.refactoring.checked;

import java.util.function.Supplier;

public interface CheckedSupplier<T> extends Supplier<T> {
    @Override
    default T get() {
        try {
            return getThrows();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    T getThrows() throws Exception;
}
