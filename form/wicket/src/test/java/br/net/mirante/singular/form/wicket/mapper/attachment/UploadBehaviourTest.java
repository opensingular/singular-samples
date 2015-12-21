package br.net.mirante.singular.form.wicket.mapper.attachment;

import br.net.mirante.singular.form.mform.MDicionario;
import br.net.mirante.singular.form.mform.core.attachment.IAttachmentPersistenceHandler;
import br.net.mirante.singular.form.mform.core.attachment.MIAttachment;
import br.net.mirante.singular.form.wicket.hepers.TestPackage;
import br.net.mirante.singular.form.wicket.test.base.TestApp;
import br.net.mirante.singular.form.wicket.test.base.TestPage;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class UploadBehaviourTest extends WebBehaviourBaseTest {
    private static MDicionario dicionario;
    private static TestPackage tpackage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private UploadBehavior b;

    private MultipartServletWebRequest multipart;


    @BeforeClass
    public static void createDicionario() {
        dicionario = MDicionario.create();
        tpackage = dicionario.carregarPacote(TestPackage.class);
    }

    @Before
    public void setup() throws Exception {
        setupDriver(setupInstance());
    }

    private void setupDriver(MIAttachment instance) throws FileUploadException {
        new WicketTester(new TestApp());
        b = new UploadBehavior(instance);
        b.setWebWrapper(createWebWrapper());
        b.bind(new TestPage());
    }

    private MIAttachment setupInstance() {
        return tpackage.attachmentFileField.novaInstancia();
    }

    private WebWrapper createWebWrapper() throws FileUploadException {
        WebWrapper w = new WebWrapper();
        w.setRequest(mockRequest());
        multipart = mock(MultipartServletWebRequest.class);
        when(request.newMultipartWebRequest(any(Bytes.class), anyString())).thenReturn(multipart);
        w.setResponse(mockResponse());
        return w;
    }

    @Test public void rejectsNonMultipartRequests() {
//        when(containerRequest.getContentType()).thenReturn("text/html");
//        thrown.expect(AbortWithHttpErrorCodeException.class);
//        thrown.expectMessage("Request is not Multipart as Expected");
//        b.onResourceRequested();
    }

    @Test public void wicketDemandsToCallParseToWork() throws Exception {
        b.onResourceRequested();
        verify(multipart).parseFileParts();
    }

    @Test public void informsTheServiceAboutTheFileReceived() throws Exception {
        FileItem file = file("my.file.ext", new byte[] { 0 });

        when(multipart.getFile("FILE-UPLOAD")).thenReturn(newArrayList(file));

        b.onResourceRequested();

        CharSequence textResponse = response.getTextResponse();
        JSONObject result = new JSONObject(textResponse);
        JSONObject answer = new JSONObject();
        JSONArray expected = new JSONArray();
        JSONObject jsonFile = new JSONObject();
        jsonFile.put("name", "my.file.ext");
        jsonFile.put("fileId", "5ba93c9db0cff93f52b521d7420e43f6eda2784f");
        jsonFile.put("hashSHA1", "5ba93c9db0cff93f52b521d7420e43f6eda2784f");
        jsonFile.put("size", 1);
        expected.put(jsonFile);
        answer.put("files", expected);
        assertThat(result).isEqualsToByComparingFields(answer);
    }
    
    @Test public void storesInTheTemporaryServiceHandler() throws Exception {
        MIAttachment instance = (MIAttachment) setupInstance();
        setupDriver(instance);
        
        FileItem f1 = file("my.file.ext", new byte[] { 0 });
        when(multipart.getFile("FILE-UPLOAD")).thenReturn(newArrayList(f1));

        b.onResourceRequested();
        
        IAttachmentPersistenceHandler handler = instance.getDocument().getAttachmentPersistenceHandler();
        
        assertThat(handler.getAttachment("5ba93c9db0cff93f52b521d7420e43f6eda2784f"))
            .isNotNull();
    }
    
    @Test public void removesOlderTemporaryFilesIfNewOneIsUploaded() throws Exception {
        MIAttachment instance = (MIAttachment) setupInstance();
        setupDriver(instance);
        
        FileItem f1 = file("my.file.ext", new byte[] { 0 });
        when(multipart.getFile("FILE-UPLOAD")).thenReturn(newArrayList(f1));
        b.onResourceRequested();
        
        FileItem f2 = file("my.file.ext", new byte[] { 1 });
        when(multipart.getFile("FILE-UPLOAD")).thenReturn(newArrayList(f2));
        b.onResourceRequested();
        
        IAttachmentPersistenceHandler handler = instance.getDocument().getAttachmentPersistenceHandler();
        
        assertThat(handler.getAttachments()).hasSize(1);
        assertThat(handler.getAttachment("bf8b4530d8d246dd74ac53a13471bba17941dff7"))
            .isNotNull();
    }

    private FileItem file(String fileName, byte[] content) throws IOException {
        FileItem file = mock(FileItem.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(content));
        when(file.getName()).thenReturn(fileName);
        return file;
    }

}
