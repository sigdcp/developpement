package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.GroupeMissionDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;

public class GroupeMissionServiceImpl extends AbstractGroupeServiceImpl<GroupeMission> implements GroupeMissionService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject
	public GroupeMissionServiceImpl(GroupeMissionDao dao) {
		super(dao);
	}

	@Override
	public GroupeMission findByFonctionOrGrade(Fonction fonction, Grade grade) {
		GroupeMission groupeMission = null;
		if(fonction!=null)
			groupeMission = ((GroupeMissionDao)dao).readByFonction(fonction);
		if(groupeMission==null)
			groupeMission = ((GroupeMissionDao)dao).readByGrade(grade);
		
		return groupeMission;
	}

	@Override
	public GroupeMission findByFonctionOrProfession(Fonction fonction,	Profession profession) {
		GroupeMission groupeMission = null;		
		
		groupeMission = findByFonctionOrGrade(fonction, profession==null?null:profession.getGrade());
		
		return groupeMission;
	}

	@Override
	public Collection<GroupeMission> findByTypeClasseVoyage(Collection<TypeClasseVoyage> typeClasseVoyages) {
		Collection<GroupeMission> groupe=new ArrayList<>();
		for(TypeClasseVoyage cl : typeClasseVoyages )
		groupe.addAll(((GroupeMissionDao)dao).readByTypeClasseVoyage(cl));
		
		return groupe;
	}
	

	
}
