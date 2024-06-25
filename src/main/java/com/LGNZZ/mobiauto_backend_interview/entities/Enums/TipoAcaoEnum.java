package com.LGNZZ.mobiauto_backend_interview.entities.Enums;

public enum TipoAcaoEnum implements EnumLabel{
    NOVA_ATRIBUICAO("Nova atribuição ao atendimento"),
    ALTERACAO_RESPONSAVEL("Alteração de responsável do atendimento"),
    AGUARDANDO_RESPONSAVEL("Aguardando responsável"),
    CONCLUSAO("Atendimento concluído");

    private String label;

    private TipoAcaoEnum(String label) {
        this.label = label;
    }

    @Override
    public Object getValue() { return name(); }

    @Override
    public String getLabel() { return label; }
}
