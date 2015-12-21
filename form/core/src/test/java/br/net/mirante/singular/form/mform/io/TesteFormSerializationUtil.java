package br.net.mirante.singular.form.mform.io;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import br.net.mirante.singular.form.mform.*;
import org.junit.Before;
import org.junit.Test;

import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;
import br.net.mirante.singular.form.mform.basic.ui.MPacoteBasic;
import br.net.mirante.singular.form.mform.core.MIString;
import br.net.mirante.singular.form.mform.core.MTipoString;
import br.net.mirante.singular.form.mform.document.SDocument;
import br.net.mirante.singular.form.mform.document.ServiceRegistry.Pair;

import static org.junit.Assert.*;

public class TesteFormSerializationUtil {

    @Before
    public void clean() {
        MDicionarioResolver.setDefault(null);
    }

    @Test
    public void testVerySimplesCase() {
        MDicionarioResolver loader = createLoaderPacoteTeste((pacote) -> {
            pacote.createTipo("endereco", MTipoString.class);
        });
        MInstancia instancia = loader.loadType("teste.endereco").novaInstancia();
        testSerializacao(instancia, loader);

    }

    @Test
    public void testTipoComposto() {
        MDicionarioResolver loader = createLoaderPacoteTeste((pacote) -> {
            MTipoComposto<? extends MIComposto> endereco = pacote.createTipoComposto("endereco");
            endereco.addCampoString("rua");
            endereco.addCampoString("bairro");
            endereco.addCampoString("cidade");
        });
        MIComposto instancia = (MIComposto) loader.loadType("teste.endereco").novaInstancia();
        instancia.setValor("rua", "A1");
        instancia.setValor("bairro", "A2");
        testSerializacao(instancia, loader);

        // Testa um subPath
        testSerializacao(instancia.getCampo("bairro"), loader);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTipoListSimples() {
        MDicionarioResolver loader = createLoaderPacoteTeste((pacote) -> {
            pacote.createTipoListaOf("enderecos", MTipoString.class);
        });
        MILista<MIString> instancia = (MILista<MIString>) loader.loadType("teste.enderecos").novaInstancia();
        instancia.addValor("A1");
        instancia.addValor("A2");
        instancia.addValor("A3");
        instancia.addValor("A4");
        testSerializacao(instancia, loader);

        // Testa um subPath
        testSerializacao(instancia.getCampo("[1]"), loader);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTipoListComposto() {
        MDicionarioResolver loader = createLoaderPacoteTeste((pacote) -> {
            MTipoComposto<MIComposto> endereco = pacote.createTipoListaOfNovoTipoComposto("enderecos", "endereco").getTipoElementos();
            endereco.addCampoString("rua");
            endereco.addCampoString("bairro");
            endereco.addCampoString("cidade");
        });
        MILista<MIComposto> instancia = (MILista<MIComposto>) loader.loadType("teste.enderecos").novaInstancia();
        instancia.addNovo(e -> e.setValor("rua", "A1"));
        instancia.addNovo(e -> e.setValor("bairro", "A2"));
        instancia.addNovo(e -> {
            e.setValor("rua", "A31");
            e.setValor("bairro", "A32");
        });
        testSerializacao(instancia, loader);

        // Testa um subPath
        testSerializacao(instancia.getCampo("[0].rua"), loader);
        testSerializacao(instancia.getCampo("[2].bairro"), loader);
    }

    @Test
    public void testTipoCompostoListCompostoList() {
        MDicionarioResolver loader = createLoaderPacoteTeste((pacote) -> {
            MTipoComposto<? extends MIComposto> tipoCurriculo = pacote.createTipoComposto("curriculo");
            tipoCurriculo.addCampoString("nome");
            MTipoComposto<MIComposto> tipoContato = tipoCurriculo.addCampoListaOfComposto("contatos", "contato").getTipoElementos();
            tipoContato.addCampoInteger("prioridade");
            MTipoComposto<MIComposto> endereco = tipoContato.addCampoListaOfComposto("enderecos", "endereco").getTipoElementos();
            endereco.addCampoString("rua");
            endereco.addCampoString("cidade");
        });

        MIComposto instancia = (MIComposto) loader.loadType("teste.curriculo").novaInstancia();
        instancia.setValor("nome", "Joao");
        MIComposto contato = (MIComposto) instancia.getFieldList("contatos").addNovo();
        contato.setValor("prioridade", -1);
        contato.getFieldList("enderecos").addNovo();
        contato.setValor("enderecos[0].rua", "A31");
        contato.setValor("enderecos[0].cidade", "A32");
        testSerializacao(instancia, loader);

        // Testa um subPath
        testSerializacao(instancia.getCampo("nome"), loader);
        testSerializacao(instancia.getCampo("contatos"), loader);
    }

    @Test
    public void testUsoDicionarioResolverDefault() {
        TestCaseForm.assertException(() -> MDicionarioResolver.getDefault(), "resolver default não está configurado");

        MDicionarioResolver loader = createLoaderPacoteTeste((pacote) -> {
            pacote.createTipo("endereco", MTipoString.class);
        });
        MInstancia instancia = loader.loadType("teste.endereco").novaInstancia();
//        TestCaseForm.assertException(() -> testSerializacao(instancia, null), "resolver default não está configurado");

        MDicionarioResolver.setDefault(loader);
        testSerializacao(instancia, null);

    }

    @Test
    public void testUsoDicionarResolverSerializado() {
        DicionarioResolverStaticTest resolver = new DicionarioResolverStaticTest(null);
        MInstancia instancia = resolver.loadType("teste.cadastro").novaInstancia();
        instancia.setValor("Fulano");

//        TestCaseForm.assertException(() -> testSerializacao(instancia, null), "resolver default não está configurado");

        testSerializacaoComResolverSerializado(instancia, resolver);
    }

    @Test
    public void testUsoDicionarResolverSerializadoMasComReferenciaInternaNaoSerializavel() {
        DicionarioResolverStaticTest resolver = new DicionarioResolverStaticTest(this);
        MInstancia instancia = resolver.loadType("teste.cadastro").novaInstancia();
        instancia.setValor("Fulano");

        TestCaseForm.assertException(() -> testSerializacaoComResolverSerializado(instancia, resolver), "NotSerializableException");
    }

    @Test
    public void testSerializacaoReferenciaServico() {
        MDicionarioResolver resolver = createLoaderPacoteTeste((pacote) -> {
            pacote.createTipo("endereco", MTipoString.class);
        });
        MInstancia instancia = resolver.loadType("teste.endereco").novaInstancia();

        instancia.getDocument().bindLocalService("A", String.class,
            ServiceRef.of("AA"));
        MInstancia instancia2 = testSerializacao(instancia, resolver);
        assertEquals("AA", instancia2.getDocument().lookupService("A", String.class));

        // Testa itens não mantido entre serializações
        instancia.getDocument().bindLocalService("B", String.class,
            ServiceRef.ofToBeDescartedIfSerialized("BB"));
        instancia2 = serializarEDeserializar(instancia, resolver);
        assertNull(instancia2.getDocument().lookupService("B", String.class));

    }

    @Test
    public void testSerializacaoAtributos() {
        MDicionarioResolver resolver = createLoaderPacoteTeste((pacote) -> {
            pacote.getDicionario().carregarPacote(MPacoteBasic.class);
            MTipoComposto<?> tipoEndereco = pacote.createTipoComposto("endereco");
            tipoEndereco.addCampoString("rua");
            tipoEndereco.addCampoString("cidade");
        });
        MIComposto instancia = (MIComposto) resolver.loadType("teste.endereco").novaInstancia();
        instancia.setValor("rua", "A");
        instancia.as(AtrBasic.class).label("Address");
        instancia.getCampo("rua").as(AtrBasic.class).label("Street");
        instancia.getCampo("cidade").as(AtrBasic.class).label("City");


        MIComposto instancia2 = (MIComposto) testSerializacao(instancia, resolver);

        assertEquals("Address", instancia2.as(AtrBasic.class).getLabel());
        assertEquals("Street", instancia2.getCampo("rua").as(AtrBasic.class).getLabel());
        assertEquals("City", instancia2.getCampo("cidade").as(AtrBasic.class).getLabel());

    }

    @Test public void serializationAndDeserializationAreIndempontent(){
        MDicionario dict = MDicionario.create();
        MPacoteTesteContatos pkt = dict.carregarPacote(MPacoteTesteContatos.class);
        MIComposto bruce = pkt.contato.novaInstancia();
        bruce.setValor("identificacao.nome","Bruce");
        bruce.setValor("identificacao.sobrenome","Wayne");
        FormSerializationUtil.toInstance(FormSerializationUtil.toSerializedObject(bruce));
        FormSerializationUtil.toInstance(FormSerializationUtil.toSerializedObject(bruce));
        FormSerializationUtil.toInstance(FormSerializationUtil.toSerializedObject(bruce));
    }

    @Test public void cachesDictionaryForDeserialization(){
        MDicionario dict1 = MDicionario.create();
        MPacoteTesteContatos pkt = dict1.carregarPacote(MPacoteTesteContatos.class);
        MIComposto bruce = pkt.contato.novaInstancia();
        bruce.setValor("identificacao.nome","Bruce");

        MDicionario dict2 = MDicionario.create();
        MPacoteTesteContatos pkt2 = dict2.carregarPacote(MPacoteTesteContatos.class);
        MIComposto clark = pkt2.contato.novaInstancia();
        clark.setValor("identificacao.nome","Clark");

        FormSerialized fsBruce = FormSerializationUtil.toSerializedObject(bruce);
        FormSerialized fsClark = FormSerializationUtil.toSerializedObject(clark);
        MInstancia deBruce = FormSerializationUtil.toInstance(fsBruce);
        assertThat(deBruce.getDicionario()).isSameAs(dict1);
    }


    @SuppressWarnings("serial")
    private static final class DicionarioResolverStaticTest extends MDicionarioResolverSerializable {
        @SuppressWarnings("unused")
        private Object ref;

        public DicionarioResolverStaticTest(Object ref) {
            this.ref = ref;

        }
        @Override
        public Optional<MDicionario> loadDicionaryForType(String typeName) {
            MDicionario novo = MDicionario.create();
            novo.criarNovoPacote("teste").createTipo("cadastro", MTipoString.class);
            return Optional.of(novo);
        }

    }

    private static void testSerializacaoComResolverSerializado(MInstancia original, MDicionarioResolverSerializable resolver) {
        testSerializacao(original, i -> FormSerializationUtil.toSerializedObject(i, resolver), fs -> FormSerializationUtil.toInstance(fs));
    }

    public static MInstancia testSerializacao(MInstancia original, MDicionarioResolver loader) {
        return testSerializacao(original, i -> FormSerializationUtil.toSerializedObject(i),
                fs -> FormSerializationUtil.toInstance(fs, loader));
    }

    private static MInstancia testSerializacao(MInstancia original, Function<MInstancia, FormSerialized> toSerial,
            Function<FormSerialized, MInstancia> fromSerial) {
        // Testa sem transformar em array de bytes
        FormSerialized fs = toSerial.apply(original);
        MInstancia instancia2 = fromSerial.apply(fs);
        assertEquivalent(original.getDocument(), instancia2.getDocument());
        assertEquivalent(original, instancia2);

        // Testa transformando em um array de bytes
        try {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(out1);
            out2.writeObject(fs);
            out2.close();

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(out1.toByteArray()));
            fs = (FormSerialized) in.readObject();
            instancia2 = fromSerial.apply(fs);
            assertEquivalent(original.getDocument(), instancia2.getDocument());
            assertEquivalent(original, instancia2);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return instancia2;
    }

    public static MInstancia serializarEDeserializar(MInstancia original, MDicionarioResolver loader) {
        return serializarEDeserializar(original, i -> FormSerializationUtil.toSerializedObject(i),
                fs -> FormSerializationUtil.toInstance(fs, loader));
    }

    private static MInstancia serializarEDeserializar(MInstancia original, Function<MInstancia, FormSerialized> toSerial,
            Function<FormSerialized, MInstancia> fromSerial) {
        try {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(out1);
            out2.writeObject(toSerial.apply(original));
            out2.close();

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(out1.toByteArray()));
            return fromSerial.apply((FormSerialized) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static MDicionarioResolver createLoaderPacoteTeste(Consumer<PacoteBuilder> setupCode) {
        return new MDicionarioLoader() {
            @Override
            protected void configDicionary(MDicionario newDicionary, String taregetTypeName) {
                setupCode.accept(newDicionary.criarNovoPacote("teste"));
            }
        };
    }

    public static void assertEquivalent(SDocument original, SDocument novo) {
        assertNotSame(original, novo);
        assertEquals(original.getLastId(), novo.getLastId());

        for (Entry<String, Pair> service : original.getServices().entrySet()) {
            Object originalService = original.lookupService(service.getKey(), Object.class);
            Object novoService = novo.lookupService(service.getKey(), Object.class);
            if (originalService == null) {
                assertNull(novoService);
            } else if (novoService == null && !(service.getValue().provider instanceof ServiceRefTransientValue)) {
                fail("O documento deserializado para o serviço '" + service.getKey() + "' em vez de retorna uma instancia de "
                        + originalService.getClass().getName() + " retornou null");
            }

        }

        assertEquivalent(original.getRoot(), novo.getRoot());
    }

    private static void assertEquivalent(MInstancia original, MInstancia novo) {
        assertNotSame(original, novo);
        assertEquals(original.getClass(), novo.getClass());
        assertEquals(original.getMTipo().getNome(), novo.getMTipo().getNome());
        assertEquals(original.getMTipo().getClass(), novo.getMTipo().getClass());
        assertEquals(original.getNome(), novo.getNome());
        assertEquals(original.getId(), novo.getId());
        assertEquals(original.getPathFull(), novo.getPathFull());
        if (original.getPai() != null) {
            assertNotNull(novo.getPai());
            assertEquals(original.getPai().getPathFull(), novo.getPai().getPathFull());
        } else {
            assertNull(novo.getPai());
        }
        if (original instanceof ICompositeInstance) {
            List<MInstancia> filhosOriginal = new ArrayList<>(((ICompositeInstance) original).getChildren());
            List<MInstancia> filhosNovo = new ArrayList<>(((ICompositeInstance) novo).getChildren());
            assertEquals(filhosOriginal.size(), filhosNovo.size());
            for (int i = 0; i < filhosOriginal.size(); i++) {
                assertEquivalent(filhosOriginal.get(0), filhosNovo.get(0));
            }
        } else {
            assertEquals(original.getValor(), novo.getValor());
        }

        assertEquals(original.getAtributos().size(), novo.getAtributos().size());
        for (Entry<String, MInstancia> atrOriginal : original.getAtributos().entrySet()) {
            MInstancia atrNovo = novo.getAtributos().get(atrOriginal.getKey());
            assertNotNull(atrNovo);
            assertEquals(atrOriginal.getValue(), atrNovo);
        }
    }
}
