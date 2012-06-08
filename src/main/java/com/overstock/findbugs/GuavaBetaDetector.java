package com.overstock.findbugs;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.ba.AnalysisContext;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

public class GuavaBetaDetector extends OpcodeStackDetector {
  private final static String BETA = "Lcom/google/common/annotations/Beta;";

  private BugReporter bugReporter;

  public GuavaBetaDetector(BugReporter bugReporter) {
    this.bugReporter = bugReporter;
  }

  @Override
  public void sawOpcode(int seen) {
    switch (seen) {
      case INVOKEINTERFACE:
      case INVOKESTATIC:
      case INVOKESPECIAL:
      case INVOKEVIRTUAL: {
        JavaClass javaClass = getOperandLibraryClass();
        if (javaClass != null) {
          checkJavaClass(javaClass);
          Method method = getMethod(javaClass, getNameConstantOperand(), getSigConstantOperand());
          if (method != null && isBetaAnnotated(method)) {
            bugReporter.reportBug(bugInstance("GBU_GUAVA_BETA_METHOD_USAGE").addCalledMethod(this));
          }
        }
        break;
      }
      case GETFIELD:
      case GETSTATIC: {
        JavaClass javaClass = getOperandLibraryClass();
        if (javaClass != null) {
          checkJavaClass(javaClass);
          Field field = getField(javaClass, getNameConstantOperand());
          if (field != null && isBetaAnnotated(field)) {
            bugReporter.reportBug(
              bugInstance("GBU_GUAVA_BETA_FIELD_USAGE").addReferencedField(this));
          }
        }
        break;
      }
      case LDC:
      case LDC_W:
      case LDC2_W: {
        Constant c = getConstantRefOperand();
        if (c instanceof ConstantClass) {
          JavaClass javaClass = getOperandLibraryClass();
          if (javaClass != null) {
            checkJavaClass(javaClass);
          }
        }
        break;
      }
    }
  }

  /**
   * @return the JavaClass for the current operand, provided that it is not an application class;
   * null otherwise.
   */
  private JavaClass getOperandLibraryClass() {
    try {
      final JavaClass javaClass = Repository.lookupClass(getClassConstantOperand());
      return AnalysisContext.currentAnalysisContext().isApplicationClass(javaClass)
          ? null
          : javaClass;
    }
    catch (ClassNotFoundException e) {
      bugReporter.reportMissingClass(e);
      return null;
    }
  }

  private Method getMethod(JavaClass javaClass, String name, String sig) {
    for (JavaClass currentClass = javaClass; currentClass != null; currentClass = getSuperclass(currentClass)) {
      for (Method method : currentClass.getMethods()) {
        if (name.equals(method.getName()) && sig.equals(method.getSignature())) {
          return method;
        }
      }
    }
    if (!javaClass.isAbstract()) {
      bugReporter.logError("Unable to locate method " + javaClass.getClassName() + "." + name + sig);
    }
    return null;
  }

  private JavaClass getSuperclass(JavaClass javaClass) {
    try {
      return javaClass.getSuperClass();
    }
    catch (ClassNotFoundException e) {
      bugReporter.reportMissingClass(e);
      return null;
    }
  }

  private Field getField(JavaClass javaClass, String name) {
    for (JavaClass currentClass = javaClass; currentClass != null; currentClass = getSuperclass(currentClass)) {
      for (Field field : currentClass.getFields()) {
        if (name.equals(field.getName())) {
          return field;
        }
      }
    }
    bugReporter.logError("Unable to locate field " + javaClass.getClassName() + "." + name);
    return null;
  }

  private void checkJavaClass(JavaClass javaClass) {
    if (isBetaAnnotated(javaClass.getAnnotationEntries())) {
      bugReporter.reportBug(bugInstance("GBU_GUAVA_BETA_CLASS_USAGE").addClass(javaClass));
    }
  }

  private BugInstance bugInstance(String type) {
    return new BugInstance(this, type, NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this);
  }

  private boolean isBetaAnnotated(FieldOrMethod annotated) {
      return isBetaAnnotated(annotated.getAnnotationEntries());
  }

  private boolean isBetaAnnotated(AnnotationEntry[] annotationEntries) {
    for (AnnotationEntry annotationEntry : annotationEntries) {
      if (BETA.equals(annotationEntry.getAnnotationType())) {
        return true;
      }
    }
    return false;
  }
}
