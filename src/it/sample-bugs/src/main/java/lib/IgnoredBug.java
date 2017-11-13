package lib;

import com.google.common.annotations.Beta;
import beta.ignored.*;

public class IgnoredBug {
  public static void betaClassReference() {
    System.out.println(BetaClass.class);
  }

  public static void betaClassInstanceFieldReference() {
    System.out.println(new BetaClass().x);
  }

  public static void betaClassStaticFieldReference() {
    System.out.println(BetaClass.y);
  }

  public static void betaClassInstanceMethodReference() {
    System.out.println(new BetaClass().f());
  }

  public static void betaClassStaticMethodReference() {
    System.out.println(BetaClass.g());
  }

  public static void selfInstanceFieldReference() {
    System.out.println(new IgnoredBug().x);
  }

  public static void selfStaticFieldReference() {
    System.out.println(y);
  }

  public static void libInstanceBetaFieldReference() {
    System.out.println(new BetaField().x);
  }

  public static void libStaticBetaFieldReference() {
    System.out.println(BetaField.y);
  }

  public static void libInstanceBetaMethodReference() {
    System.out.println(new BetaMethod().f());
  }

  public static void libStaticBetaMethodReference() {
    System.out.println(BetaMethod.g());
  }

  @Beta
  private int x;
  @Beta
  private static int y;
}
