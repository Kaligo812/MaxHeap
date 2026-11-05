import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaxHeapTest {

    @Test
    void maximoTrasInsertar() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.insertar(5);
        heap.insertar(10);
        heap.insertar(7);
        assertEquals(10, heap.maximo().valor());
        assertEquals(3, heap.tamaño());
    }

    @Test
    void extraerEnOrdenDescendente() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        for (int x : new int[]{3, 9, 1, 7, 5}) heap.insertar(x);
        int[] esperado = {9, 7, 5, 3, 1};
        for (int e : esperado) assertEquals(e, heap.extraerMaximo());
        assertTrue(heap.estaVacio());
    }

    @Test
    void modificarReordena() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        Handle<Integer> a = heap.insertar(5);
        heap.insertar(10);
        a.modificar(20);
        assertEquals(20, heap.maximo().valor());
        a.modificar(1);
        assertEquals(10, heap.maximo().valor());
    }

    @Test
    void eliminarPorHandle() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.insertar(5);
        Handle<Integer> b = heap.insertar(10);
        heap.insertar(7);
        b.eliminar();
        assertEquals(7, heap.maximo().valor());
        assertEquals(2, heap.tamaño());
    }
}
