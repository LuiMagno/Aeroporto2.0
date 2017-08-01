package PacoteAirport;

public class Tempo extends Thread {
	private int tempo = 0;
	
	public void run(){
		try {
			while(true){
				/*System.out.println("Tempo: "+ tempo);*/
				tempo ++;
				
				if(Aeroporto.AvioesPousando.size() == 0 && Aeroporto.AvioesDecolando.size() == 0 && finalExecucao()){
					System.out.println("--------------------------------------------------------------------------");
					System.out.println("FIM");
					break;
				}
				for(int i = 0; i<Aeroporto.AvioesPrincipal.size(); i++){
					((Aviao) Aeroporto.AvioesPrincipal.get(i)).setTempo(tempo);
				}
				
				
				Thread.sleep(1000);
				
					
					
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public boolean finalExecucao(){
		if(Aeroporto.contadorAvioesD == 10 && Aeroporto.contadorAvioesP == 10){
			return true;
		}else{
			return false;
		}
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

}
