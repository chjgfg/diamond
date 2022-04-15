package org.lyf.diamond.frame.execute;

public interface Base<T> {
  public T build(String name);

  public T of();

}
