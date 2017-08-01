package PacoteAirport;



public class Aviao extends Thread {
	
	private int id; // Inteiro usado como ID dos Aviões
	private boolean estado; // Boolean que controla o estado da Thread: se true(voando) se false(decolando)
	private int tempoInicial;
	private int tempoFinal;
	private int tempo;
	private static int contador = 0; // Contador utilizado para detectar o fim de todas as threads
	private static boolean cair; // Variável que faz o controle de aviões que estão perto de cair
	// Construtor 
	public Aviao (int id){
		this.id = id;
	}
	
	public void run(){ // Método run para as ações da Thread
		try{
			while(true){
				if(permissaoPista(this)){ // 
					break;
				}else{
					sleep(100);
					 }
			}
			
			Aeroporto.semaforo.acquire();
			Aeroporto.tempoOperacao(this);
			
	
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			Aeroporto.semaforo.release();
			if(this.estado == true){
				cair = false;
			}
			contador++;
			
			
		}
		if(contador == 20){
			// Mostrando todos os aviões com seus respectivos tempos
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("Tempo total de todos os Aviões: ");
			tempoFinal();
			
		}
	}
	
	
	
	
	
	
	
		
	public static boolean permissaoPista(Aviao airplane){ // Método que decide quem poderá entrar na região crítica
		int tempo = airplane.tempo - airplane.tempoInicial;
		if(airplane.estado && Aeroporto.contadorAvioesP == 10){
			if(tempo>19){
				cair = true;
				System.out.println("\nAviao: "+airplane.id+". Preciso acessar a pista! Pouco combustível!\n");
				return true;
			}else{
				return false;
			}
		}else
		if(!airplane.estado){
			if(Aeroporto.AvioesPousando.size()>0 && Aeroporto.AvioesDecolando.size()<3){
				return false;
			}else if(airplane == Aeroporto.AvioesDecolando.getFirst()&& cair ==false ){
				
				return true;
			}
			return false;
			
		}else
		if(Aeroporto.AvioesDecolando.size()>2 && Aeroporto.contadorAvioesD <10 ){// Se todos os aviões da terra já foram criados, o avião pode pousar.
			return false;
		
		}
		else if( airplane == Aeroporto.AvioesPousando.getFirst()){
			return true;
		}
		return false;
		
		
	}
	
	public static void tempoFinal(){ // Método responsável por mostrar o tempo total dos aviões
		int totalAviao = 0;
		int mediaTotalPousando = 0;
		int mediaTotalDecolando = 0;
		for(int i = 0; i<Aeroporto.AvioesPrincipal.size(); i++){
			int tempI = ((Aviao)Aeroporto.AvioesPrincipal.get(i)).tempoInicial;
			int tempF = ((Aviao)Aeroporto.AvioesPrincipal.get(i)).tempoFinal;
			totalAviao = tempF - tempI;
			if(((Aviao) Aeroporto.AvioesPrincipal.get(i)).getEstado()){
				System.out.println("O Aviao "+((Aviao) Aeroporto.AvioesPrincipal.get(i)).getId()+" pousou com o tempo de: "+totalAviao+"s");
				mediaTotalPousando +=totalAviao;
			}else{
				System.out.println("O Aviao "+((Aviao) Aeroporto.AvioesPrincipal.get(i)).getId()+" decolou com o tempo de: "+totalAviao+"s");
				mediaTotalDecolando +=totalAviao;
			}
		}
		System.out.println("--------------------------------------------------------------------------\nMedias:");
		System.out.println("Media aproximada de tempo de Avioes que Pousaram: "+mediaTotalPousando/10+"s");
		System.out.println("Media aproximada de tempo de Avioes que Decolaram: "+mediaTotalDecolando/10+"s");
	}
			
	
	
	
	
	public boolean getEstado() {
		return estado;
	}
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public int getTempoInicial() {
		return tempoInicial;
	}
	public void setTempoInicial(int tempoInicial) {
		this.tempoInicial = tempoInicial;
	}
	public int getTempoFinal() {
		return tempoFinal;
	}
	public void setTempoFinal(int tempoFinal) {
		this.tempoFinal = tempoFinal;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
}
	