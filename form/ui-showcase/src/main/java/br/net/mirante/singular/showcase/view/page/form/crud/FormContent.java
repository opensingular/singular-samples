package br.net.mirante.singular.showcase.view.page.form.crud;

import javax.inject.Inject;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import br.net.mirante.singular.form.mform.MILista;
import br.net.mirante.singular.form.mform.MInstancia;
import br.net.mirante.singular.form.mform.MTipo;
import br.net.mirante.singular.form.mform.MTipoComposto;
import br.net.mirante.singular.form.mform.basic.view.MAnnotationView;
import br.net.mirante.singular.form.mform.core.annotation.AtrAnnotation;
import br.net.mirante.singular.form.mform.core.annotation.MIAnnotation;
import br.net.mirante.singular.form.mform.core.annotation.MTipoAnnotationList;
import br.net.mirante.singular.form.mform.io.MformPersistenciaXML;
import br.net.mirante.singular.form.util.xml.MElement;
import br.net.mirante.singular.form.util.xml.MParser;
import br.net.mirante.singular.form.wicket.component.BelverSaveButton;
import br.net.mirante.singular.form.wicket.component.BelverValidationButton;
import br.net.mirante.singular.form.wicket.enums.ViewMode;
import br.net.mirante.singular.form.wicket.model.MInstanceRootModel;
import br.net.mirante.singular.form.wicket.panel.BelverPanel;
import br.net.mirante.singular.showcase.dao.form.ExampleDataDAO;
import br.net.mirante.singular.showcase.dao.form.ExampleDataDTO;
import br.net.mirante.singular.showcase.dao.form.TemplateRepository;
import br.net.mirante.singular.showcase.view.SingularWicketContainer;
import br.net.mirante.singular.showcase.view.page.form.crud.services.SpringServiceRegistry;
import br.net.mirante.singular.showcase.view.template.Content;

public class FormContent extends Content implements SingularWicketContainer<CrudContent, Void> {

    /**
     *
     */
    private static final long serialVersionUID = 327099871613673185L;

    private static final Logger logger = LoggerFactory.getLogger(FormContent.class);

    private final String key;
    private final String typeName;
    private ViewMode viewMode = ViewMode.EDITION;

    private ExampleDataDTO currentModel;
    private BelverPanel belverPanel;

    @Inject
    private ExampleDataDAO dao;

    @Inject
    private SpringServiceRegistry serviceRegistry;

    public FormContent(String id, StringValue type, StringValue key, StringValue viewMode) {
        super(id, false, true);
        if (!viewMode.isNull()) {
            this.viewMode = ViewMode.valueOf(viewMode.toString());
        }
        this.typeName = type.toString();
        this.key = key.toString();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(buildForm());
    }

    @Override
    protected IModel<?> getContentTitlelModel() {
        return new ResourceModel("label.content.title");
    }

    @Override
    protected IModel<?> getContentSubtitlelModel() {
        return new ResourceModel("label.content.title");
    }

    private Form<?> buildForm() {
        Form<?> form = new Form<>("save-form");
        form.setMultiPart(true);
        form.add(buildBelverBasePanel());
        form.add(buildSaveButton());
        form.add(buildSaveWithoutValidateButton());
        form.add(buildValidateButton());
        form.add(buildCancelButton());
        return form;
    }

