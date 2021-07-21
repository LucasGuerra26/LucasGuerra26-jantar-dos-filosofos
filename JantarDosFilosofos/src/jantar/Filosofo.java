package jantar;

public class Filosofo extends Thread {
    int id;
    final int PENSANDO = 0;
    final int FAMINTO = 1;
    final int COMENDO = 2;
    
    public Filosofo(String nome, int id) {
        super(nome);
        this.id = id;
    }

    public void ComFome() {
        Principal.estado[this.id] = 1;
        System.out.println("O Filósofo " + getName() + " está FAMINTO!");
    }

    public void Come() {
        Principal.estado[this.id] = 2;
        System.out.println("O Filósofo " + getName() + " está COMENDO!");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    public void Pensa() {
        Principal.estado[this.id] = 0;
        System.out.println("O Filósofo " + getName() + " está PENSANDO!");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            System.out.println("ERROR>" + ex.getMessage());
        }
    }

    public void LargarGarfo() throws InterruptedException {
        Principal.mutex.acquire();
        Pensa();
        /*Quando um filosofo largar os garfos, o vizinho da esquera e da direita
        podem tentar pegar os garfos*/
        Principal.filosofos[VizinhoEsquerda()].TentarObterGarfos();
        Principal.filosofos[VizinhoDireita()].TentarObterGarfos();
        Principal.mutex.release();
    }

    public void PegarGarfo() throws InterruptedException {
        Principal.mutex.acquire();
        ComFome();
        //caso a condição for verdadeira, semaforo(1), permitindo
        //que o filosofo obtenha os garfos
        TentarObterGarfos();        
        Principal.mutex.release();
        //caso a condição não seja verdadeira, o filosofo vai ficar travado
        //no seu respectivo indice do semaforo, até chegar sua vez novamente
        //para tentar pegar os garfos
        Principal.semaforos[this.id].acquire();//semaforos[this.id] = new Semaphore(0)
    }

    public void TentarObterGarfos() {
        //se o filosofo estiver faminto e o vizinho esquerdo e direito não
        //estiver comendo, chama metodo come();
        if (Principal.estado[this.id] == 1
                && Principal.estado[VizinhoEsquerda()] != 2
                && Principal.estado[VizinhoDireita()] != 2) {
            Come();
            Principal.semaforos[this.id].release();//semaforos[this.id] = new Semaphore(1)
        } else {
            System.out.println(getName() + " não conseguiu comer!");
        }

    }

    @Override
    public void run() {
        try {
            Pensa();
            System.out.println("");
            do {
                PegarGarfo();
                Thread.sleep(1000L);
                LargarGarfo();
            } while (true);
        } catch (InterruptedException ex) {
            System.out.println("ERROR>" + ex.getMessage());
            return;
        }
    }

    public int VizinhoDireita() {
        return (this.id + 1) % 5;
    }

    public int VizinhoEsquerda() {
        // filosofo 0 recebe vizinho a esquerda 4, porque se calcularmos
        //o resultado será -1.
        if (this.id == 0) {            
            return 4;
        } else {
            return (this.id - 1) % 5;
        }
    }
}