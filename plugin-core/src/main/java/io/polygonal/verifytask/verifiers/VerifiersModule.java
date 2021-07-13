package io.polygonal.verifytask.verifiers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.polygonal.verifytask.ports.SinglePackageVerifier;

public class VerifiersModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), SinglePackageVerifier.class)
                .addBinding().to(BasicSinglePackageVerifier.class);
        Multibinder.newSetBinder(binder(), SinglePackageVerifier.class)
                .addBinding().to(KotlinSinglePackageVerifier.class);
    }
}
