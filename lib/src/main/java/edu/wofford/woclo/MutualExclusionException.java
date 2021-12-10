package edu.wofford.woclo;

import java.util.*;

public class MutualExclusionException extends RuntimeException {
  private List<String> mutexc;

  public MutualExclusionException(List<String> mutexc) {
    List<String> cloned = new ArrayList<String>();
    for (int i = 0; i < mutexc.size(); i++) {
      cloned.add(mutexc.get(i));
    }
    this.mutexc = cloned;
  }

  public List<String> getMutuallyExcList() {
    List<String> cloned = new ArrayList<String>();
    for (int i = 0; i < mutexc.size(); i++) {
      cloned.add(mutexc.get(i));
    }
    return cloned;
  }
}
