package br.ufpe.cin.model;

public enum StringVariables {
	ACAO_WRITE("WRITE"),
	ACAO_READ("READ"),
	
	EVENTO_TRANSACAO("TRANSACAO"),
	EVENTO_ACAO("ACAO"),
	EVENTO_CHECKPOINT("CHECKPOINT"),
	EVENTO_VARIAVEL("VARIAVEL"),
	
	TRANSACAO_INICIO("INICIO"),
	TRANSACAO_ACAO("ACAO"),
	TRANSACAO_ABORT("ABORT"),
	TRANSACAO_COMMIT("COMMIT"),

	END("END");
	
	 private String value;
	 public String getValue() {
	    return value;
	   }
	 private StringVariables (String value) {
	  this.value = value;
	 } 

}
