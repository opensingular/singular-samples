package br.net.mirante.singular.exemplos.notificacaosimplificada.form.vocabulario;

import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.EmbalagemPrimariaBasica;
import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.generic.VocabularioControlado;
import org.opensingular.singular.form.SIComposite;
import org.opensingular.singular.form.SInfoType;
import org.opensingular.singular.form.STypeComposite;
import org.opensingular.singular.form.TypeBuilder;
import org.opensingular.singular.form.converter.SInstanceConverter;
import org.opensingular.singular.form.type.core.STypeInteger;
import org.opensingular.singular.form.type.core.STypeString;
import org.opensingular.singular.form.util.transformer.Value;

import static br.net.mirante.singular.exemplos.notificacaosimplificada.form.vocabulario.SPackageVocabularioControlado.dominioService;

@SInfoType(spackage = SPackageVocabularioControlado.class)
public class STypeEmbalagemPrimaria extends STypeComposite<SIComposite> {

    public STypeString  descricao;
    public STypeInteger id;

    @Override
    protected void onLoadType(TypeBuilder tb) {
        super.onLoadType(tb);
        id = this.addFieldInteger("id");
        descricao = this.addFieldString("descricao");
        {
            this
                    .asAtrBootstrap()
                    .colPreference(6)
                    .asAtr()
                    .label("Embalagem primária")
                    .required();
            this.autocompleteOf(EmbalagemPrimariaBasica.class)
                    .id("${id}")
                    .display(VocabularioControlado::getDescricao)
                    .converter(new SInstanceConverter<EmbalagemPrimariaBasica, SIComposite>() {
                        @Override
                        public void fillInstance(SIComposite ins, EmbalagemPrimariaBasica obj) {
                            ins.setValue(id, obj.getId());
                            ins.setValue(descricao, obj.getDescricao());
                        }

                        @Override
                        public EmbalagemPrimariaBasica toObject(SIComposite ins) {
                            return dominioService(ins).findEmbalagensBasicas(null)
                                    .stream().filter(u -> Integer.valueOf(u.getId().intValue()).equals(Value.of(ins, id)))
                                    .findFirst()
                                    .orElse(null);
                        }
                    })
                    .filteredProvider((ins, query) -> dominioService(ins).findEmbalagensBasicas(query));
        }
    }


}
