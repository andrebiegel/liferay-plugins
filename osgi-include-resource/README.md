# OSGI Bundle with extended Bundle Classpath

It is possible to create a Fat-jar in the osgi context. The additional sources can be shadowed by extracting the jar contents and placing them in the new bundle . An other option is completly emebedding the jars in the bundle by extending the created Bundle-Classpath. This will expanf the classloaders context and much osgi issues can be gone .. imagine Service Provider Interface usages in framework jars. 

## Sources 

https://felix.apache.org/documentation/subprojects/apache-felix-maven-bundle-plugin-bnd.html
