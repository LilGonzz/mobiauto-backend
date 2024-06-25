package com.LGNZZ.mobiauto_backend_interview.entity.Enums;

public enum RoleEnum implements EnumLabel{
    ADMINISTRADOR("Usuario Administrador"),
    PROPRIETARIO("Usuario Proprietario"),
    GERENTE("Usuario Gerente"),
    ASSISTENTE("Usuario Assistente"),;


    private String label;

    private RoleEnum(String label) {
        this.label = label;
    }
    @Override
    public Object getValue() { return name(); }

    @Override
    public String getLabel() { return label; }
}
