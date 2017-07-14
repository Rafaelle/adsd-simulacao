import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Sistema {
	
	private static Fila fila1;
	private static Fila fila2;
	private static Servidor servidor;
	private int proximaChegadaF1;
	private int proximaChegadaF2;
	
	private static File file;
	private static BufferedWriter writer;
	
	private static final String caminho_arquivo = "saida_execucao.txt";

	public static void main(String[] args) throws IOException {
	
		int tempoSimulacao = 10;
		int tempoDecorrido = 0;
		
		//Filas
		fila1 = new Fila(1, 10);
		fila1.setProximaChegada(tempoDecorrido);
		fila2 = new Fila(1, 5);
		fila2.setProximaChegada(tempoDecorrido);

		//Servidor
		servidor = new Servidor(3,7);
		
		//Escrita da saída
		file = new File(caminho_arquivo);
		writer = new BufferedWriter(new FileWriter(file));
		
		while(tempoDecorrido <= tempoSimulacao){
			tempoDecorrido +=1;	
				
			/*Chegada de cliente na fila 1
				1-Escalona próxima chegada (Randômico entre 1 e 10)
				2-Testa estado do servidor
					Livre: Mude estado para ocupado
						Escalone término de serviço (randômico entre 3 e 7)
					Ocupado: Escalone evento “colocar freguês na fila”
			*/
			if (fila1.getProximaChegada() == tempoDecorrido){
				
				fila1.setProximaChegada(tempoDecorrido);
				fila1.addCliente(tempoDecorrido);
				escreveNoArquivo("Chegada", tempoDecorrido, 1);
				
				if(servidor.livre()){
					// O tempo de 'término de serviço' é calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila1.atendeCliente();
					escreveNoArquivo("Saida", tempoDecorrido, 1);
				}
			} 
			
			/*Chegada de cliente na fila 2
			1-Escalona próxima chegada (Randômico entre 1 e 5)
			2-Testa estado do servidor
				Livre: Mude estado para ocupado
					Escalone término de serviço (randômico entre 3 e 7)
				Ocupado: Escalone evento “colocar freguês na fila”
			 */
			if (fila2.getProximaChegada() == tempoDecorrido){
				fila2.setProximaChegada(tempoDecorrido);
				fila2.addCliente(tempoDecorrido);
				escreveNoArquivo("Chegada", tempoDecorrido, 2);
				if(servidor.livre() && fila1.getQtdClientes()==0){
					// O tempo de 'término de serviço' é calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila2.atendeCliente();
					escreveNoArquivo("Saida", tempoDecorrido, 2);
				}
			}
			
			/* Término de serviço
				1-Teste estado das filas de espera
					Vazia: Mude estado servidor para “livre”
					Não vazia:
						Remova freguês da fila	
						Escalone evento “Término de serviço” (randômico entre 3 e 7)
			*/
			if(servidor.getTempoServico() == tempoDecorrido){
				if(fila1.getQtdClientes()==0 && fila2.getQtdClientes()==0){
					servidor.teminarAtendimento();
				} else if(fila1.getQtdClientes() > 0){
					// O tempo de 'término de serviço' é calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila1.atendeCliente();
					escreveNoArquivo("Saída", tempoDecorrido, 1);
				} else if(fila2.getQtdClientes() > 0){
					// O tempo de 'término de serviço' é calculado dentro de atender()
					servidor.atender(tempoDecorrido);
					fila2.atendeCliente();
					escreveNoArquivo("Saída", tempoDecorrido, 2);
				}
			}
			
			if(servidor.livre()){
				if(fila1.getQtdClientes() > 0){
					servidor.atender(tempoDecorrido);
					escreveNoArquivo("Saída", tempoDecorrido, 1); // se der algum erro na execução, inverter essa linha com a de baixo.
					fila1.atendeCliente();
				} else if(fila2.getQtdClientes() > 0){
					servidor.atender(tempoDecorrido);
					escreveNoArquivo("Saída", tempoDecorrido, 2); // se der algum erro na execução, inverter essa linha com a de baixo.
					fila2.atendeCliente();
				}	
			} 
		}
		
		encerraEscrita();
	}
	
	// Escreve a saída no arquivo saida_execucao.txt.
	private static void escreveNoArquivo(String evento, int tempoDecorrido, int elementoNoServico) throws IOException{
		/*Tipo de evento: Chegada/Saída, Momento do evento: Z
		Elementos na Fila 1: X
		Elementos na Fila 2: Y
		Elemento no serviço: A
	*/
		
		writer.write("Tipo de evento: " + evento +", Momento do evento: " + tempoDecorrido);
		writer.newLine();
		writer.write("Elementos na Fila 1: " + fila1.getQtdClientes());
		writer.newLine();
		writer.write("Elementos na Fila 2: " + fila2.getQtdClientes());
		
		writer.newLine();
		writer.write("Elemento no serviço: " + elementoNoServico);
		writer.newLine();
		writer.write("------------------------------------------------------" );
		writer.newLine();
		
		//Criando o conteúdo do arquivo
		writer.flush();
	}
	
	private static void encerraEscrita() throws IOException {
		//Fechando conexão e escrita do arquivo.
		writer.close();
		System.out.println("A saída foi salva no arquivo " + caminho_arquivo);
	}
	
}
