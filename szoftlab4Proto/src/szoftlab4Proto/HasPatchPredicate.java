package szoftlab4Proto;

import java.util.function.Predicate;

class HasPatchPredicate implements Predicate<NormalTile>
{
	@Override
	public boolean test(NormalTile t)
	{
		return t.getPatch()==null;
	}
}
