package ci.gouv.budget.solde.sigdcp.service.resources;

public enum CRUDType {
	
	CREATE{
		@Override
		public String libelle() {
			return "Création";
		}
	},
	READ{
		@Override
		public String libelle() {
			return "Consultation";
		}
	},
	UPDATE{
		@Override
		public String libelle() {
			return "Modification";
		}
	},
	DELETE{
		@Override
		public String libelle() {
			return "Suppresion";
		}
	}
	
	;
	
	
	public abstract String libelle();

}
