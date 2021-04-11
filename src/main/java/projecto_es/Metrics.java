package projecto_es;

public enum Metrics {
	LOC_CLASS ("LOC_CLASS"),
	NOM_CLASS ("NOM_CLASS"),
	WMC_CLASS ("WMC_CLASS"),
	LOC_METHOD ("LOC_METHOD"),
	CYCLO_METHOD ("CYCLO_METHOD")
	;

	private String metric;
	
	Metrics(String string) {
		this.metric=metric;
	}
}
