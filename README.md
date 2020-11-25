# Polygonal 🧱 Architecture

Plug-in which helps you implement clean&bounded context driven architecture in your application.

[![Build Status](https://travis-ci.org/mk-5/polygonal-architecture.svg?branch=master)](https://travis-ci.org/mk-5/polygonal-architecture) [ ![Gradle Plugin](https://img.shields.io/badge/gradle%20-plugin-green) ](https://plugins.gradle.org/plugin/pl.mk5.polygonal-architecture) 
[ ![Maven Plugin](https://img.shields.io/badge/maven%20-plugin-blue) ](https://bintray.com/mk-5/maven/polygonal-architecture/_latestVersion) [ ![Bintray](https://api.bintray.com/packages/mk-5/maven/polygonal-architecture/images/download.svg) ](https://bintray.com/mk-5/maven/polygonal-architecture/_latestVersion) 

## Getting started | gradle

Because you are here I suppose that you would like to keep clean codebase architecture, wouldn't you? I'm here to help you!  
To make the magic happen, you need following gradle configuration:

``` gradle
plugins {
  id 'pl.mk5.polygonal-architecture' version 'X.Y.Z'
}

apply plugin: "pl.mk5.polygonal-architecture"

```

Next step is to [create](https://polygonal.io/#yml-polygon-definition) `polygon.yml` file, and that's it.

## Getting started | maven

The maven configuration is really straightforward. Currently, the plugin is available only via bintray repository.

```xml
<pluginRepositories>
    <pluginRepository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>bintray-mk-5-maven</id>
        <name>bintray-plugins</name>
        <url>https://dl.bintray.com/mk-5/maven</url>
    </pluginRepository>
</pluginRepositories>

<build>
    <plugins>
        <plugin>
            <groupId>pl.mk5.polygonal</groupId>
            <artifactId>polygonal-architecture-maven-plugin</artifactId>
            <version>latest</version>
            <configuration>
                <basePackage>org.example</basePackage>
            </configuration>
        </plugin>
    </plugins>
</build>
```



# What is Polygon?

It’s a **domain-level package** which can communicate (match) with other polygons only by agreed rules. It does not matter if you are going to use triangles, hexagons, octagons, pentagons -> you can chose whatever your like

## Why should I use it?

Let assume for a second that every application is built with blocks. From architecture perspective the very basic block/brick in Java app is a package, right? Now imagine that  you are going to build a wall from bricks, the only way that bricks will match with each other is that they have the same shape, and the same amount of match side edges. What if the same rule can be applied to the software? Haven't you seen the projects where packaging looks more like delicious spaghetti than a good peace of software? This kind of freaky packages structure projects are really common. Everybody was there, yep? Let stop this! I'd like to show you easy way to keep your architecture clean as a baby ass.

```
.
├── 📂 accounts  ⬅ this is a domain (polygon)
│   └── ...
|   📂 customers ⬅ this is a domain (polygon)  
│   └── ...
|   📂 api       ⬅ this is a domain (polygon)
│   └── ...
└── 📄 SpringBootApp.java
```

Maybe you heard about "hexagonal architecture" , "ports&adapters", today I'd like to make everything a little bit easier to understand. Polygon - what is the "polygon" 🤔? To build something using polygons, every peace should match. Whatever polygons you choose -> they can be triangles, quarters, hexagons, doesn't meter, but they need to match with other polygons. In software development language it means that each domain package (polygon) should have the same kind of shape as other domain packages, and the same amount of entrance/communication points (edges).

## Plug-in configuration

Let assume that we have following project structure:

```
.
├── 📂 users
|   ├── 📂 models
|   |   ├── 📄 User.java
|   |   📂 ports
|   |   ├── 📄 UserFinder.java
│   └── RepositoryUserFinder.java
|   📂 posts
|   ├── 📂 models
|   |   ├── 📄 Post.java
|   |   📂 ports
|   |   ├── 📄 PostPublisher.java
│   └── 📄 RSSPostPublisher.java
└── 📄 AppBootstrap.java
```

We decided to provide clean architecture here. What rules can be applied?
- the root domain level may contains ***only package-private classes***

- the ports domain package should contain ***only public interfaces***

- the models domain package is allowed to contain ***only public DTO objects***

To make that works you need to define gradle DSL configuration or yml one.

### YML configuration

You'd like to keep configurations in YML files? no problem. Polygon definition can be kept in yaml file. Default location is `src/main/resources/polygon.yml`, but you can customize it using following parameters:

```groovy
polygonalArchitecture {
  basePackage = 'org.example'
  sourcesDir = file('src/main/java') // default value
  polygonTemplate = file('src/main/resources/polygon.yml')  // default value
}
```

```yaml
// polygon.yml
polygon:
  public: 0
  packagePrivate: -1
  types: ['interface', 'class', 'enum']
  packages:
    models:
      required: true 
      public: -1
      packagePrivate: 0
      types: ['class']
    ports:
      public: -1
      packagePrivate: -1
      types: ['interface']
```

#### Yaml elements
|Element|Description|
|--|--|
|`polygon`| The root element. Required |
|`polygon.public`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed |
|`polygon.packagePrivate`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed |
|`polygon.protected`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed |
|`polygon.internal`| *Kotlin only.* How many internal scope object are allowed. `-1` unlimited, `0` not allowed, `n` n allowed |
|`polygon.types`| What types are allowed. Available values are `['interface', 'class', 'enum', 'abstract class', 'data class', 'open class']` |
|`polygon.packages`| All packages definitions goes here. |

### Gradle DSL

You can define your architecture also via gradle dsl:

```groovy
// build.gradle 
polygonalArchitecture {
  sourcesDir = file('src/main/java')
  basePackage = 'org.example'
  
  polygon {
    packageDef {
      publicScope = 0
      packagePrivateScope = -1
      types = ['interface', 'class', 'enum']
    }
    
    packageDef {
      name = 'models'
      required = true
      publicScope = -1
      types = ['class']
    }
    
    packageDef {
      name = 'ports'
      publicScope = -1
      types = ['interface']
    }
  }
} 
```
#### Attributes
|DSL atribute|Description|Default|
|--|--|--|
|`sourcesDir`| Relative path to the sources. Required | `file('src/main/java')` or `file('src/main/kotlin')` |
|`basePackage`| The level0 / package where polygons are stored. Your polygonal architecture starts here. Required | `` |
|`strictMode`| If true, only defined packages are allowed. | `false` |
|`polygonTemplate`| Template file -> if you'd like to keep polygon definition in .yml file. Required if polygon dsl is not defined | `null` |
|`polygon`| Polygon gradle dsl configuration. Required if polygonTemplate is not defined | `null` |
|`polygon.packageDef`| The polygon package rules definition. | |
|`polygon.packageDef.name`|  The name of the package. The nested packages like `abc.defg` are allowed. | `''`, it's root level definition |
|`polygon.packageDef.required`|  If true, definied packages is required inside polygon. | `false` |
|`polygon.packageDef.publicScope`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.packageDef.packagePrivateScope`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0 (-1 for root level)` |
|`polygon.packageDef.protectedScope`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.packageDef.internalScope`| How many internal scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.packageDef.types`| What types are allowed. Available values are `['interface', 'class', 'enum', 'abstract class', 'data class', 'open class']` | `['interface', 'class', 'enum', 'abstract class']` |


##### YML+Gradle mix
Mixing of gradle DSL, and yaml configuration is allowed. The one rules here is that gradle DSL has higher precedence than yaml, so you can define the base polygon schema in yaml, and then overwrite some rules by gradle configuration.

#### More examples 

Are you looking for more polygon examples? Here you go: [polygons gallery](https://polygonal.io/polygons-gallery)

## Usage

Polygons analyzing is plug into default build task. If you'd like to check your polygons explicitly you should use the following task:

```bash
$ ./gradlew verifyPolygons
```

In case of any problems plugin will tell you, where is the problem. Example output:

```gradle
> Task :kotlin-e2e:verifyPolygons

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':verifyPolygons'.
> 0 public scope objects are allowed in 'models' package
```



## What's next?

- package definition inheritance
- groovy language support
- improve multi-threaded processing for maven

## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
