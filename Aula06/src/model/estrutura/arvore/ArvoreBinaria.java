package modes.estrutura.arvore;

import model.estrutura.arvore.No;
import model.estrutura.lista.ListaEncadeadaSimples;

public class ArvoreBinaria<T extends Comparable>{
	private No<T> raiz;
	private int tamanho = 0;
	
	public ArvoreBinaria(){
		this.raiz = null;
	}
	
	// Add: adiciona um novo No<T> na arvore;
	public void add(T valor){
		tamanho++;
		if (raiz == null){
			this.raiz = novo;
			defineBalanceamento(this.raiz);
			return;
		}
		No<T> atual = this.raiz;
		while (true){
			if (novo.getValor().compareTo(atual.getValor()) == -1){
				if (atual.getMenor() != null){
					atual = atual.getMenor();
				} else {
					atual.setMenor(novo);
					novo.setRaiz(atual);
					defineBalanceamento(this.raiz);
					this.raiz = balancear(raiz);
					break;
				}
			} else {
				if (atual.getMaior() != null){
					atual = atual.getMaior();
				} else {
					atual.setMaior(novo);
					novo.setRaiz(atual);
					defineBalanceamento(this.raiz);
					this.raiz = balancear(raiz);
					break;
				}
			}
		}
	}
	
	// Altura (No<T>): calcula a altura do No<T>
	public int altura(No<T> atual){
		if (atual == null){
			// Se o No<T> for nulo, altura = -1
			return -1;
		}
		if (atual.getMaior() == null && atual.getMenor() == null){
			// Se ele não tiver filho, altura = 0
			return 0;
		} else if (atual.getMenor() == null){
			// Se o No<T> tem 1 filho, altura = 1 + altura do filho
			return 1 + altura(atual.getMaior());
		} else if (atual.getMaior() == null){
			// Se o No<T> tem 1 filho, altura = 1 + altura do filho
			return 1 + altura(atual.getMenor());
		} else {
			// Se ele tiver 2 filhos ver o "mais alto"
			if (altura(atual.getMenor()) > altura(atual.getMaior())){
				// A altura do nó é 1 + seu filho "mais alto"
				return 1 + altura(atual.getMenor());
			} else {
				return 1 + altura(atual.getMaior());
			}
		}
	}
	
	// defineBalanceamento (No<T>): define o indice de balanceamento
	public void defineBalanceamento(No<T> atual){
		atual.setBalanceamento(altura(atual.getMenor()) - altura(atual.getMaior()));
		if (atual.getMaior() != null)
			defineBalanceamento(atual.getMaior());
		if (atual.getMenor() != null )
			defineBalanceamento(atual.getMenor());
	}
	
	// rotacaoEsquerda
	public No<T> rotacaoEsquerda(No<T> atual){
		No aux = atual.getMaior();
		aux.setRaiz(atual.getRaiz());
		// tratamento para quando a arvore é egenerada
		if (aux.getMenor() != null)
			aux.getMenor().setRaiz(atual);
		atual.setRaiz(aux);
		atual.setMaior(aux.getMenor());
		aux.setMenor(atual);
		if (aux.getRaiz() != null){
			if (aux.getRaiz().getMaior() == atual){
				aux.getRaiz().setMaior(aux);
			} else if (aux.getRaiz().getMenor() == atual){
				aux.getRaiz().setMenor(aux);
			}
		}
		// atualizar valor de balanceamento
		defineBalanceamento(aux);
		return aux;
	}
	
	// rotacaoDireita
	public No<T> rotacaoDireita(No<T> atual){
		No<T> aux = atual.getMenor();
		aux.setRaiz(atual.getRaiz());
		// tratamento para quando a arvore é degenerada
		if (aux.getMaior() != null)
			aux.getMaior().setRaiz(atual);
		atual.setRaiz(aux);
		atual.setMenor(aux.getMaior());
		aux.setMaior(atual);
		if (aux.getRaiz() != null){
			if (aux.getRaiz().getMaior() == atual){
				aux.getRaiz().setMaior(aux);
			} else if (aux.getRaiz().getMenor() == atual){
				aux.getRaiz().setMenor(aux);
			}
		}
		// atualizar valor de balanceamento
		defineBalanceamento(aux);
		return aux;
	}
	
	// rotacaDuplaDireita
	public No rotacaoDuplaDireita(No<T> atual){
		No<T> aux = atual.getMenor();
		atual.setMenor(rotacaoEsquerda(aux));
		No<T> aux2 = rotacaoDireita(atual);
		return aux2;
	}
	
	// rotacaDuplaEsquerda
	public No rotacaoDuplaEsquerda(No<T> atual){
		No<T> aux = atual.getMaior();
		atual.setMaior(rotacaoDireita(aux));
		No<T> aux2 = rotacaoEsquerda(atual);
		return aux2;
	}
	
	// balancear
	public void balancear(){
		this.raiz = balancear(this.raiz);
	}
	public No<T> balancear(No<T> atual){
		if(atual.getBalanceamento() == 2 && atual.getNoEsq().getBalanceamento() >= 0){
			atual = rotacaoDireita(atual);
		} else if(atual.getBalanceamento() == -2 && atual.getNoDir().getBalanceamento() <= 0){
			atual = rotacaoDuplaEsquerda(atual);
		} else if(atual.getBalanceamento() == 2 && atual.getNoEsq().getBalanceamento() < 0) {
			atual = rotacaoDuplaDireita(atual);
		} else if(atual.getBalanceamento() == -2 && atual.getNoDir().getBalanceamento() > 0){
			atual = rotacaoDuplaEsquerda(atual);
		}
		
		if(atual.getNoDir() != null){
			balancear(atual.getNoDir());
		}
		
		if(atual.getNoEsq() != null){
			balancear(atual.getNoEsq());
		}
		
		return atual;
	}
}