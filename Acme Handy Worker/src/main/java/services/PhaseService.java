
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	@Autowired
	private PhaseRepository		phaseRepository;

	@Autowired
	private ApplicationService	AService;


	public Phase create() {
		final Phase res = new Phase();

		res.setTitle("");
		res.setDescription("");
		res.setStartMoment(new Date());
		res.setEndMoment(new Date());
		res.setApplication(this.AService.create());
		return res;
	}

	public Phase create(final String title, final String description, final Date startMoment, final Date endMoment, final Application application) {
		final Phase phase = new Phase();
		phase.setTitle(title);
		phase.setDescription(description);
		phase.setStartMoment(startMoment);
		phase.setEndMoment(endMoment);
		phase.setApplication(application);

		return phase;
	}
	//listing
	public Collection<Phase> findAll() {
		return this.phaseRepository.findAll();
	}

	public Phase findOne(final int phaseId) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER"));
		Assert.isTrue(this.phaseRepository.findOne(phaseId).getApplication().getStatus() == 0);
		return this.phaseRepository.findOne(phaseId);
	}
	//updating
	public Phase save(final Phase phase) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER"));
		Assert.isTrue(phase.getApplication().getStatus() == 0);

		Assert.isTrue(phase != null && !(phase.getTitle().equals("") && !(phase.getTitle().equals(null))));
		Assert.isTrue(!(phase.getStartMoment().equals(null) && !(phase.getStartMoment().before(Calendar.getInstance().getTime()))));
		Assert.isTrue(phase.getApplication() != null && (phase.getEndMoment().equals(null) || phase.getEndMoment().after(phase.getStartMoment())));
		return this.phaseRepository.save(phase);
	}

	//deleting
	public void delete(final Phase phase) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER"));
		Assert.isTrue(phase.getApplication().getStatus() == 0);
		this.phaseRepository.delete(phase);
	}

	public Collection<Phase> findAllApplicationId(final Integer applicationId) {

		return this.phaseRepository.findAllApplicationId(applicationId);
	}

}
