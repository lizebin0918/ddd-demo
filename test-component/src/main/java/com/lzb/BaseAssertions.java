package com.lzb;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

/**
 * <br/>
 * Created on : 2023-05-18 10:04
 * @author lizebin
 */
public interface BaseAssertions extends WithAssertions {

    default <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier) {
        return Assertions.assertDoesNotThrow(supplier);
    }

    default void assertDoesNotThrow(Executable executor) {
        Assertions.assertDoesNotThrow(executor);
    }

    default <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable) {
        return Assertions.assertThrows(expectedType, executable);
    }

}
