package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import org.gradle.api.*
import org.gradle.api.provider.Provider
import org.gradle.api.specs.Spec
import pl.mk5.polygonal.polygons.PackageDefExtension

@PackageScope
class NamedDomainObjectContainerMock implements NamedDomainObjectContainer {

    private final List<PackageDefExtension> list

    NamedDomainObjectContainerMock(List<PackageDefExtension> list) {
        this.list = list;
    }

    List<PackageDefExtension> asList() {
        return list
    }

    @Override
    PackageDefExtension create(String name) throws InvalidUserDataException {
        return null
    }

    @Override
    PackageDefExtension maybeCreate(String name) {
        return null
    }

    @Override
    PackageDefExtension create(String name, Closure configureClosure) throws InvalidUserDataException {
        return null
    }

    @Override
    Object create(String name, Action configureAction) throws InvalidUserDataException {
        return null
    }

    @Override
    NamedDomainObjectContainer<PackageDefExtension> configure(Closure configureClosure) {
        return null
    }

    NamedDomainObjectProvider register(String name, Action configurationAction) throws InvalidUserDataException {
        return null
    }

    @Override
    NamedDomainObjectProvider<PackageDefExtension> register(String name) throws InvalidUserDataException {
        return null
    }

    void addLater(Provider provider) {

    }

    @Override
    DomainObjectCollection<PackageDefExtension> withType(Class type, Action configureAction) {
        return null
    }

    void addAllLater(Provider provider) {

    }

    @Override
    NamedDomainObjectSet<PackageDefExtension> withType(Class type) {
        return null
    }

    NamedDomainObjectSet matching(Spec spec) {
        return null
    }

    @Override
    int size() {
        return list.size()
    }

    @Override
    boolean isEmpty() {
        return false
    }

    @Override
    boolean contains(Object o) {
        return false
    }

    @Override
    Iterator<PackageDefExtension> iterator() {
        return null
    }

    @Override
    Object[] toArray() {
        return new Object[0]
    }

    @Override
    Object[] toArray(Object[] a) {
        return null
    }

    boolean add(PackageDefExtension e) {
        return false
    }

    @Override
    boolean remove(Object o) {
        return false
    }

    @Override
    boolean containsAll(Collection<?> c) {
        return false
    }

    @Override
    boolean removeAll(Collection<?> c) {
        return false
    }

    @Override
    boolean retainAll(Collection<?> c) {
        return false
    }

    @Override
    void clear() {

    }

    @Override
    boolean add(Object e) {
        return false
    }

    @Override
    boolean addAll(Collection c) {
        return false
    }

    @Override
    Namer<PackageDefExtension> getNamer() {
        return null
    }

    @Override
    SortedMap<String, PackageDefExtension> getAsMap() {
        return null
    }

    @Override
    SortedSet<String> getNames() {
        return null
    }

    @Override
    PackageDefExtension findByName(String name) {
        return null
    }

    @Override
    PackageDefExtension getByName(String name) throws UnknownDomainObjectException {
        return null
    }

    @Override
    PackageDefExtension getByName(String name, Closure configureClosure) throws UnknownDomainObjectException {
        return null
    }

    @Override
    Object getByName(String name, Action configureAction) throws UnknownDomainObjectException {
        return null
    }

    @Override
    PackageDefExtension getAt(String name) throws UnknownDomainObjectException {
        return null
    }

    @Override
    Rule addRule(Rule rule) {
        return null
    }

    @Override
    Rule addRule(String description, Closure ruleAction) {
        return null
    }

    @Override
    Rule addRule(String description, Action<String> ruleAction) {
        return null
    }

    @Override
    List<Rule> getRules() {
        return null
    }

    @Override
    DomainObjectCollection<PackageDefExtension> withType(Class type, Closure configureClosure) {
        return null
    }

    @Override
    NamedDomainObjectSet<PackageDefExtension> matching(Closure spec) {
        return null
    }

    Action whenObjectAdded(Action action) {
        return null
    }

    @Override
    void whenObjectAdded(Closure action) {

    }

    Action whenObjectRemoved(Action action) {
        return null
    }

    @Override
    void whenObjectRemoved(Closure action) {

    }

    void all(Action action) {

    }

    @Override
    void all(Closure action) {

    }

    void configureEach(Action action) {

    }

    @Override
    NamedDomainObjectProvider<PackageDefExtension> named(String name) throws UnknownDomainObjectException {
        return null
    }

    NamedDomainObjectProvider named(String name, Action configurationAction) throws UnknownDomainObjectException {
        return null
    }

    @Override
    NamedDomainObjectProvider<PackageDefExtension> named(String name, Class type) throws UnknownDomainObjectException {
        return null
    }

    @Override
    NamedDomainObjectCollectionSchema getCollectionSchema() {
        return null
    }

    @Override
    NamedDomainObjectProvider<PackageDefExtension> named(String name, Class type, Action configurationAction) throws UnknownDomainObjectException {
        return null
    }

    @Override
    Set<PackageDefExtension> findAll(Closure spec) {
        return null
    }
}
