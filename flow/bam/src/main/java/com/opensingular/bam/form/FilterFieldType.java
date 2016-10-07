/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.opensingular.bam.form;

import com.opensingular.bam.client.portlet.FilterConfig;
import com.opensingular.bam.client.portlet.filter.AggregationPeriod;
import com.opensingular.bam.client.portlet.filter.FieldType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.STypeSimple;
import org.opensingular.form.converter.SInstanceConverter;
import org.opensingular.form.type.core.SIString;
import org.opensingular.form.type.core.STypeString;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.util.StringUtils.isEmpty;

public enum FilterFieldType {

    BOOLEAN(FieldType.BOOLEAN) {
        @Override
        protected STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                           STypeComposite root) {
            return root.addFieldBoolean(fc.getIdentifier());
        }
    },

    INTEGER(FieldType.INTEGER) {
        @Override
        protected STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                           STypeComposite root) {
            return root.addFieldInteger(fc.getIdentifier());
        }
    },

    TEXT(FieldType.TEXT) {
        @Override
        protected STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                           STypeComposite root) {
            return root.addFieldString(fc.getIdentifier());
        }
    },

    TEXTAREA(FieldType.TEXTAREA) {
        @Override
        protected STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                           STypeComposite root) {
            return root.addFieldString(fc.getIdentifier()).withTextAreaView();
        }
    },

    SELECTION(FieldType.SELECTION) {
        @Override
        protected STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                           STypeComposite root) {
            final STypeString simpleType = root.addFieldString(fc.getIdentifier());
            if (!isEmpty(fc.getRestEndpoint()) && !isEmpty(groupConnectionURL)) {
                final String connectionURL = groupConnectionURL + fc.getRestEndpoint();
                switch (fc.getRestReturnType()) {
                    case VALUE:
                        simpleType.selectionOf(String.class)
                                .selfIdAndDisplay()
                                .simpleProvider(ins -> {
                                    final RestTemplate restTemplate = new RestTemplate();
                                    return restTemplate.getForObject(connectionURL, List.class);
                                });
                        break;
                    case KEY_VALUE:
                        simpleType.selectionOf(Pair.class)
                                .id("${left}")
                                .display("${right}")
                                .converter(new SInstanceConverter<Pair, SIString>() {
                                    @Override
                                    public void fillInstance(SIString ins, Pair obj) {
                                        ins.setValue(obj.getLeft());
                                    }

                                    @Override
                                    public Pair toObject(SIString ins) {
                                        final RestTemplate        restTemplate = new RestTemplate();
                                        final Map<String, String> map          = restTemplate.getForObject(connectionURL, Map.class);
                                        for (Map.Entry<String, String> entry : map.entrySet()) {
                                            if (entry.getKey().equals(ins.getValue())) {
                                                return Pair.of(entry.getKey(), entry.getValue());
                                            }
                                        }
                                        return null;
                                    }
                                })
                                .simpleProvider(ins -> {
                                    final RestTemplate        restTemplate = new RestTemplate();
                                    final Map<String, String> map          = restTemplate.getForObject(connectionURL, Map.class);
                                    final List<Pair>          pairs        = new ArrayList<>();
                                    map.forEach((k, v) -> pairs.add(Pair.of(k, v)));
                                    return pairs;
                                });
                        break;
                }
            } else if (fc.getOptions() != null && fc.getOptions().length > 0) {
                simpleType.selectionOf(String.class)
                        .selfIdAndDisplay()
                        .simpleProviderOf(fc.getOptions());
            }
            return simpleType;
        }
    },

    DATE(FieldType.DATE) {
        @Override
        protected STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                           STypeComposite root) {
            return root.addFieldDate(fc.getIdentifier());
        }
    },

    AGGREGATION_PERIOD(FieldType.AGGREGATION_PERIOD) {
        @Override
        protected STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                           STypeComposite root) {
            final STypeString typeSimple = root.addFieldString(fc.getIdentifier());
            typeSimple.selectionOf(AggregationPeriod.class)
                    .id(AggregationPeriod::getDescription)
                    .display(AggregationPeriod::getDescription)
                    .converter(new SInstanceConverter<AggregationPeriod, SIString>() {
                        @Override
                        public void fillInstance(SIString ins, AggregationPeriod obj) {
                            ins.setValue(obj.getDescription());
                        }

                        @Override
                        public AggregationPeriod toObject(SIString ins) {
                            return Arrays.stream(AggregationPeriod.values())
                                    .filter(ap -> ap.getDescription().equals(ins.getValue()))
                                    .findFirst().orElse(null);
                        }
                    })
                    .simpleProviderOf(AggregationPeriod.values());
            return typeSimple;
        }
    };

    private FieldType fieldType;

    FilterFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void addField(String groupConnectionURL, FilterConfig fc, STypeComposite root) {
        final STypeSimple sType = addFieldImpl(groupConnectionURL, fc, root);
        if (sType != null) {
            sType.asAtr().label(fc.getLabel());
            sType.asAtrBootstrap().colPreference(fc.getSize());
            sType.asAtr().required(fc.getRequired());
        }
    }

    protected abstract STypeSimple addFieldImpl(String groupConnectionURL, FilterConfig fc,
                                                STypeComposite root);

    public static Optional<FilterFieldType> valueOfFieldType(FieldType fieldType) {
        return Arrays.asList(values()).stream().filter(f -> f.fieldType == fieldType).findFirst();
    }

}