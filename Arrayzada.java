import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

//criando Array
class ArrayCompartilhado {
   private int[] array;


   public ArrayCompartilhado(int length) {
      this.array = new int[length];
      Random rnd = new Random();
      for (int i = 0; i < length; i++ ) {
         this.array[i] = rnd.nextInt(10 - 1) + 1;
      }
   }

   public synchronized int[] get() {
      return this.array;
   }
}

class T extends Thread {
   private int id;

   int[] array;

   int NThreads;

   int quantEven = 0;

   public T(int tid, ArrayCompartilhado a, int n) {
      this.id = tid;
      this.array = a.get();
      this.NThreads = n;
   }
   public int getEven(){
      return this.quantEven;
   }

   public void run() {
      for (int i = id; i < array.length; i += NThreads) {
         if(array[i]%2 == 0){
            this.quantEven++;
         }
      }
   }
}

public class Arrayzada {
   public static void main (String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.println("Escolha o numero de threads:");
      int NThreads = sc.nextInt();
      System.out.println("Coloque o tamanho do array:");
      int DIM = sc.nextInt();

      sc.close();

      if (NThreads > DIM) {
         NThreads = DIM;
      }

      // Criando o array de threads
      T[] threads = new T[NThreads];

      // Criando Instancia
      ArrayCompartilhado array = new ArrayCompartilhado(DIM);

      // Criando Threads
      for (int i=0; i < threads.length; i++) {
         threads[i] = new T(i, array, NThreads);
      }

      // Executando as Threads
      for (int i=0; i<threads.length; i++) {
         threads[i].start();
      }
      int quantEven=0;
      // Aguardando o termino de Threads
      for (int i=0; i < threads.length; i++) {
         try {
            threads[i].join();
            quantEven+=threads[i].getEven();
         } catch (InterruptedException e) {
            return;
         }
      }


      // Validação do numero de pares
      int validacao = 0;
      for (int element: array.get()) {
         if (element%2 == 0) {
            validacao++;
         }
      }
      if(quantEven!=validacao){
         System.out.println("Erro de validacao");
         return;
      }


      System.out.println("A funcao foi validada com sucesso");
      System.out.println("O numero de pares e igual a "+quantEven);
   }

}
