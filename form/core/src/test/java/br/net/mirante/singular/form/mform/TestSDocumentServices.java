package br.net.mirante.singular.form.mform;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.net.mirante.singular.form.mform.core.attachment.MIAttachment;
import br.net.mirante.singular.form.mform.core.attachment.MTipoAttachment;
import br.net.mirante.singular.form.mform.document.SDocument;

public class TestSDocumentServices {
    private MTipoComposto<?> groupingType;
    private MIAttachment fileFieldInstance;
    private SDocument document;

    @Before public void setup(){
        MDicionario dicionario = MDicionario.create();
        createTypes(dicionario.criarNovoPacote("teste"));
        createInstances();
    }

    private void createTypes(PacoteBuilder pb) {
        groupingType = pb.createTipoComposto("Grouping");
        groupingType.addCampo("anexo", MTipoAttachment.class);
        groupingType.addCampoInteger("justIgnoreThis");
    }
    
    private void createInstances() {
        MIComposto instance = (MIComposto) groupingType.novaInstancia();
        document = instance.getDocument();
        fileFieldInstance = (MIAttachment) instance.getAllChildren().iterator().next();
    }
    
    @SuppressWarnings({ "rawtypes", "serial" })
    private ServiceRef ref(final Object provider) {
        return new ServiceRef() {
            public Object get() {   return provider;    }
        };
    }
    
    @Test public void findsRegisteredServiceByName(){
        final Object provider = new Object();
        document.bindLocalService("something", ref(provider)); 
        
        assertThat(document.lookupLocalService("something", Object.class))
            .isSameAs(provider);
    }
    
    @Test public void doesNotConfusesNames(){
        document.bindLocalService("something", ref(new Object())); 
        
        assertThat(document.lookupLocalService("nothing", Object.class))
            .isNull();
    }
    
    @SuppressWarnings("unchecked")
    @Test public void findsRegisteredServiceByClass(){
        final Object provider = new Object();
        document.bindLocalService(Object.class, ref(provider)); 
        
        assertThat(document.lookupLocalService(Object.class))
            .isSameAs(provider);
    }
    
    @SuppressWarnings("unchecked")
    @Test public void doesNotAceptsSubclasses(){
        document.bindLocalService(Object.class, ref(new Object())); 
        
        assertThat(document.lookupLocalService(String.class))
            .isNull();
    }

}