    private BelverPanel buildBelverBasePanel() {
        belverPanel = new BelverPanel("belver-panel", serviceRegistry) {

            @Override
            protected MTipo<?> getTipo() {
                return TemplateRepository.get().loadType(typeName);
            }

            @Override
            protected MInstanceRootModel<MInstancia> populateInstance(MTipo<?> tipo) {
                try {
                    loadOrbuildModel();

                    final String xml = currentModel.getXml();
                    if (xml == null || xml.isEmpty()) {
                        return super.populateInstance(tipo);
                    } else {
                        MElement xmlElement = MParser.parse(xml);
                        MInstancia instance = MformPersistenciaXML.fromXML(tipo, xmlElement);

                        final String annotations = currentModel.getAnnnotations();
                        if(StringUtils.isNotBlank(annotations)){
                            MElement xmlAnnotations = MParser.parse(annotations);
                            MTipoAnnotationList tipoAnnotation = tipo.getDicionario().getTipo(MTipoAnnotationList.class);

                            MILista iAnnotations =
                                    (MILista) MformPersistenciaXML.fromXML(tipoAnnotation, xmlAnnotations);
                            instance.as(AtrAnnotation::new).loadAnnotations(iAnnotations);
                        }

                        return new MInstanceRootModel<>(instance);
                    }

                } catch (SAXException | IOException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            }

            @Override
            public ViewMode getViewMode() {
                return viewMode;
            }
        };

        return belverPanel;
    }

    private void loadOrbuildModel() {
        if (key == null || key.isEmpty()) {
            currentModel = new ExampleDataDTO(UUID.randomUUID().toString());
            currentModel.setType(typeName);
        } else {
            currentModel = dao.find(key, typeName);
        }
    }

    private void backToCrudPage(Component componentContext) {
        PageParameters params = new PageParameters()
                .add(CrudPage.TYPE_NAME, currentModel.getType());
        componentContext.setResponsePage(CrudPage.class, params);
    }

    private Component buildSaveButton() {
        final Component button = new BelverSaveButton("save-btn") {
            @Override
            public IModel<? extends MInstancia> getCurrentInstance() {
                return belverPanel.getRootInstance();
            }

            @Override
            protected void handleSaveXML(AjaxRequestTarget target, MElement xml) {
                getCurrentInstance().getObject().getDocument().persistFiles();
                if(xml != null) {
                    currentModel.setXml(xml.toStringExato());
                } else {
                    currentModel.setXml(StringUtils.EMPTY);
                }
                addAnnotationsToModel(getCurrentInstance().getObject());
                dao.save(currentModel);
                backToCrudPage(this);
            }
        };
        return button.add(visibleOnlyInEditionBehaviour());
    }

    private void addAnnotationsToModel(MInstancia instancia) {
        AtrAnnotation annotatedInstance = instancia.as(AtrAnnotation::new);
        List<MIAnnotation> allAnnotations = annotatedInstance.allAnnotations();
        if(!allAnnotations.isEmpty()){
            Optional<String> annXml = annotationsToXml(instancia, annotatedInstance, allAnnotations);
            currentModel.setAnnotations(annXml.orElse(""));
        }
    }

    private Optional<String> annotationsToXml(MInstancia instancia, AtrAnnotation annotatedInstance, List<MIAnnotation> allAnnotations) {
        MILista pLista = (MILista) instancia.getDicionario().getTipo(MTipoAnnotationList.class).novaInstancia();
        for(MIAnnotation a: allAnnotations){
            pLista.addElement(a);
        }
        return MformPersistenciaXML.toStringXML(
                annotatedInstance.persistentAnnotations());
    }

    private Component buildSaveWithoutValidateButton() {
        final Component button = new BelverValidationButton("save-whitout-validate-btn") {
            protected void save() {
                MElement rootXml = MformPersistenciaXML.toXML(getCurrentInstance().getObject());
                getCurrentInstance().getObject().getDocument().persistFiles();
                if(rootXml != null) {
                    currentModel.setXml(rootXml.toStringExato());
                } else {
                    currentModel.setXml(StringUtils.EMPTY);
                }
				addAnnotationsToModel(getCurrentInstance().getObject());
                dao.save(currentModel);
                backToCrudPage(this);
            }

            @Override
            protected void onValidationSuccess(AjaxRequestTarget target, Form<?> form, IModel<? extends MInstancia> instanceModel) {
                save();
            }

            @Override
            protected void onValidationError(AjaxRequestTarget target, Form<?> form, IModel<? extends MInstancia> instanceModel) {
                save();
            }

            @Override
            public IModel<? extends MInstancia> getCurrentInstance() {
                return belverPanel.getRootInstance();
            }

        };
        return button.add(visibleOnlyInEditionBehaviour());
    }

    @SuppressWarnings("rawtypes")
    private AjaxLink<?> buildCancelButton() {
        return new AjaxLink("cancel-btn") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                backToCrudPage(this);
            }
        };
    }

    private Component buildValidateButton() {
        final BelverValidationButton button = new BelverValidationButton("validate-btn") {

            @Override
            protected void onValidationSuccess(AjaxRequestTarget target, Form<?> form,
                                               IModel<? extends MInstancia> instanceModel) {
            }

            @Override
            public IModel<? extends MInstancia> getCurrentInstance() {
                return belverPanel.getRootInstance();
            }
        };

        return button.add(visibleOnlyInEditionBehaviour());
    }

    private Behavior visibleOnlyInEditionBehaviour() {
        return new Behavior() {
            @Override
            public void onConfigure(Component component) {
                super.onConfigure(component);

                component.setVisible(viewMode.isEdition() || isInAnnotationMode());
            }
        };
    }

    private boolean isInAnnotationMode() {
        MTipo<?> mTipo = TemplateRepository.get().loadType(typeName);
        return viewMode.isVisualization() && isAnnotated(mTipo);
    }

    private boolean isAnnotated(MTipo<?> mTipo) {
        if(mTipo.getView() instanceof MAnnotationView){
            return true;
        }
        if(mTipo instanceof MTipoComposto){
            MTipoComposto composto = (MTipoComposto) mTipo;
            Collection<MTipo> fields = composto.getFields();
            for(MTipo child: fields){
                if(isAnnotated(child)){
                    return true;
                }
            }
        }
        return false;
    }

}