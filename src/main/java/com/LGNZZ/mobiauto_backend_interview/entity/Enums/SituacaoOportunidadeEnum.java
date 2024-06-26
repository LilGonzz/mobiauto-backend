package com.LGNZZ.mobiauto_backend_interview.entity.Enums;

public enum SituacaoOportunidadeEnum implements EnumLabel{
    NOVO("Nova oportunidade"),
    EM_ATENDIMENTO("Oportunidade em andamento"),
    CONCLUIDO("Oportunidade em andamento");

    private String label;

    private SituacaoOportunidadeEnum(String label) {
        this.label = label;
    }

    @Override
    public Object getValue() { return name(); }

    @Override
    public String getLabel() { return label; }
}
