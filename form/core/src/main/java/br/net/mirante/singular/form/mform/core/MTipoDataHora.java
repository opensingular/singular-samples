package br.net.mirante.singular.form.mform.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import br.net.mirante.singular.form.mform.MInfoTipo;
import br.net.mirante.singular.form.mform.MTipoSimples;

@MInfoTipo(nome = "DataHora", pacote = MPacoteCore.class)
public class MTipoDataHora extends MTipoSimples<MIData, Date> {

    private static final Logger LOGGER = Logger.getLogger(MIData.class.getName());

    public static final String FORMAT = "dd/MM/yyyy HH:mm";

    public MTipoDataHora() {
        super(MIData.class, Date.class);
    }

    protected MTipoDataHora(Class<? extends MIData> classeInstancia) {
        super(classeInstancia, Date.class);
    }

    public Date fromString(String valor) {
        try {
            if (Strings.isNullOrEmpty(valor)) return null;
            return (new SimpleDateFormat(FORMAT)).parse(valor);
        } catch (ParseException e) {
            String msg = String.format("Can't parse value '%s' with format '%s'.", valor, FORMAT);
            LOGGER.log(Level.WARNING, msg, e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    protected String toStringPersistencia(Date valorOriginal) {
        return (new SimpleDateFormat(FORMAT)).format(valorOriginal);
    }

}