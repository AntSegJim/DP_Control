
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfileSocialNetworkRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.ProfileSocialNetwork;
import domain.Referee;
import domain.Sponsor;

@Service
@Transactional
public class ProfileSocialNetworkService {

	@Autowired
	private ProfileSocialNetworkRepository	profileSocialNetworkRepository;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private AdministratorService			adminService;

	@Autowired
	private CustomerService					customerService;

	@Autowired
	private HandyWorkerService				handyService;

	@Autowired
	private RefereeService					refereeService;

	@Autowired
	private SponsorService					sponsorService;


	//CRUD
	public ProfileSocialNetwork create() {
		final ProfileSocialNetwork profile = new ProfileSocialNetwork();
		profile.setNickName("");
		profile.setNameSocialNetwork("");
		profile.setLinkProfile("");
		profile.setActor(new Actor());
		return profile;
	}

	public Collection<ProfileSocialNetwork> findAll() {
		return this.profileSocialNetworkRepository.findAll();
	}

	public ProfileSocialNetwork findOne(final int id) {
		return this.profileSocialNetworkRepository.findOne(id);
	}

	public ProfileSocialNetwork save(final ProfileSocialNetwork profile) {
		Assert
			.isTrue(profile != null && profile.getNameSocialNetwork() != null && profile.getNameSocialNetwork() != "" && profile.getNickName() != null && profile.getNickName() != null && profile.getLinkProfile() != null && profile.getLinkProfile() != "");
		final Integer userID = LoginService.getPrincipal().getId();
		final Actor a = this.actorService.getActorByUserAccount(userID);
		profile.setActor(a);
		return this.profileSocialNetworkRepository.save(profile);
	}

	public void delete(final ProfileSocialNetwork profile) {
		this.profileSocialNetworkRepository.delete(profile);
	}

	public Collection<ProfileSocialNetwork> getProfilesByActor(final int id) {
		return this.profileSocialNetworkRepository.profilesByActor(id);
	}

	public Integer getNumberProfiles(final int id) {
		return this.profileSocialNetworkRepository.getNumberProfile(id);
	}

	public void UpdateProperty() {
		final UserAccount user = LoginService.getPrincipal();
		final Integer userID = LoginService.getPrincipal().getId();
		final Actor a = this.actorService.getActorByUserAccount(userID);

		final Integer numProfile = this.getNumberProfiles(a.getId());

		if (user.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
			final Administrator ad = (Administrator) a;
			a.setNumberSocialProfiles(numProfile);
			this.adminService.save(ad);
		} else if (user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER")) {
			final Customer cust = (Customer) a;
			a.setNumberSocialProfiles(numProfile);
			this.customerService.save(cust);
		} else if (user.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER")) {
			final HandyWorker handy = (HandyWorker) a;
			a.setNumberSocialProfiles(numProfile);
			this.handyService.save(handy);
		} else if (user.getAuthorities().iterator().next().getAuthority().equals("SPONSOR")) {
			final Sponsor spon = (Sponsor) a;
			a.setNumberSocialProfiles(numProfile);
			this.sponsorService.save(spon);
		} else if (user.getAuthorities().iterator().next().getAuthority().equals("REFEREE")) {
			final Referee ref = (Referee) a;
			a.setNumberSocialProfiles(numProfile);
			this.refereeService.save(ref);
		}

	}
}
