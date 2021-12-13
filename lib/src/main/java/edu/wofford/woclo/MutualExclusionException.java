package edu.wofford.woclo;

import java.util.*;

/**
 * The MutualExclusionException is thrown when the user gives two or more arguments in the same mutually
 * exclusive group.
 */
public class MutualExclusionException extends RuntimeException {
  private List<String> mutexc;

  /**
   * The constructor takes a group of mutually exclusive arguments.
   * @param mutexc a group of mutually exclusive arguments
   */
  public MutualExclusionException(List<String> mutexc) {
    List<String> cloned = new ArrayList<String>();
    for (int i = 0; i < mutexc.size(); i++) {
      cloned.add(mutexc.get(i));
    }
    this.mutexc = cloned;
  }

  /**
   * Returns the group of mutually exclusive arguments.
   * @return the group of mutually exclusive arguments
   */
  public List<String> getMutuallyExcList() {
    List<String> cloned = new ArrayList<String>();
    for (int i = 0; i < mutexc.size(); i++) {
      cloned.add(mutexc.get(i));
    }
    return cloned;
  }
}
