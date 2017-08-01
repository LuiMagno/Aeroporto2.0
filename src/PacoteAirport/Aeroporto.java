package PacoteAirport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/* Feito por Lui Magno - Universidade Estadual do Ceará - Programação Paralela e Concorrente
   19/06/17
 */
	
public class Aeroporto extends Thread {
	// Listas e Semáforo usados para o controle dos Aviões
	public static Semaphore semaforo = new Semaphore(1);
	public static ArrayList <Thread> AvioesPrincipal = new ArrayList<Thread>();
	public static LinkedList <Thread> AvioesPousando = new LinkedList<Thread>();
	public static LinkedList <Thread> AvioesDecolando = new LinkedList<Thread>();
	public static int contadorAvioesP = 0;
	public static int contadorAvioesD = 0;
	
	public static void main(String[] args) throws InterruptedException{
		//Criação da Thread Tempo para controlar o tempo de execução do programa
		Thread threadT = new Tempo();
		threadT.start();
		// Loop para a criação das Threads
		for(int i = 0; i<20 ; i++){
			
			int decision = 1 + (int)(Math.random() *(2));
			switch(decision){
				case 1:
					if(contadorAvioesP<10){
						Thread threadX = new Aviao(i);
						AvioesPrincipal.add(threadX);
						AvioesPousando.add(threadX);
						((Aviao) threadX).setEstado(true);
						((Aviao) threadX).setId(i);
						tempoCriacao(threadX);
						threadX.start();
						contadorAvioesP++;
						
					break;
					}else if(contadorAvioesD<10 && AvioesDecolando.size()<3){
						Thread threadX = new Aviao(i);
						AvioesPrincipal.add(threadX);
						AvioesDecolando.add(threadX);
						((Aviao) threadX).setEstado(false);
						((Aviao) threadX).setId(i);
						tempoCriacao(threadX);
						threadX.start();
						contadorAvioesD++;
						break;
					}else{
						System.out.println("Impedindo criação!");
						i--;
						break;
					}
				case 2:
					if(contadorAvioesD<10 && AvioesDecolando.size()<3){
						Thread threadX = new Aviao(i);
						AvioesPrincipal.add(threadX);
						AvioesDecolando.add(threadX);
						((Aviao) threadX).setEstado(false);
						((Aviao) threadX).setId(i);
						tempoCriacao(threadX);
						threadX.start();
						contadorAvioesD++;
						break;
					}else if(contadorAvioesP<10){
						Thread threadX = new Aviao(i);
						AvioesPrincipal.add(threadX);
						AvioesPousando.add(threadX);
						((Aviao) threadX).setEstado(true);
						((Aviao) threadX).setId(i);
						tempoCriacao(threadX);
						threadX.start();
						contadorAvioesP++;
						break;
					}else{
						System.out.println("Impedindo criação!");
						i--;
						break;
					}
						
					
			}
			sleep(8000);
		}
		//Evidenciando a criação de todas as threads
		System.out.println("----------------------------------------------\nNumero de avioes ceu "+contadorAvioesP);
		System.out.println("Numero de avioes terra "+contadorAvioesD);
		System.out.println("Todos os aviões criados!\n");
		
	}
					
				
			
		
	//Método usado para controlar o tempo inicial da Thread de Avião
	public static void tempoCriacao(Thread  airplane){
		if(((Aviao) airplane).getEstado()){
			System.out.println("Aviao " + airplane.getId() + " criado. Status = Voando. ("+(int) (airplane.getId()*8)+"s)");
			((Aviao) airplane).setTempoInicial((int) (airplane.getId()*8));
		}else if (!((Aviao) airplane).getEstado()){
			System.out.println("Aviao " + airplane.getId() + " criado. Status = Decolando. ("+(int) (airplane.getId()*8)+"s)");
			((Aviao) airplane).setTempoInicial((int) (airplane.getId()*8));
		}
	}
	
	//Método usado pela Thread Aviao para iniciar e finalizar os procedimentos de pouso e decolagem
	public static void tempoOperacao(Aviao airplane) throws InterruptedException{
		if(((Aviao) airplane).getEstado()){
			System.out.println("Aviao " + ((Aviao) airplane).getId()+" pousando. ("+((Aviao) airplane).getTempo()+"s)");
			((Aviao) airplane).setTempoFinal(((Aviao) airplane).getTempo());
			sleep(10000);
			System.out.println("Aviao " + ((Aviao) airplane).getId()+" pousou e liberou a pista. (" +((Aviao) airplane).getTempo()+"s)");
			Aeroporto.AvioesPousando.remove(airplane);
			
		}else{
			System.out.println("Aviao " + ((Aviao) airplane).getId()+" decolando. ("+((Aviao) airplane).getTempo()+"s)");
			((Aviao) airplane).setTempoFinal(((Aviao) airplane).getTempo());
			sleep(10000);
			System.out.println("Aviao " + ((Aviao) airplane).getId()+" decolou e liberou a pista. ("+((Aviao) airplane).getTempo()+"s)");
			Aeroporto.AvioesDecolando.remove(airplane);
		}

	}

	
	

	
	
	
	
	//Getters and Setters
	public static int getContadorAvioesP() {
		return contadorAvioesP;
	}
	public static void setX(int contadorAvioesP) {
		Aeroporto.contadorAvioesP =contadorAvioesP;
	}
	public static int getContadorAvioesD() {
		return contadorAvioesD;
	}
	public static void setContadorAvioesD(int contadorAvioesD) {
		Aeroporto.contadorAvioesD = contadorAvioesD;
	}
}
