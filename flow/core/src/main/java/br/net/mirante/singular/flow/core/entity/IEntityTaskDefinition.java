package br.net.mirante.singular.flow.core.entity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface IEntityTaskDefinition extends IEntityByCod {

    IEntityProcessDefinition getProcessDefinition();

    String getAbbreviation();

    void setAbbreviation(String abbreviation);

    List<? extends IEntityTaskVersion> getVersions();

    default IEntityTaskVersion getLastVersion(){
        return getVersions().stream().collect(Collectors.maxBy(Comparator.comparing(IEntityTaskVersion::getVersionDate))).get();
    }
}
