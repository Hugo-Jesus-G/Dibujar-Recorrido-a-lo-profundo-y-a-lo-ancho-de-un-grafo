
public class ListaSimple {

    public int tam ;
    private Nodo raiz[] ;
//creacion de un arreglo de lsitas ligadas simples de tramaño del numero de grafos
    public ListaSimple(int tamano) {
        tam=tamano;
       raiz = new Nodo[tam];
       //todas las raices igualadas a null
        for (int i = 0; i < tam; i++) {
            raiz[i] = null;
        }
    }

    public void insertar(int Valoractual, int Valoranterior) 
    {
        //Creamos la variable temporal para las raices
        Nodo temp = new Nodo(Valoractual);
        //ligamos las listas entrantes
        int n = 0;
        boolean encontrado = false;
        //si es el primer nodo se iguala al temp de la posicion n
         if (raiz[n] == null) {
                raiz[n] = temp;
            }
        
        if (Valoranterior != -1) {
            //verificar que el dato aun no se encuentre
            for (int i = 0; i < tam; i++) {
                if (raiz[i] != null) {
                    if (Valoranterior == raiz[i].inf) {
                        n = i;
                        encontrado = true;
                    }
                     if (!encontrado ) {
                        n++;
                    }
                }
            }
            //si raiz en la posicion n es nula se le asigna el valor de temp 
            //---y recorre la lista para unirla con el nodo anteriror ya insertado de la lista
            if (raiz[n] == null) {
                raiz[n] = temp;
                for (int i = 0; i < n; i++) {
                    if (raiz[i] != null) {
                        Nodo aux = raiz[i];
                        while (aux != null) {
                            if (aux.inf == Valoranterior) {
                                raiz[n].liga = aux;
                                break;
                            } else {
                                aux = aux.liga;
                            }
                        }
                    }
                }
            } //si no es nulo el nodo temporal se liga a la raiz n y la raiz n pasa a ser el temporal
            else {
                temp.liga = raiz[n];
                raiz[n] = temp;
            }
        } 
     
           
        
    }
//recorrido de las raices 
    public void mostrar() {
        for (int i = 0; i < tam; i++) {
            if (raiz[i] != null) {

                System.out.println("\nRaiz " + (i + 1));
                Nodo aux = raiz[i];

                while (aux != null) {

                    System.out.print(aux.inf + " ->");

                    aux = aux.liga;
                }

                System.out.println("");
            }
        }
    }

   //metodo que retorna la cantidad de ciclos de inicio a fin
    public int ciclos(int inicio, int fin) {
        int cont = 0;
        for (int i = 0; i < tam; i++) {
                Nodo aux = raiz[i];
                boolean visitado = false;
                while (aux != null) {
                    if ((aux.inf == inicio) || (aux.inf == fin)) {
                        if (!visitado) {
                            visitado = true;
                        } else {
                            System.out.print(aux.inf);
                            visitado = false;
                            i = tam;
                            cont++;
                        }
                    }
                  if (visitado) {
                        cont++;
                       System.out.print(aux.inf + " -> ");
                    }
                    aux = aux.liga;
                }
          
        }
        return cont;
    }
}
