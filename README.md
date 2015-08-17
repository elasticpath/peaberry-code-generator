## Peaberry Code Generator
The Peaberry Code Generator is a Java Annotation Processor that auto-generates Peaberry export and import classes at compile time. The supplied
annotations can be used to either export a service to the OSGI Service registry, or to consume a service from the registry.

It is not necessary to deploy this jar at runtime. Each of the annotations are marked as RetentionPolicy.SOURCE and will not be present in the
class files. However, the Peaberry classes that are generated have a runtime dependency on both the Sisu and Peaberry libraries.





