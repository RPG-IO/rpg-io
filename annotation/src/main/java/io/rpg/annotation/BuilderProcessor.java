package io.rpg.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Requires parameterless constructor.
 */
@SupportedAnnotationTypes("io.rpg.annotation.BuilderProcessor")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class BuilderProcessor extends AbstractProcessor {
  private final int INDENTATION = 2;
  private final String INDENT = " ".repeat(INDENTATION);

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (TypeElement annotation : annotations) {
      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

      // Annotated method with one parameter && name wit "set" prefix is recognized as setter
      Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream().collect(Collectors.partitioningBy(element ->
        ((ExecutableType) element.asType()).getParameterTypes().size() == 1
            && element.getSimpleName().toString().startsWith("set"))
      );



      List<Element> annotatedSetters = annotatedMethods.get(true);
      List<Element> otherMethods = annotatedMethods.get(false);

      otherMethods.forEach(element -> {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@BuilderProperty annotation must be" +
            " applied to a method with name starting with \"set\" prefix with exactly one parameter", element);
      });

      if (annotatedElements.isEmpty()) {
        continue;
      }

      String className = ((TypeElement) annotatedSetters.get(0).getEnclosingElement()).getQualifiedName().toString();

      Map<String, String> setterMap = annotatedSetters.stream().collect(Collectors.toMap(
          setter -> setter.getSimpleName().toString(),
          setter -> ((ExecutableType) setter.asType())
              .getParameterTypes().get(0).toString()
      ));

      String packageName = null;
      int lastDotIndex = className.lastIndexOf('.');

      if (lastDotIndex > 0) {
        packageName = className.substring(0, lastDotIndex);
      }

      String simpleClassName = className.substring(lastDotIndex + 1);
      String builderClassName = className + "Builder";
      String builderSimpleClassName = builderClassName.substring(lastDotIndex + 1);

      try {
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter writer = new PrintWriter(builderFile.openWriter())) {
          if (packageName != null) {
            writer.println("package " + packageName + ';');
          }

          // begin class definition
          writer.println("public class " + builderSimpleClassName + '{');

          writer.println(INDENT + "private " + simpleClassName + " object = new " + simpleClassName + "();");

          writer.println(INDENT + "public " + simpleClassName + " build() {");
          writer.println(INDENT.repeat(2) + "return object;");
          writer.println(INDENT + "}");

          // generate setters for builder
          setterMap.forEach((methodName, argumentType) -> {
            writer.println(INDENT + " public " + builderSimpleClassName + " " + methodName + "(" + argumentType + "value) {");
            writer.println(INDENT.repeat(2) + "object." + methodName + "(value);");
            writer.println(INDENT.repeat(2) + "return this;");
            writer.println('}');
          });

          // end class definition
          writer.println('}');

        }
      } catch (IOException e) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "IOException while creating builder file");
        e.printStackTrace();
      }
    }

    return true;
  }
}
