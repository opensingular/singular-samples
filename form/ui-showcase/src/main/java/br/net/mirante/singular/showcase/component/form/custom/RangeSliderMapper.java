/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.showcase.component.form.custom;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import br.net.mirante.singular.form.SIComposite;
import br.net.mirante.singular.form.SInstance;
import br.net.mirante.singular.form.type.basic.SPackageBasic;
import br.net.mirante.singular.form.type.core.STypeInteger;
import br.net.mirante.singular.form.wicket.IWicketComponentMapper;
import br.net.mirante.singular.form.wicket.WicketBuildContext;
import br.net.mirante.singular.form.wicket.model.AttributeModel;
import br.net.mirante.singular.form.wicket.model.SInstanceRootModel;
import br.net.mirante.singular.form.wicket.model.SInstanceValueModel;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSControls;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSLabel;

public class RangeSliderMapper implements IWicketComponentMapper {

    private final String valorInicialPath, valorFinalPath;

    public RangeSliderMapper(STypeInteger valorInicial, STypeInteger valorFinal) {
        this.valorInicialPath = valorInicial.getNameSimple();
        this.valorFinalPath = valorFinal.getNameSimple();
    }

    @Override
    public void buildView(WicketBuildContext ctx) {

        final BSControls formGroup = createFormGroup(ctx);
        final SIComposite rootInstance = ctx.getCurrentInstance();

        final IModel<? extends SInstance> miInicial = resolveModel(rootInstance, valorInicialPath);
        final IModel<? extends SInstance> miFinal = resolveModel(rootInstance, valorFinalPath);

        final HiddenField valorInicial = new HiddenField<>("valorInicial", miInicial);
        final HiddenField valorFinal = new HiddenField<>("valorFinal", miFinal);

        final Boolean disable = ctx.getViewMode().isVisualization();

        final String initScript = String.format("RangeSliderMapper.init(%s,%s,%s,%s)", formGroup.getMarkupId(true),
                valorInicial.getMarkupId(true), valorFinal.getMarkupId(true), disable);

        formGroup.appendLabel(buildLabel(ctx.getModel()));
        formGroup.appendInputHidden(valorInicial);
        formGroup.appendInputHidden(valorFinal);
        formGroup.add(buildIonRangeScriptBehaviour(initScript));

    }

    private Behavior buildIonRangeScriptBehaviour(String initScript){
        return new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);
                PackageResourceReference prr = new PackageResourceReference(RangeSliderMapper.class, "RangeSliderMapper.js");
                response.render(JavaScriptHeaderItem.forReference(prr));
                response.render(OnDomReadyHeaderItem.forScript(initScript));
            }
        };
    }

    private BSLabel buildLabel(IModel<? extends SInstance> model) {
        final AttributeModel<String> labelModel = new AttributeModel<>(model, SPackageBasic.ATR_LABEL);
        return new BSLabel("label", labelModel);
    }

    private IModel<? extends SInstance> resolveModel(SIComposite mi, String path) {
        final SInstance SInstance = mi.getField(path);
        final SInstanceRootModel<?> rootModel = new SInstanceRootModel<>(SInstance);
        return new SInstanceValueModel<>(rootModel);
    }

    private BSControls createFormGroup(WicketBuildContext ctx) {
        return ctx.getContainer().newFormGroup();
    }
}
