# Polygonal 🧱 Architecture

Plug-in which helps you implement clean architecture in your application.

[![Build Status](https://travis-ci.org/mk-5/polygonal-architecture.svg?branch=master)](https://travis-ci.org/mk-5/polygonal-architecture)

## Getting started

Because you are here I suppose that you would like to keep clean codebase architecture, wouldn't you? I'm here to help you!  
To make the magic happen, you need following gradle configuration:

``` gradle
plugins {
  id 'pl.mk5.polygonal-architecture'
}

// or 

buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "pl.mk5.polygonal:plugin-gradle:$currentVersion"
  }
}

apply plugin: "pl.mk5.polygonal-architecture"

```

🤞 - only gradle is supported right now, but there is a plan to implement maven plugin as well. The github stars are my fuel 🔥🔥🔥🔥 then I know that my work is valuable for the universe 😂

## What does polygonal mean?

Let assume that every application is built with blocks. From architecture perspective the very basic block/brick in Java app is a package, right? Now imagine that  you are going to build a wall from bricks, the only way that bricks will match with each other is that they have the same shape, and the same amount of edges. What if the same rule can be applied to the software? Haven't you seen the projects where packaging looks more like delicious spaghetti than a good peace of software? This kind of freaky packaing structure projects are really common. Everybody was there, yep? Let stop this! I'd like to show you easy way to keep your architecture clean as a baby ass.

Example: 

```
org.example
    domain1  // this is a polygon
      dto
        ImmutableDto.java
      Object1.java
    domain2 // this is also a polygon
      dto
        ImmutableDto2.java
      Object2.java
    App.java
```

Maybe you heard about "hexagonal architecture" , "ports&adapters", today I'd like to make the things a little bit more straightforward. Polygon - what is the "polygon" 🤔? To build something using polygons, every peace should match, right? Whatever polygons you choose -> they can be triangles, quarters, hexagons, doesn't meter, but they need to match with other polygons. In software development language it means that each domain package (polygon) should have the same shape as other domain packages, and the same amount of entrance/communication points (edges).

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

### Gradle DSL

 You can use following configuration to specify rules which covers above architecture:

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
 
```
#### Attributes
|DSL atribute|Description|Default|
|--|--|--|
|`sourcesDir`| Relative path to the sources. Required | `file('src/main/java')` |
|`basePackage`| The level0 / package where polygons are stored. Your polygonal architecture starts here. Required | `` |
|`strictMode`| If true, only defined packages are allowed. | `false` |
|`polygonTemplate`| Template file -> if you'd like to keep polygon definition in .yml file. Required if polygon dsl is not defined | `null` |
|`polygon`| Polygon gradle dsl configuration. Required if polygonTemplate is not defined | `null` |
|`polygon.packageDef`| The polygon package rules definition. | |
|`polygon.packageDef.name`|  The name of the package. The nested packages like `abc.defg` are allowed. | `''`, it's root level definition |
|`polygon.packageDef.required`|  If true, definied packages is required inside polygon. | `false` |
|`polygon.packageDef.publicScope`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.packageDef.packagePrivateScope`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.packageDef.protectedScope`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.packageDef.types`| What types are allowed. Possible values are `['interface', 'class', 'enum', 'abstract class']` | `['interface', 'class', 'enum', 'abstract class']` |

### YML configuration

You'd like to keep configurations in YML files? no problem. Polygon definition can be kept in yaml file as well:

```groovy
polygonalArchitecture {
  polygonTemplate = file('src/resources/polygon.yml')
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
|`polygon.public`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.packagePrivate`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.protected`| How many public scope objects are allowed. `-1` unlimited, `0` not allowed, `n` n allowed | `0` |
|`polygon.types`| What types are allowed. Possible values are `['interface', 'class', 'enum', 'abstract class']` | `['interface', 'class', 'enum', 'abstract class']` |
|`polygon.packages`| All packages definitions goes here. |

##### YML+Gradle mix
Mixing of gradle DSL, and yaml configuration is allowed. The one rules here is that gradle DSL has higher precedence than yaml, so you can define the base polygon schema in yaml, and then overwrite some rules by gradle configuration.

## Usage

All you need to check your architecture is this:

```bash
$ ./gradlew verifyPolygons
```

Your polygons would be check automatically within `check` step. 

## What's next?

- package definition inheritance
- groovy, kotlin support
- maven plug-in

## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
