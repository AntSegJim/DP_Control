
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	SRepo;
	@Autowired
	private CreditCardService		CCService;
	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private ActorService			actorS;


	public Sponsorship create() {
		final Sponsorship sponsorship = new Sponsorship();
		sponsorship.setUrlBanner("");
		sponsorship.setLinkTargetPage("");
		sponsorship.setCreditCard(this.CCService.create());
		final int id = LoginService.getPrincipal().getId();
		sponsorship.setSponsor(this.sponsorService.sponsorUserAccount(id));

		return sponsorship;
	}

	//listing
	public Collection<Sponsorship> findAll() {
		return this.SRepo.findAll();
	}
	public Sponsorship findOne(final int sponsorshipId) {
		return this.SRepo.findOne(sponsorshipId);
	}

	//updating
	public Sponsorship save(final Sponsorship sponsorship) {

		final Sponsor sponsor = this.sponsorService.sponsorUserAccount(LoginService.getPrincipal().getId());
		Assert.isTrue(sponsor.getId() == sponsorship.getSponsor().getId(), "Sponsor logueado y enviado no igual");
		Assert.isTrue(sponsorship.getCreditCard().getActor() == this.actorS.getActorLogged(), "Actor logueado y el actor de la creditCard no igual");

		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("SPONSOR"));
		Assert.isTrue(sponsorship != null && sponsorship.getLinkTargetPage() != null && sponsorship.getLinkTargetPage() != "" && sponsorship.getUrlBanner() != null && sponsorship.getUrlBanner() != "" && sponsorship.getCreditCard() != null
			&& sponsorship.getSponsor() != null);
		return this.SRepo.save(sponsorship);
	}
	//deleting
	public void delete(final Sponsorship sponsorship) {
		this.SRepo.delete(sponsorship);
	}

	public Collection<Sponsorship> findAllMySponsorships(final Integer sponsorId) {
		return this.SRepo.allMySponsorships(sponsorId);
	}
}
