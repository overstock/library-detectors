package lib;

import com.google.common.annotations.Beta;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import fieldlib.BetaField;

public class Bug {
  public static void classReference() {
    CacheBuilder.newBuilder();
  }

  public static void methodReference() {
    Throwables.getCausalChain(new Exception("message: "));
  }

  public static void selfInstanceFieldReference() {
    System.out.println(new Bug().x);
  }

  public static void selfStaticFieldReference() {
    System.out.println(y);
  }

  public static void libInstanceFieldReference() {
    System.out.println(new BetaField().x);
  }

  public static void libStaticFieldReference() {
    System.out.println(BetaField.y);
  }

  @Beta
  private int x;
  @Beta
  private static int y;
}
