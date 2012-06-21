library-detectors
=================


Library detectors is a FindBugs plugin with detectors for certain patterns which could be considered bugs in library code. It currently detects the following bugs:

    GBU_GUAVA_BETA_CLASS_USAGE Reference to a class annotated with [@Beta][Beta]
    GBU_GUAVA_BETA_FIELD_USAGE Reference to a field annotated with [@Beta][Beta]
    GBU_GUAVA_BETA_METHOD_USAGE Reference to a method annotated with [@Beta][Beta]



See [the site page][Site] for more information.

[Beta]: http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/annotations/Beta.html
[Site]: http://overstock.github.com/library-detectors/
