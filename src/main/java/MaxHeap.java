interface Handle<T> {
    /** Devuelve el valor del elemento */
    public T valor();

    /** Elimina al elemento de la colección */
    public void eliminar();

    /** Modifica al elemento de la colección y su orden (de ser necesario) */
    public void modificar(T v);

    /** Devuelve si el valor que representa el Handle es equivalente al que representa otro. */
    public boolean equals(Handle<T> h);
}

public class MaxHeap<T extends Comparable<T>> {

    private class Nodo {
        T valor;
        Nodo padre, izq, der;
        HandleImpl handle;

        Nodo(T valor, Nodo padre) {
            this.valor = valor;
            this.padre = padre;
            this.handle = new HandleImpl(this);
        }
    }

    private class HandleImpl implements Handle<T> {
        private Nodo nodo;

        HandleImpl(Nodo nodo) {
            this.nodo = nodo;
        }

        private boolean nodoValido() {
            return nodo != null;
        }

        public T valor() {
            return nodo.valor;
        }

        public void eliminar() {
            eliminarNodo(nodo);
        }

        public void modificar(T v) {
            Nodo n = this.nodo;
            n.valor = v;
            subir(n);
            bajar(this.nodo);
        }


        @Override 
        public boolean equals(Handle<T> h) {
            return h == this;
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof Handle<?>) && this == o;
        }

        @Override
        public String toString() {
            return nodo == null ? "Handle(eliminado)" : "Handle(" + nodo.valor + ")";
        }
    }

    private Nodo raiz;
    private int tamaño;

    public MaxHeap() {
        raiz = null;
        tamaño = 0;
    }

    // ----------------------- Metodos publicos ----------------------

    public int tamaño() {
        return tamaño;
    }

    public boolean estaVacio() {
        return tamaño == 0;
    }

    public Handle<T> insertar(T v) {
        Nodo nuevo;
        if (raiz == null) {
            nuevo = new Nodo(v, null);
            raiz = nuevo;
        } else {
            Nodo padre = nodoEnPosicion((tamaño + 1) / 2);
            nuevo = new Nodo(v, padre);
            if (padre.izq == null) {
                padre.izq = nuevo;
            } else {
                padre.der = nuevo;
            }
        }
        tamaño++;
        subir(nuevo);
        return nuevo.handle;
    }

    public Handle<T> maximo() {
        return raiz.handle;
    }

    public T extraerMaximo() {
        T v = raiz.valor;
        eliminarNodo(raiz);
        return v;
    }

    // ----------------------- Metodso privados ---------------------

    // La numeracion de niveles empieza en 1
    // bit = (0 -> izquierda) o (1 -> derecha)
    private Nodo nodoEnPosicion(int pos) {
        Nodo actual = raiz;
	
	int masc = Integer.highestOneBit(pos) >> 1; // 1 rshift para ignorar el bit mas significativo
        for (;masc > 0; masc >>= 1) {
            actual = ((pos & masc) == 0) ? actual.izq : actual.der;
        }
        return actual;
    }

    private void intercambiar(Nodo a, Nodo b) {
        T tmpValor = a.valor;
        a.valor = b.valor;
        b.valor = tmpValor;

        HandleImpl tmpHandle = a.handle;
        a.handle = b.handle;
        b.handle = tmpHandle;

        a.handle.nodo = a;
        b.handle.nodo = b;
    }

    private void subir(Nodo n) {

        while (n.padre != null && n.valor.compareTo(n.padre.valor) > 0) {
            intercambiar(n, n.padre);
            n = n.padre;
        }
    }

    private void bajar(Nodo n) {
        while (true) {
            Nodo mayor = n;
            if (n.izq != null && n.izq.valor.compareTo(mayor.valor) > 0) {
                mayor = n.izq;
            }
            if (n.der != null && n.der.valor.compareTo(mayor.valor) > 0) {
                mayor = n.der;
            }

            if (mayor == n) {
                return;
            }
            intercambiar(n, mayor);
            n = mayor;
        }

    }


    private void eliminarNodo(Nodo n) {
        Nodo ultimo = nodoEnPosicion(tamaño);

	// el elemento a eliminar pasa a ser el ultimo
        if (n != ultimo) {
            intercambiar(n, ultimo);
        }

        ultimo.handle.nodo = null;

        if (ultimo.padre == null) { // Caso: MaxHeap con tamaño 1;
            raiz = null;
        } else if (ultimo.padre.der == ultimo) {
            ultimo.padre.der = null;
        } else {
            ultimo.padre.izq = null;
        }
        ultimo.padre = null;
        tamaño--;

        if (n != ultimo) {
            subir(n);
            bajar(n.handle.nodo);
        }
    }
}


