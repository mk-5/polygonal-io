package io.polygonal.verifytask.ports;

import io.polygonal.Language;

import java.util.function.Predicate;

public interface LanguageAware extends Predicate<Language[]> {
    @Override
    boolean test(Language... languages);
}
