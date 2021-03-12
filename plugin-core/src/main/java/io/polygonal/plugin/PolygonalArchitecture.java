package io.polygonal.plugin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolygonalArchitecture {
    /**
     * Sources directory, required. Default value based on module language
     */
    private File sourcesDir;

    /**
     * Base package, this is a package for polygon packages
     */
    private String basePackage = "";

    /**
     * Polygon definition
     */
    private Polygon polygon;

    /**
     * Polygon template, can be used instead of {@link #polygon}
     */
    private File polygonTemplate;

    /**
     * Strict mode means that only packages from definitions are allowed
     */
    private boolean strictMode = true;
}
