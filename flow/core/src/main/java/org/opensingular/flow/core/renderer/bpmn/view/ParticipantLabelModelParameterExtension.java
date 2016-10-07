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
import com.yworks.yfiles.graph.ILookup;
import com.yworks.yfiles.graph.labelmodels.ILabelModelParameter;
import com.yworks.yfiles.graphml.MarkupExtension;
import com.yworks.yfiles.graphml.MarkupExtensionReturnType;

@MarkupExtensionReturnType(ILabelModelParameter.class)
@Obfuscation(stripAfterObfuscation = false, exclude = true, applyToMembers = true)
public class ParticipantLabelModelParameterExtension extends MarkupExtension {
  private int index;

  /**
   * @return The Index.
   * @see #setIndex(int)
   */
  public final int getIndex() {
    return this.index;
  }

  /**
   * @param value The Index to set.
   * @see #getIndex()
   */
  public final void setIndex( int value ) {
    this.index = value;
  }

  private boolean top;

  /**
   * @return The Top.
   * @see #setTop(boolean)
   */
  public final boolean isTop() {
    return this.top;
  }

  /**
   * @param value The Top to set.
   * @see #isTop()
   */
  public final void setTop( boolean value ) {
    this.top = value;
  }


  @Override
  public Object provideValue( ILookup serviceProvider ) {
    return ChoreographyLabelModel.INSTANCE.createParticipantParameter(isTop(), getIndex());
  }
}
