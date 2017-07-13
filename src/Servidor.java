
public class Servidor {
	
	private int tempoMininoServico;
	private int tempoMaximoServico;
	private int tempoServico;
	private boolean livre;

	public Servidor(int tempoMininoServico, int tempoMaximoServico){
		this.tempoMininoServico = tempoMininoServico;
		this.tempoMaximoServico = tempoMaximoServico;
		livre = true;
	}
	
	
	public void atender(int tempoDecorrido){
		this.livre = false;
		setTempoServico(tempoDecorrido);
	}
	
	public int getTempoServico() {
		return tempoServico;
	}
	

	public void teminarAtendimento(){
		livre = true;
	}


	public boolean livre() {
		return livre;
	}
	
	public void setTempoServico(int tempoDecorrido){
		this.tempoServico = tempoDecorrido +
				(int)((Math.random()*(tempoMaximoServico + 1 - tempoMininoServico)) + tempoMininoServico);
	}

}
