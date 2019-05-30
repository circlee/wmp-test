package com.circlee.wmp.common.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public enum AlphaNumericType {

    NUMBER((c) -> {return c >= 48 && c <= 57;})
    , ALPHABET((c) -> {return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);})
    ;



    Predicate<Character> matcher;

    AlphaNumericType(Predicate<Character> matcher) {
        this.matcher = matcher;
    }

    public Predicate<Character> getMatcher(){
        return this.matcher;
    }

    private static Optional<AlphaNumericType> getAlphaNumericType(final Character ch) {
        return Arrays.stream(values())
                .filter(type -> type.getMatcher().test(ch))
                .findFirst();
    }

    public static boolean isAlphaNumericType(final Character ch) {
        return getAlphaNumericType(ch).isPresent();
    }

    public static AlphaNumericType findAlphaNumericType(final Character ch) {
        return getAlphaNumericType(ch)
                .orElseThrow(() -> {return new RuntimeException("can not find Enum");});
    }

}
