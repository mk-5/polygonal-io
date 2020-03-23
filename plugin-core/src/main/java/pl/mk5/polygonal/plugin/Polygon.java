package pl.mk5.polygonal.plugin;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Polygon {
    private List<PackageDef> packagesDefs = new ArrayList<>();
}
