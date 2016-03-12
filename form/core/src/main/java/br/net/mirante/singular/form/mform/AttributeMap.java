package br.net.mirante.singular.form.mform;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

class AttributeMap implements Iterable<SAttribute> {

    private Map<String, SAttribute> attributes;

    final void add(SAttribute atributo) {
        if (attributes == null) {
            attributes = new LinkedHashMap<>();
        } else if (attributes.containsKey(atributo.getName())) {
            throw new RuntimeException("Já existe um atributo '" + atributo.getName() + "' definido");
        }
        attributes.put(atributo.getName(), atributo);
    }

    public SAttribute get(String name) {
        if (attributes == null) {
            return null;
        }
        return attributes.get(name);
    }

    public Collection<SAttribute> getAttributes() {
        if (attributes == null) {
            return Collections.emptyList();
        }
        return attributes.values();
    }

    @Override
    public Iterator<SAttribute> iterator() {
        if (attributes == null) {
            return Collections.emptyListIterator();
        }
        return attributes.values().iterator();
    }

}
