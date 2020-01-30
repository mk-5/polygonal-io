#!/usr/bin/env bash
PLUGIN_LATEST_VERSION=$(curl -s https://plugins.gradle.org/m2/pl/mk5/polygonal-architecture/pl.mk5.polygonal-architecture.gradle.plugin/maven-metadata.xml | grep -Po '(?<=<version>)([0-9\.]+(-SNAPSHOT)?)' | sort --version-sort -r | head -n 1)
NEXT_VERSION=$(./gradlew -q nextVersion)
git config user.name "Travis CI"
git config user.email "builds@travis-ci.com"
git tag $NEXT_VERSION
git push https://$TRAVIS_GITHUB_KEY@github.com/mk-5/polygonal-architecture.git --tags