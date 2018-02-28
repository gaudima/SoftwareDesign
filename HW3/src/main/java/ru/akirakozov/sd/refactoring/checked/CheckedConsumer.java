package ru.akirakozov.sd.refactoring.checked;

import java.util.function.Consumer;

public interface CheckedConsumer<T> extends Consumer<T> {

    @Override
    default void accept(T t) {
        try {
            acceptThrows(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T t) throws Exception;
}
