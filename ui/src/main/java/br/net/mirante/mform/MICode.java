package br.net.mirante.mform;

public class MICode extends MInstancia {

    private Object code;

    @Override
    public Object getValor() {
        return code;
    }

    @Override
    public boolean isNull() {
        return code != null;
    }

}
