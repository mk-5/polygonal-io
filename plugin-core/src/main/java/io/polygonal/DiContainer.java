package io.polygonal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.polygonal.verifytask.VerifyTaskModule;

public final class DiContainer {
    private static Injector injector;

    public static void initialize(Language language) {
        injector = Guice.createInjector(new VerifyTaskModule(language));
    }

    public static <T> T get(Class<T> type) {
        assert injector != null : "DiContainer not initialized.";
        return injector.getInstance(type);
    }
}
