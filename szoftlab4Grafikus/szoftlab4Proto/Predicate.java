package szoftlab4Proto;

/**
 * A java.util.function.Predicate interfész funkcióját helyettesítendő interfész. (JDK 8-tól elérhető)
 * 
 * 
 * @param <T> Az az osztály (típus), amelynek példányain feltételvizsgálatot szeretnénk végezni az interfészt megvalósító osztályokkal.
 */
public interface Predicate<T>
{
	/**
	 * A megvalósító osztályok ezen függvény implementálásával tudnak egy a paraméterben kapott objektumra vonatkozó feltételt kiértékelni.
	 * 
	 * @param obj Az az objektum, amelyen a kiértékelés végbemegy.
	 * @return Megmutatja, hogy teljesült-e az implementált függvényben meghatározott feltétel.
	 */
	public boolean test(T obj);
}
