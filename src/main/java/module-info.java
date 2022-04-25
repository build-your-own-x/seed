module com.techzealot.seed {
    requires java.base;
    requires java.desktop;
    requires static lombok;
    requires com.google.common;
    requires org.apache.commons.lang3;

    exports com.techzealot.designpatterns.behavioral.observer.guava;
}