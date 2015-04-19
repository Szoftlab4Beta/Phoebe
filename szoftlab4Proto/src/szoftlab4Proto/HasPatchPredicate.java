package szoftlab4Proto;

class HasPatchPredicate implements Predicate<NormalTile>
{
	@Override
	public boolean test(NormalTile t)
	{
		return t.getPatch()!=null;
	}
}
