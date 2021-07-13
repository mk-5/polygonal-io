package io.polygonal.verifytask;

import com.google.inject.AbstractModule;
import io.polygonal.Language;
import io.polygonal.verifytask.dto.ProjectLanguage;
import io.polygonal.verifytask.parsers.ParsersModule;
import io.polygonal.verifytask.ports.PackagesVerifier;
import io.polygonal.verifytask.verifiers.VerifiersModule;

public class VerifyTaskModule extends AbstractModule {
    private final Language language;

    public VerifyTaskModule(Language language) {
        this.language = language;
    }

    @Override
    protected void configure() {
        bind(ProjectLanguage.class).toInstance(new ProjectLanguage(language));
        bind(PackagesVerifier.class).to(RecursivePackagesVerifier.class);
        install(new ParsersModule());
        install(new VerifiersModule());
    }
}
