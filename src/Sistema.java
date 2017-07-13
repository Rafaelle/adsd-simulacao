import java.util.ArrayList;

public class Sistema {
	
	private static Fila fila1;
	private static Fila fila2;
	private static Servidor servidor;
	private int proximaChegadaF1;
	private int proximaChegadaF2;

	public static void main(String[] args) {
	
		int tempoSimulacao = 10;
		int tempoDecorrido = 0;
		
		//Filas
		fila1 = new Fila(1, 10);
		fila1.setProximaChegada(tempoDecorrido);
		fila2 = new Fila(1, 5);
		fila2.setProximaChegada(tempoDecorrido);

		//Servidor
		servidor = new Servidor(3,7);	
		

		
		while(tempoDecorrido <= tempoSimulacao){
			tempoDecorrido +=1;	
				
			/*Chegada de cliente na fila 1
				1-Escalona pr�xima chegada (Rand�mico entre 1 e 10)
				2-Testa estado do servidor
					Livre: Mude estado para ocupado
						Escalone t�rmino de servi�o (rand�mico entre 3 e 7)
					Ocupado: Escalone evento �colocar fregu�s na fila�
			*/
			if (fila1.getProximaChegada() == tempoDecorrido){
				printEvento("Chegada", tempoDecorrido);
				fila1.setProximaChegada(tempoDecorrido);
				
				if(servidor.livre()){
					// O tempo de 't�rmino de servi�o' � calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila1.atendeCliente();
				} else {
					fila1.addCliente(tempoDecorrido);
				}
			} 
			
			/*Chegada de cliente na fila 2
			1-Escalona pr�xima chegada (Rand�mico entre 1 e 5)
			2-Testa estado do servidor
				Livre: Mude estado para ocupado
					Escalone t�rmino de servi�o (rand�mico entre 3 e 7)
				Ocupado: Escalone evento �colocar fregu�s na fila�
			 */
			
			if (fila2.getProximaChegada() == tempoDecorrido){
				printEvento("Chegada", tempoDecorrido);

				fila2.setProximaChegada(tempoDecorrido);
				//TODO VERIFICAR SE � PRECISO CHECAR SE A FILA 1 TEM GENTE
				if(servidor.livre() && fila1.getQtdClientes()==0){
					// O tempo de 't�rmino de servi�o' � calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila2.atendeCliente();
				} else {
					fila1.addCliente(tempoDecorrido);
				}
			}
			
			
			/* T�rmino de servi�o
				1-Teste estado das filas de espera
					Vazia: Mude estado servidor para �livre�
					N�o vazia:
						Remova fregu�s da fila	
						Escalone evento �T�rmino de servi�o� (rand�mico entre 3 e 7)
			*/
			
			if(servidor.getTempoServico() == tempoDecorrido){
				printEvento("Sa�da", tempoDecorrido);
				if(fila1.getQtdClientes()==0 && fila2.getQtdClientes()==0){
					servidor.teminarAtendimento();
				} else if(fila1.getQtdClientes() > 0){
					// O tempo de 't�rmino de servi�o' � calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila1.atendeCliente();
				} else if(fila2.getQtdClientes() > 0){
					// O tempo de 't�rmino de servi�o' � calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila2.atendeCliente();
				}
			}
			
			
			if(servidor.livre()){
				if(fila1.getQtdClientes() > 0){
					// O tempo de 't�rmino de servi�o' � calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila1.atendeCliente();
				} else if(fila2.getQtdClientes() > 0){
					// O tempo de 't�rmino de servi�o' � calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila2.atendeCliente();
				}
			}
			
			
		} 
			
	}
	
	
	private static void printEvento(String evento, int tempoDecorrido){
		/*Tipo de evento: Chegada/Sa�da, Momento do evento: Z
		Elementos na Fila 1: X
		Elementos na Fila 2: Y
		Elemento no servi�o: A
	*/
		System.out.println("Tipo de evento: " + evento +", Momento do evento: " + tempoDecorrido);
		System.out.println("Elementos na Fila 1:" + fila1.getQtdClientes());
		System.out.println("Elementos na Fila 2:" + fila1.getQtdClientes());
		// TODO N�o entendi esse
		System.out.println("Elemento no servi�o:" );
		System.out.println("------------------------------------------------------" );

	}

}
