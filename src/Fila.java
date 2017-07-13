import java.util.ArrayList;

public class Fila {
	
	private int tempoChegadaMinimo;
	private int tempoChegadaMaximo;
	private ArrayList<Integer> clientes;
	private int proximaChegada;

	
	public Fila(int tempoChegadaMinino, int tempoChegadaMaximo){
		this.tempoChegadaMinimo = tempoChegadaMinino;
		this.tempoChegadaMaximo = tempoChegadaMaximo;
		clientes = new ArrayList<Integer>();
	}

	public void addCliente(int tempoDecorrido){
		clientes.add(1);
		setProximaChegada(tempoDecorrido);
	}
	
	public void atendeCliente(){
		if (clientes.size() > 0){
			clientes.remove(0);
		}
	}

	public int getProximaChegada() {
		return proximaChegada;
	}

	public void setProximaChegada(int tempoDecorrido) {
		this.proximaChegada = tempoDecorrido +
		(int)((Math.random()*(tempoChegadaMaximo + 1 - tempoChegadaMinimo)) + tempoChegadaMinimo);
	}
	
	public int getQtdClientes(){
		return clientes.size();
	}

	
}
