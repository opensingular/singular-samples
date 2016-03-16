package br.net.mirante.singular.form.wicket.test.base;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import br.net.mirante.singular.form.mform.*;
import br.net.mirante.singular.form.wicket.enums.AnnotationMode;
import br.net.mirante.singular.form.wicket.enums.ViewMode;
import com.google.common.base.Predicate;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import br.net.mirante.singular.form.mform.context.SFormConfig;
import br.net.mirante.singular.form.mform.document.DefaultServiceRegistry;
import br.net.mirante.singular.form.mform.document.RefSDocumentFactory;
import br.net.mirante.singular.form.mform.document.RefType;
import br.net.mirante.singular.form.mform.document.SDocument;
import br.net.mirante.singular.form.mform.document.SDocumentFactory;
import br.net.mirante.singular.form.mform.document.ServiceRegistry;
import br.net.mirante.singular.form.mform.document.TypeLoader;
import br.net.mirante.singular.form.wicket.SingularFormContextWicket;
import br.net.mirante.singular.form.wicket.UIBuilderWicket;
import br.net.mirante.singular.form.wicket.component.SingularValidationButton;
import br.net.mirante.singular.form.wicket.panel.SingularFormPanel;

public class MockPage extends WebPage {

    public SFormConfig<String> mockFormConfig = new MockFormConfig();
    protected ViewMode viewMode = ViewMode.EDITION;
    protected AnnotationMode annotationMode = AnnotationMode.NONE;
    protected SIComposite currentInstance;

    protected Consumer<STypeComposite> typeBuilder;
    private Function<SType, SIComposite> instanceCreator;

    private Form<?> form = new Form("form");

    private SingularFormPanel<String> singularFormPanel = new SingularFormPanel<String>("singularFormPanel", mockFormConfig) {
        @Override
        protected SInstance createInstance(SFormConfig<String> singularFormConfig) {
            final Optional<SType<?>> mockType = mockFormConfig.getTypeLoader().loadType("mockType");
            if (mockType.isPresent()) {
                if (mockType.get() instanceof STypeComposite) {
                    typeBuilder.accept((STypeComposite) mockType.get());
                }
            }
            if(instanceCreator == null) return null;
            return currentInstance = instanceCreator.apply(mockType.get());
        }

        @Override
        public ViewMode getViewMode() { return viewMode;    }

        @Override
        public AnnotationMode annotation() {    return annotationMode;  }
    };

    private SingularValidationButton singularValidationButton = new SingularValidationButton("validate-btn") {
        @Override
        protected void onValidationSuccess(AjaxRequestTarget target, Form<?> form, IModel<? extends SInstance> instanceModel) {

        }

        @Override
        public IModel<? extends SInstance> getCurrentInstance() {
            return singularFormPanel.getRootInstance();
        }
    };

    public MockPage() {
        add(form.add(singularFormPanel, singularValidationButton));
    }

    public Form<?> getForm() {
        return form;
    }

    public SingularFormPanel<String> getSingularFormPanel() {
        return singularFormPanel;
    }

    public SingularValidationButton getSingularValidationButton() {
        return singularValidationButton;
    }

    public void setAsVisualizationView() {  viewMode = ViewMode.VISUALIZATION;  }

    public void enableAnnotation() { annotationMode = AnnotationMode.EDIT; }

    public SIComposite getCurrentInstance() { return currentInstance; }

    public void setInstanceCreator(Function<SType, SIComposite> instanceCreator) {
        this.instanceCreator = instanceCreator;
    }
}

class MockFormConfig implements SFormConfig<String> {

    private final MockSDocumentFactory documentFactory = new MockSDocumentFactory();
    private final MockTypeLoader mockTypeLoader = new MockTypeLoader();

    @Override
    public SDocumentFactory getDocumentFactory() {
        return documentFactory;
    }

    @Override
    public TypeLoader<String> getTypeLoader() {
        return mockTypeLoader;
    }
}

class MockSDocumentFactory extends SDocumentFactory {

    private final DefaultServiceRegistry defaultServiceRegistry = new DefaultServiceRegistry();

    private final SingularFormContextWicket singularFormContextWicket = new SingularFormContextWicket() {
        @Override
        public UIBuilderWicket getUIBuilder() {
            return new UIBuilderWicket();
        }

        @Override
        public ServiceRegistry getServiceRegistry() {
            return defaultServiceRegistry;
        }
    };

    {
        defaultServiceRegistry.bindLocalService(SingularFormContextWicket.class, new RefService<SingularFormContextWicket>() {
            @Override
            public SingularFormContextWicket get() {
                return singularFormContextWicket;
            }
        });
    }

    @Override
    public RefSDocumentFactory getDocumentFactoryRef() {
        final MockSDocumentFactory documentFactory = this;
        return new RefSDocumentFactory() {
            @Override
            protected SDocumentFactory retrieve() {
                return documentFactory;
            }
        };
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return defaultServiceRegistry;
    }

    @Override
    protected void setupDocument(SDocument document) {

    }
}

class MockTypeLoader extends TypeLoader<String> {

    private final SDictionary dictionary;

    {
        dictionary = SDictionary.create();
    }

    @Override
    protected Optional<RefType> loadRefTypeImpl(String typeId) {
        return Optional.of(new RefType() {
            @Override
            protected SType<?> retrieve() {
                final SType<?> typeOptional = dictionary.getTypeOptional(typeId);
                if (typeOptional != null) {
                    return typeOptional;
                } else {
                    return dictionary.createNewPackage(typeId).createCompositeType("mockRoot");
                }
            }
        });
    }

    @Override
    protected Optional<SType<?>> loadTypeImpl(String typeId) {
        final SType<?> typeOptional = dictionary.getTypeOptional(typeId);
        if (typeOptional != null) {
            return Optional.of(typeOptional);
        } else {
            return Optional.of(dictionary.createNewPackage(typeId).createCompositeType("mockRoot"));
        }
    }
}