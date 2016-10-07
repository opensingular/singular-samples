/*
 * Copyright (C) 2016 Singular Studios (a.k.a Atom Tecnologia) - www.opensingular.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opensingular.flow.core.renderer.bpmn.view;

import com.yworks.yfiles.annotations.Obfuscation;
import com.yworks.yfiles.graphml.GraphML;

/**
 * Specifies the type of a Choreography according to BPMN.
 * @see ChoreographyNodeStyle
 */
@Obfuscation(stripAfterObfuscation = false, exclude = true, applyToMembers = true)
public enum ChoreographyType {
  /**
   * Specifies that a Choreography is a Choreography Task according to BPMN.
   * @see ChoreographyNodeStyle
   */
  @GraphML(name = "Task")
  TASK(0),

  /**
   * Specifies that a Choreography is a Call Choreography according to BPMN.
   * @see ChoreographyNodeStyle
   */
  @GraphML(name = "Call")
  CALL(1);

  private final int value;

  ChoreographyType(final int value) {
    this.value = value;
  }

  public int value() {
    return this.value;
  }

  public static final ChoreographyType fromOrdinal( int ordinal ) {
    for (ChoreographyType current : values()) {
      if (ordinal == current.value) {
        return current;
      }
    }
    throw new IllegalArgumentException("Enum has no value " + ordinal);
  }
}
