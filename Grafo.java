
import java.io.*;
import java.util.StringTokenizer;

public class Grafo {

    private int NN, aristas, origen[] = new int[100], destino[] = new int[100], M[][] = new int[20][20];

    public void escribematriz() {
        int i, j;
        System.out.println(" NODOS = " + NN);
        System.out.println(" Matriz de Adyacencias");
        System.out.print(" ");
        for (i = 1; i <= NN; i++) {
            System.out.print("  " + i);
        }
        System.out.println();

        for (i = 1; i <= NN; i++) {
            System.out.print(i + "  ");
            for (j = 1; j <= NN; j++) {
                System.out.print(M[i][j] + "  ");
            }
            System.out.println();
        }
    }
//lee los datos del archivo de texto

    public void LeeGrafo(String arch) {
        FileInputStream fp;
        DataInputStream f;
        String linea = null;
        int token1, token2, i, j;
        aristas = 0;
        try {
            fp = new FileInputStream(arch);
            f = new DataInputStream(fp);
            linea = f.readLine();

            NN = Integer.parseInt(linea);
            System.out.println(" Numero de Nodos: " + NN);
            // Inicializamos la matriz con ceros
            for (i = 1; i <= NN; i++) {
                for (j = 1; j <= NN; j++) {
                    M[i][j] = 0;
                }
            }

            // Leemos el archivo linea por linea
            do {
                linea = f.readLine();
                if (linea != null) {
                    StringTokenizer tokens = new StringTokenizer(linea);
                    token1 = Integer.parseInt(tokens.nextToken());
                    token2 = Integer.parseInt(tokens.nextToken());
                    // escribimos en pantalla los datos leidos transformados en numeros
                    System.out.println("\t" + token1 + " , " + token2);
                    origen[aristas] = token1;
                    destino[aristas] = token2;
                    aristas++;
                    // almacenamos en la matriz
                    M[token1][token2] = 1;
                    M[token2][token1] = 1;
                }
            } while (linea != null);
            fp.close();
        } catch (FileNotFoundException exc) {
            System.out.println("El archivo " + arch + " no fue encontrado ");
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    /*
    public void Ancho(int inicio) {
        GraphViz gv1 = new GraphViz();
        gv1.addln(gv1.start_graph());

        Cola C = new Cola(100);
        int[] visitados = new int[NN + 1];
        int n, j, padre;
        String cad;

        for (j = 0; j <= NN; j++) {
            visitados[j] = 0;
        }
        C.insertar(inicio);
        visitados[inicio] = 1;
        int cont = 1;
        System.out.print("Recorrido a lo ancho\n");
        while (C.vacia() != 1) {
            n = C.extraer();
            if (cont != NN) {
                System.out.print(n + " -> ");
                cont++;
            } else {
                System.out.print(n);
            }
            for (j = 1; j <= NN; j++) {
                if (M[n][j] != 0) {
                    if (visitados[j] == 0) {
                        C.insertar(j);
                        cad = n + "->" + j;
                        gv1.addln(cad);
                        visitados[j] = 1;
                    }
                }
            }
        }
        // System.out.print(" }");
        gv1.addln(gv1.end_graph());
//            System.out.println(gv1.getDotSource());
        String type = "jpg";
        File out = new File("Ancho" + inicio + "." + type);    // Windows
        gv1.writeGraphToFile(gv1.getGraph(gv1.getDotSource(), type), out);
    }
     */
    public void Profundo(int inicio) {
//variables para dibujar  el grafo
        GraphViz gv1 = new GraphViz();
        gv1.addln(gv1.start_graph());
        String cadena = null;
//creacion de variables necesarias para el metodo y el contador
        ListaSimple lista = new ListaSimple(NN + 1);
        Pila p = new Pila(100);
        int[] visitados = new int[NN + 1];
        int fin = 1;
        int act = 0, ant = 0;
//arreglo visitados todos =0
        for (int j = 0; j <= NN; j++) {
            visitados[j] = 0;
        }
//agregamos el nodo de cual partiremos a la pila       
        p.Push(inicio);
        //insertamos el inicio de la pila a un  lista ligada simple
        lista.insertar(inicio, -1);

        System.out.print("\nRecorrido a lo profundo\n");
//ciclo hasta que el contadro sea igual al numero de nodos
        while (fin <= NN) {
            //Sacamos el dato a comparar
            act = p.Pop();
            if (visitados[act] != 1) { //Si no esta visitado
                visitados[act] = 1; //se marca como visitado

                //mandamos a imprimir a pantalla el dato extraido
                System.out.print(act + " -> ");
                fin++;//incrementamos contador

                //Se agreagan a la pila los adyacentes del dato extraido
                for (int j = NN; j >= 1; j--) {
                    if (M[act][j] != 0) {
                        p.Push(j);
                    }
                }

                //revisamos que el valor ant sea diferente de 0
                if (ant != 0) {
                    //Revisamos el contenido de las posisicones act y ant
                    if (M[act][ant] == 1) {
                        //mandamos a dibujar el dato
                        cadena = ant + "->" + act;
                        gv1.addln(cadena);
                        //igualamos las posiciones ya evaludas a 0 para no volver a usarlas
                        M[ant][act] = M[act][ant] = 0;
                        //insertamos los valores act y ant a la lista ligada
                        lista.insertar(act, ant);
                    } else {
                        //si evalua una posicion ya evaluada recorre la matriz por la columna del act para enconatar una conexion y mandarla a dibujar
                      
                        for (int j = NN; j >= 1; j--) {
                            if (M[j][act] == 1) {
                              
                                    cadena = j + "->" + act;
                                    gv1.addln(cadena);

                                    M[act][j] = M[j][act] = 0;
                                    //mandamos a ingresar a la lista act y j
                                    lista.insertar(act, j);
                                   
                                
                            }
                        }
                    }
                }
                //reinicimaos el contenido de la cadena
                cadena = " ";
                //cambiamos los valores de ant con act
                ant = act;
            }
        }

        System.out.println("\n");
        //mostarar  listas de raices
        lista.mostrar();

        System.out.println("\n");
        //---------------------ciclos
        int contador = 1, cantidad = 0, impar = 0, par = 0;
        for (int i = NN; i >= 1; i--) {
            for (int j = NN; j >= 1; j--) {
                //se comparan las posiciones para ver deonde hay 1
                if (M[i][j] == 1) {
                    //imprimimos el valor del contador
                    System.out.println("\nCiclo " + contador);
//el retorno del metodo ciclos los igualamos a cantidad.le mandamos i,j para la comparacion
                    cantidad = lista.ciclos(i, j);
                   // System.out.println("i=."+i+"j"+j);
                    //hacemos la comparacion a traves del modulo de la operacion
                    if (cantidad % 2 == 0) {
                        System.out.print(" ( Es Par) \n");
                        par++;//incrementamos contador de par
                    } else {
                        System.out.print(" ( Es Impar) \n");
                        impar++;//incrementamos contador impar
                    }
                    //mandamos a dibujar las conexiones de los ciclos
                    if (i == inicio) {
                        cadena = j + "->" + i;
                    } else {

                        cadena = i + "->" + j;
                    }
                    gv1.addln(cadena);

                    //igualamos a 0 las posicones de la matriz ya evaluada para no repetirse
                    M[j][i] = M[i][j] = 0;

                    //incrementamos el contador
                    contador++;
                }
            }
        }

        //comparando el contador y el contadro de par y impar obtenemos el resultado de bipartito o no bipartito
        if (par == contador) {
            System.out.println("\nEl grafo Es Bipartito");
        } else {
            System.out.println("\nEl grafo No es Bipartito");
        }

        //mandamos a crear el jpg
        gv1.addln(gv1.end_graph());
        String type = "jpg";
        File out = new File("Profundo" + "." + type);    // Windows
        gv1.writeGraphToFile(gv1.getGraph(gv1.getDotSource(), type), out);
        System.out.println("\n");

    }

    //dibujamos grafo Original
    public void dibujarGrafo1(String nombre) {
        GraphViz gv = new GraphViz();
        String cad;
        gv.addln(gv.start_NOgraph());
        for (int i = 0; i < aristas; i++) {
            cad = this.origen[i] + "--" + destino[i];
            gv.addln(cad);
        }
        gv.addln(gv.end_graph());

        String type = "jpg";
        File out = new File(nombre + "." + type);    // Windows
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    public static void main(String[] args) {
        Grafo G = new Grafo();
        G.LeeGrafo("../Proyecto/entrada.dat");
        G.escribematriz();
        //   G.Ancho(6);
        G.Profundo(3);
        G.dibujarGrafo1("Grafo Original");
    }
}
