package ci.gouv.budget.solde.sigdcp.model.identification;

public enum Sexe {
	MASCULIN{
		@Override
		public String toString() {
			return "Masculin";
		}
	}, FEMININ{
		@Override
		public String toString() {
			return "Féminin";
		}
	};
}