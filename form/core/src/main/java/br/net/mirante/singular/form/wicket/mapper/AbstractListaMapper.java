package br.net.mirante.singular.form.wicket.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;

import br.net.mirante.singular.form.mform.MILista;
import br.net.mirante.singular.form.mform.MInstancia;
import br.net.mirante.singular.form.wicket.IWicketComponentMapper;
import br.net.mirante.singular.form.wicket.model.MInstanciaItemListaModel;
import br.net.mirante.singular.util.wicket.ajax.ActionAjaxButton;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSContainer;
import br.net.mirante.singular.util.wicket.resource.Icone;

public abstract class AbstractListaMapper implements IWicketComponentMapper {

    protected static AdicionarButton appendAdicionarButton(final IModel<MILista<MInstancia>> mLista, final Form<?> form, final BSContainer<?> cell) {
        AdicionarButton btn = new AdicionarButton("_add", form, mLista);
        cell
            .newTemplateTag(t -> ""
                + "<button"
                + " wicket:id='_add'"
                + " class='btn btn-success btn-sm'"
                + " style='padding:5px 3px 1px;margin-top:3px;margin-right:7px;'><i class='" + Icone.PLUS + "'></i>"
                + "</button>"
            ).add(btn);
        return btn;
    }

    protected static InserirButton appendInserirButton(ElementsView elementsView, Form<?> form, Item<MInstancia> item, BSContainer<?> cell) {
        InserirButton btn = new InserirButton("_inserir_", elementsView, form, elementsView.getModel(), item);
        cell
            .newTemplateTag(tp -> ""
                + "<button"
                + " wicket:id='_inserir_'"
                + " class='btn btn-success btn-sm'"
                + " style='padding:5px 3px 1px;margin-top:3px;'><i class='" + Icone.PLUS + "'></i>"
                + "</button>")
            .add(btn);
        return btn;
    }

    protected static RemoverButton appendRemoverButton(ElementsView elementsView, Form<?> form, Item<MInstancia> item, BSContainer<?> cell) {
        RemoverButton btn = new RemoverButton("_remover_", form, elementsView, item);
        cell
            .newTemplateTag(tp -> ""
                + "<button"
                + " wicket:id='_remover_'"
                + " class='btn btn-danger btn-sm'"
                + " style='padding:5px 3px 1px;margin-top:3px;'><i class='" + Icone.MINUS + "'></i>"
                + "</button>")
            .add(btn);
        return btn;
    }

    protected static abstract class ElementsView extends RefreshingView<MInstancia> {
        public ElementsView(String id, IModel<MILista<MInstancia>> model) {
            super(id, model);
            setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        }
        @Override
        protected Iterator<IModel<MInstancia>> getItemModels() {
            List<IModel<MInstancia>> list = new ArrayList<>();
            MILista<MInstancia> miLista = getModelObject();
            for (int i = 0; i < miLista.size(); i++)
                list.add(new MInstanciaItemListaModel<>(getDefaultModel(), i));
            return list.iterator();
        }
        @SuppressWarnings("unchecked")
        public MILista<MInstancia> getModelObject() {
            return (MILista<MInstancia>) getDefaultModelObject();
        }
        @SuppressWarnings("unchecked")
        public IModel<MILista<MInstancia>> getModel() {
            return (IModel<MILista<MInstancia>>) getDefaultModel();
        }
    }

    protected static class InserirButton extends ActionAjaxButton {
        private final IModel<MILista<MInstancia>> modelLista;
        private final Item<MInstancia>            item;
        private final ElementsView                elementsView;
        private InserirButton(String id, ElementsView elementsView, Form<?> form, IModel<MILista<MInstancia>> mLista, Item<MInstancia> item) {
            super(id, form);
            this.setDefaultFormProcessing(false);
            this.elementsView = elementsView;
            this.modelLista = mLista;
            this.item = item;
        }
        @Override
        protected void onAction(AjaxRequestTarget target, Form<?> form) {
            final int index = item.getIndex();
            MILista<MInstancia> lista = modelLista.getObject();
            lista.addNovoAt(index);
            List<MInstanciaItemListaModel<?>> itemModels = new ArrayList<>();
            for (Component child : elementsView) {
                IModel<?> childModel = child.getDefaultModel();
                if (childModel instanceof MInstanciaItemListaModel<?>)
                    itemModels.add((MInstanciaItemListaModel<?>) childModel);
            }
            for (MInstanciaItemListaModel<?> itemModel : itemModels)
                if (itemModel.getIndex() >= index)
                    itemModel.setIndex(itemModel.getIndex() + 1);
            target.add(form);
            target.focusComponent(this);
        }
    }

    protected static class RemoverButton extends ActionAjaxButton {
        private final ElementsView     elementsView;
        private final Item<MInstancia> item;
        private RemoverButton(String id, Form<?> form, ElementsView elementsView, Item<MInstancia> item) {
            super(id, form);
            this.setDefaultFormProcessing(false);
            this.elementsView = elementsView;
            this.item = item;
        }
        @Override
        protected void onAction(AjaxRequestTarget target, Form<?> form) {
            final int index = item.getIndex();
            MILista<MInstancia> lista = elementsView.getModelObject();
            lista.remove(index);
            List<MInstanciaItemListaModel<?>> itemModels = new ArrayList<>();
            for (Component child : elementsView) {
                IModel<?> childModel = child.getDefaultModel();
                if (childModel instanceof MInstanciaItemListaModel<?>)
                    itemModels.add((MInstanciaItemListaModel<?>) childModel);
            }
            for (MInstanciaItemListaModel<?> itemModel : itemModels)
                if (itemModel.getIndex() > index)
                    itemModel.setIndex(itemModel.getIndex() - 1);
                else if (itemModel.getIndex() == index)
                    itemModel.setIndex(Integer.MAX_VALUE);
            target.add(form);
        }
    }

    protected static final class AdicionarButton extends ActionAjaxButton {
        private final IModel<MILista<MInstancia>> modelLista;
        private AdicionarButton(String id, Form<?> form, IModel<MILista<MInstancia>> mLista) {
            super(id, form);
            this.setDefaultFormProcessing(false);
            modelLista = mLista;
        }
        @Override
        protected void onAction(AjaxRequestTarget target, Form<?> form) {
            MILista<MInstancia> lista = modelLista.getObject();
            lista.addNovo();
            target.add(form);
            target.focusComponent(this);
        }
    }
}