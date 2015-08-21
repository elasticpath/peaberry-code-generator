[![Build Status](https://api.travis-ci.org/elasticpath/peaberry-code-generator.png)](https://travis-ci.org/elasticpath/peaberry-code-generator)

# Elastic Path's Peaberry Code Generator
The Peaberry Code Generator is a Java Annotation Processor that auto-generates Peaberry code for OSGI service export and imports at
compile time. Its aim is to simplify the use of the following libraries:
 * [Sisu](https://www.eclipse.org/sisu/) - to auto bootstrap the dependency injection using Guice in each module based on the JSR-330 @Named and @Inject annotations present
 * [Peaberry](https://github.com/ops4j/peaberry) - to manage the exporting and importing of services to the OSGI registry

The supplied annotations can be used to either export a service to the OSGI Service registry, consume a service from the registry, or both.

It is not necessary to deploy this jar at runtime. Each of the annotations are marked as RetentionPolicy.SOURCE and will not be present in the
class files. However, the Peaberry classes that are generated have a runtime dependency on both the Sisu and Peaberry libraries.

## Usage
The Peaberry Code Generator is available at the Central Maven Repository. Maven users add this to your POM.

```xml
<dependency>
  <groupId>com.elasticpath</groupId>
  <artifactId>com.elasticpath.sisu.peaberry-code-generator</artifactId>
  <version>1.0</version>
</dependency>
```

### Configuration
To export a service to the OSGI registry simply add the @ServiceExport annotation to the desired class:
```
@Named
@Singleton
@ServiceExport(interfaces = WordGenerator.class)
public class TwoWordGenerator implements WordGenerator { ... }
```
To import a service from the OSGI registry simply annotation the target parameter or field:
```
@Named
@EagerSingleton
class RunOsgiTestApplication {
	@Inject
	RunOsgiTestApplication(
			@ServiceImport
			final UserGenerator userGenerator) {
```
```
@Named
@EagerSingleton
class RunOsgiTestApplication {
	@ServiceImport
    UserGenerator userGenerator
```
To import a dynamic list of services, annotate an Iterable:
```
@Named
@EagerSingleton
class RunOsgiTestApplication {
	@Inject
	RunOsgiTestApplication(
			@ServiceImport
			final Iterable<WordGenerator> wordGenerators) {
```
The test data had examples of how to configure these annotations, and the resulting code that will be generated. Test data can be found here:
`/src/test/java/org/eclipse/sisu/contrib/peaberry/testdata`

A comprehensive working example of the Peaberry Code Generator in action can be found [here](https://github
.com/elasticpath/sisu-guice-peaberry-example)

## Source
Built with Java 7 and Maven:
run `mvn clean install` to build




