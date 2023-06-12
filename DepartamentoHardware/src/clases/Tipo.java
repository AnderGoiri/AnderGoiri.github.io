package clases;

public enum Tipo {
	P("Portátil"), S("Sobremesa");

	private final String label;

	Tipo(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
