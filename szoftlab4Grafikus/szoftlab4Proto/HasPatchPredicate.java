package szoftlab4Proto;

class HasPatchPredicate implements Predicate<NormalTile>
{
	/**
	 * @return Az érték true, ha a paraméterként kapott mező tartalmaz foltot, false egyébként
	 */
	@Override
	public boolean test(NormalTile t)
	{
		return t.getPatch()!=null;
	}
}
