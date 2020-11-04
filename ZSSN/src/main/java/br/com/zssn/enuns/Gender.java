package br.com.zssn.enuns;

public enum Gender {
	M("Masculino"), F("Feminino");

	private String value;

	private Gender(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
