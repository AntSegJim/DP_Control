
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomizableSystemRepository;
import security.UserAccount;
import domain.CustomizableSystem;

@Service
@Transactional
public class CustomizableSystemService {

	@Autowired
	private CustomizableSystemRepository	customizableSystemRepository;

	@Autowired
	private ActorService					actorService;


	public CustomizableSystem create() {
		final CustomizableSystem res = new CustomizableSystem();
		res.setName("");
		res.setBanner("");
		res.setMessageWelcomePage("");
		res.setVATPercentage("");
		res.setTelephoneCode("");

		return res;
	}

	public CustomizableSystem create(final String name, final String banner, final String messageWelcomePage, final String VATPercentage, final String telephoneCode) {
		final CustomizableSystem customizableSystem = new CustomizableSystem();
		customizableSystem.setName(name);
		customizableSystem.setBanner(banner);
		customizableSystem.setMessageWelcomePage(messageWelcomePage);
		customizableSystem.setVATPercentage(VATPercentage);
		customizableSystem.setTelephoneCode(telephoneCode);

		return customizableSystem;
	}

	public Collection<CustomizableSystem> findAll() {
		return this.customizableSystemRepository.findAll();
	}

	//		public CustomizableSystem findOne(final int customizableSystemId) {
	//			return this.customizableSystemRepository.findOne(customizableSystemId);
	//		}

	//updating
	public CustomizableSystem save(final CustomizableSystem customizableSystem) {
		final UserAccount user = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Assert.isTrue(customizableSystem.getBanner() != null && customizableSystem.getBanner() != "" && customizableSystem.getMessageWelcomePage() != null && customizableSystem.getMessageWelcomePage() != "" && customizableSystem.getName() != null
			&& customizableSystem.getName() != "" && customizableSystem.getTelephoneCode() != null & customizableSystem.getTelephoneCode() != "" && customizableSystem.getVATPercentage() != null && customizableSystem.getVATPercentage() != "");
		return this.customizableSystemRepository.save(customizableSystem);
	}

	public Double getIVA() {
		return (double) this.customizableSystemRepository.getIVA() / 100;
	}

}
