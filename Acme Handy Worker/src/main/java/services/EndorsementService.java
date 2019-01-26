
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.LoginService;
import domain.Actor;
import domain.Endorsement;
import domain.HandyWorker;

@Service
@Transactional
public class EndorsementService {

	@Autowired
	private EndorsementRepository	endorsementRepository;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private ActorService			actorService;


	// ---------- Simple CRUD methods ----------

	public Endorsement create() {

		final Integer id_user = LoginService.getPrincipal().getId();
		final HandyWorker handyWorker = this.handyWorkerService.handyWorkerUserAccount(id_user);

		final Endorsement e = new Endorsement();
		e.setComments(new HashSet<String>());

		if (handyWorker == null)
			e.setCustomerSender(this.customerService.customerByUserAccount(id_user));
		else
			e.setHandyWorkerSender(handyWorker);

		e.setMoment(new Date());
		return e;
	}
	public Collection<Endorsement> findAll() {
		return this.endorsementRepository.findAll();
	}
	public Collection<Endorsement> myEndorsements(final int actorId) {
		return this.endorsementRepository.myEndorsements(actorId);
	}
	public Endorsement findOne(final int endorsementId) {
		return this.endorsementRepository.findOne(endorsementId);
	}
	public Endorsement save(final Endorsement e) {
		final Integer id_user = LoginService.getPrincipal().getId();
		final HandyWorker handyWorker = this.handyWorkerService.handyWorkerUserAccount(id_user);
		if (handyWorker == null)
			e.setCustomerSender(this.customerService.customerByUserAccount(id_user));
		else
			e.setHandyWorkerSender(handyWorker);
		Assert.isTrue((e.getCustomerReceiver() == null && e.getHandyWorkerReceiver() != null) || (e.getHandyWorkerReceiver() == null && e.getCustomerReceiver() != null));
		Assert.isTrue((e.getCustomerSender() == null && e.getHandyWorkerSender() != null) || (e.getHandyWorkerSender() == null && e.getCustomerSender() != null));
		return this.endorsementRepository.save(e);
	}
	public void delete(final Endorsement endorsement) {
		final int id_user = LoginService.getPrincipal().getId();
		final Actor actor = this.actorService.getActorByUserAccount(id_user);
		final Collection<Endorsement> endorsements = this.myEndorsements(actor.getId());
		Assert.isTrue(endorsements.contains(endorsement), "EndorsementService.delete -> You mustn't delete this endorsment.");
		this.endorsementRepository.delete(endorsement);
	}
}
