package br.net.mirante.singular.form.wicket.mapper.attachment.single;

import br.net.mirante.singular.commons.util.Loggable;
import br.net.mirante.singular.form.SIList;
import br.net.mirante.singular.form.SInstance;
import br.net.mirante.singular.form.type.core.attachment.SIAttachment;
import br.net.mirante.singular.form.wicket.enums.ViewMode;
import br.net.mirante.singular.form.wicket.mapper.SingularEventsHandlers;
import br.net.mirante.singular.form.wicket.mapper.attachment.BaseJQueryFileUploadBehavior;
import br.net.mirante.singular.form.wicket.mapper.attachment.DownloadLink;
import br.net.mirante.singular.form.wicket.mapper.attachment.DownloadSupportedBehavior;
import br.net.mirante.singular.form.wicket.mapper.attachment.DownloadUtil;
import br.net.mirante.singular.form.wicket.mapper.attachment.FileUploadServlet;
import br.net.mirante.singular.form.wicket.model.IMInstanciaAwareModel;
import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.resource.PackageResourceReference;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import static br.net.mirante.singular.form.wicket.mapper.attachment.FileUploadServlet.PARAM_NAME;
import static br.net.mirante.singular.form.wicket.mapper.SingularEventsHandlers.FUNCTION.ADD_MOUSEDOWN_HANDLERS;

public class FileUploadPanel extends Panel implements Loggable {


    private AddFileBehavior adder;
    private final IModel<SIAttachment> model;
    private final ViewMode viewMode;
    private final AjaxButton removeFileButton = new AjaxButton("remove_btn") {

        @Override
        protected void onInitialize() {
            super.onInitialize();
            add(new ClassAttributeModifier() {
                protected Set<String> update(Set<String> oldClasses) {
                    if (model.getObject().getFileId() == null) {
                        oldClasses.add("file-trash-button-hidden");
                    }
                    return oldClasses;
                }
            });
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            super.onSubmit(target, form);
            model.getObject().clearInstance();
            if (model.getObject().getParent() instanceof SIList) {
                final SIList parent = (SIList) model.getObject().getParent();
                parent.remove(parent.indexOf(model.getObject()));
                target.add(form);
            } else {
                target.add(FileUploadPanel.this);
            }
        }

    };
    private final WebMarkupContainer uploadFileButton = new WebMarkupContainer("upload_btn") {

        @Override
        protected void onInitialize() {
            super.onInitialize();
            add(new ClassAttributeModifier() {
                protected Set<String> update(Set<String> oldClasses) {
                    if (model.getObject().getFileId() != null) {
                        oldClasses.add("file-trash-button-hidden");
                    }
                    return oldClasses;
                }
            });
        }
    };

    private FileUploadField fileField;
    private WebMarkupContainer filesContainer, progressBar;
    private DownloadSupportedBehavior downloader;
    private DownloadLink downloadLink;

    public FileUploadPanel(String id, IModel<SIAttachment> model, ViewMode viewMode) {
        super(id, model);
        this.model = model;
        this.viewMode = viewMode;

    }


    private IMInstanciaAwareModel dummyModel(final IModel<SIAttachment> model) {
        return new IMInstanciaAwareModel() {
            @Override
            public Object getObject() {
                return null;
            }

            @Override
            public void setObject(Object object) {
            }

            @Override
            public void detach() {
            }

            @Override
            public SInstance getMInstancia() {
                return model.getObject();
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(adder = new AddFileBehavior());
        this.add(downloader = new DownloadSupportedBehavior(model));
        downloadLink = new DownloadLink("downloadLink", model, downloader);
        fileField = new FileUploadField("fileUpload", dummyModel(model));

        add((filesContainer = new WebMarkupContainer("files")).add(downloadLink));
        add(uploadFileButton.add(fileField));
        add(removeFileButton);
        add(progressBar = new WebMarkupContainer("progress"));

        add(new ClassAttributeModifier() {

            @Override
            protected Set<String> update(Set<String> oldClasses) {
                oldClasses.add("fileinput fileinput-new upload-single upload-single-uploaded");
                return oldClasses;
            }
        });

        uploadFileButton.add(new SingularEventsHandlers(ADD_MOUSEDOWN_HANDLERS));
        downloadLink.add(new SingularEventsHandlers(ADD_MOUSEDOWN_HANDLERS));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptReferenceHeaderItem.forReference(resourceRef("FileUploadPanel.js")));
        response.render(OnDomReadyHeaderItem.forScript(generateInitJS()));
    }

    private String generateInitJS() {
        return " $(function () { \n" +
                "     var params = { \n" +
                "             panel_id: '" + this.getMarkupId() + "', \n" +
                "             file_field_id: '" + fileField.getMarkupId() + "', \n" +
                "             files_id : '" + filesContainer.getMarkupId() + "', \n" +
                "             progress_bar_id : '" + progressBar.getMarkupId() + "', \n" +
                "  \n" +
                "             param_name : '" + PARAM_NAME + "', \n" +
                "             upload_url : '" + uploadUrl() + "', \n" +
                "             download_url : '" + downloader.getUrl() + "', \n" +
                "             add_url : '" + adder.getUrl() + "', \n" +
                "  \n" +
                "     }; \n" +
                "  \n" +
                "     window.FileUploadPanel.setup(params); \n" +
                " });";
    }

    private PackageResourceReference resourceRef(String resourceName) {
        return new PackageResourceReference(getClass(), resourceName);
    }

    private String uploadUrl() {
        String contextPath = getWebApplication().getServletContext().getContextPath();
        return contextPath + FileUploadServlet.UPLOAD_URL;
    }

    public FileUploadField getUploadField() {
        return fileField;
    }

    private class AddFileBehavior extends BaseJQueryFileUploadBehavior<SIAttachment> {

        public AddFileBehavior() {
            super((IModel<SIAttachment>) FileUploadPanel.this.getDefaultModel());
        }

        @Override
        public void onResourceRequested() {
            try {
                SIAttachment siAttachment = (SIAttachment) FileUploadPanel.this.getDefaultModel().getObject();
                siAttachment.setContent(
                        getParamFileId("name").toString(),
                        FileUploadServlet.lookupFile(getParamFileId("fileId").toString()),
                        getParamFileId("size").toLong());
                DownloadUtil.writeJSONtoResponse(siAttachment, RequestCycle.get().getResponse());
            } catch (Exception e) {
                getLogger().error(e.getMessage(), e);
                throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }


}
