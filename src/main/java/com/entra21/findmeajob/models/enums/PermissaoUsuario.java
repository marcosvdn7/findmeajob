package com.entra21.findmeajob.models.enums;

public enum PermissaoUsuario {

	ADMIN(0), 
	USUARIO(1);
	
	private int codigo;
	
	private PermissaoUsuario(int codigo) {
		this.codigo = codigo;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}
	
	public static PermissaoUsuario valueOf(Integer codigo) {
		for (PermissaoUsuario value : PermissaoUsuario.values()) {
			if (value.getCodigo() == codigo) {
				return value;
			}
		}
		throw new IllegalArgumentException("Código de permissão inválido");
	}
	
}
