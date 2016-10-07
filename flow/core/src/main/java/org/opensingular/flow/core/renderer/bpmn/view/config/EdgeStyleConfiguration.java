/****************************************************************************
 **
 ** This demo file is part of yFiles for Java 3.0.0.1.
 **
 ** Copyright (c) 2000-2016 by yWorks GmbH, Vor dem Kreuzberg 28,
 ** 72070 Tuebingen, Germany. All rights reserved.
 **
 ** yFiles demo files exhibit yFiles for Java functionalities. Any redistribution
 ** of demo files in source code or binary form, with or without
 ** modification, is not permitted.
 **
 ** Owners of a valid software license for a yFiles for Java version that this
 ** demo is shipped with are allowed to use the demo source code as basis
 ** for their own yFiles for Java powered applications. Use of such programs is
 ** governed by the rights and conditions as set out in the yFiles for Java
 ** license agreement.
 **
 ** THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESS OR IMPLIED
 ** WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 ** MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
 ** NO EVENT SHALL yWorks BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 ** SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 ** TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 ** PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 ** LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 ** NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 ** SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **
 ***************************************************************************/
package org.opensingular.flow.core.renderer.bpmn.view.config;

import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.styles.IEdgeStyle;
import com.yworks.yfiles.view.GraphComponent;

/**
 * Abstract base class for configurations that can be displayed in an option editor.
 *
 * This is only needed for the sample application to provide an easy way to configure the option pane. Customer applications
 * will likely provide their own property configuration framework and won't need this part of the library
 */
public abstract class EdgeStyleConfiguration<TStyle extends IEdgeStyle> {
  /**
   * Applies this configuration to the given {@code item} in a {@link GraphComponent}.
   * <p>
   * This is the main method of this class.
   * </p>
   * @param graphControl The {@code GraphControl} to apply the configuration on.
   * @param item The item to change.
   */
  public void apply( GraphComponent graphControl, IEdge item ) {
    graphControl.getGraph().setStyle(item, getStyleTemplate());
    graphControl.getGraphModelManager().updateDescriptor(item);
  }

  private TStyle styleTemplate;

  public final TStyle getStyleTemplate() {
    if (styleTemplate == null) {
      styleTemplate = createDefault();
    }
    return styleTemplate;
  }

  public void initializeFromExistingStyle( TStyle item ) {
    styleTemplate = (TStyle)item.clone();
  }

  protected abstract TStyle createDefault();
}