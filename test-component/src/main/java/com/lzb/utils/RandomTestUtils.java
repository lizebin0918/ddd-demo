package com.lzb.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Number;
import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

@UtilityClass
public class RandomTestUtils {
    private static final Faker FAKER = new Faker();
    private static final Address ADDRESS = FAKER.address();
    private static final Number NUMBER = FAKER.number();

    private static EasyRandom easyRandom() {
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .seed(1L)
                .stringLengthRange(5, 10)
                .collectionSizeRange(3, 5)
                .dateRange(LocalDate.now().minusMonths(3), LocalDate.now().plusYears(1))
                .timeRange(LocalTime.MIN, LocalTime.MIN);
        return new EasyRandom(easyRandomParameters);
    }

    public static <T> T random(Class<T> clazz) {
        return easyRandom().nextObject(clazz);
    }

    public static <T> List<T> randomList(Class<T> clazz, int size) {
        return IntStream.range(0, size)
                .mapToObj(x -> random(clazz))
                .toList();
    }

    public static <T> List<T> randomList(Class<T> clazz) {
        return randomList(clazz, NUMBER.numberBetween(3,10));
    }
}
