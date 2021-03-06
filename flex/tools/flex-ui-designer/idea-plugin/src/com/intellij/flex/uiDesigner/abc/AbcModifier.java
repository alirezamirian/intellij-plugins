package com.intellij.flex.uiDesigner.abc;

import org.jetbrains.annotations.Nullable;

public interface AbcModifier {
  boolean methodTraitName(int traitKind, int kind, DataBuffer in, Encoder encoder);

  boolean slotTraitName(int name, int traitKind, DataBuffer in, Encoder encoder);

  boolean methodTrait(int traitKind, int name, DataBuffer in, int methodInfo, Encoder encoder);

  int instanceMethodTraitDelta();

  boolean isModifyConstructor();

  void assertOnInstanceEnd();

  @Nullable
  String getClassLocalName();
}
