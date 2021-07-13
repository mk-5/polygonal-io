package io.polygonal.verifytask.parsers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.polygonal.verifytask.ports.PackageParser;
import io.polygonal.verifytask.ports.SourceCodeParser;

public class ParsersModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), SourceCodeParser.class)
                .addBinding().to(JavaSourceCodeParser.class);
        Multibinder.newSetBinder(binder(), SourceCodeParser.class)
                .addBinding().to(KotlinSourceCodeParser.class);
        bind(PackageParser.class).to(PackageDirectoryParser.class);
    }
}
