
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomizableFinderRepository;
import security.UserAccount;
import domain.CustomizableFinder;

@Service
@Transactional
public class CustomizableFinderService {

	@Autowired
	private CustomizableFinderRepository	customizableFinderRepository;

	@Autowired
	private ActorService					actorService;


	public CustomizableFinder create() {
		final CustomizableFinder res = new CustomizableFinder();
		res.setResultNumber(10);
		res.setTimeCache(1);
		return res;
	}

	public CustomizableFinder create(final int resultNumber, final int timeCache) {
		final CustomizableFinder customizableFinder = new CustomizableFinder();
		customizableFinder.setResultNumber(resultNumber);
		customizableFinder.setTimeCache(timeCache);

		return customizableFinder;
	}
	//listing
	public Collection<CustomizableFinder> findAll() {
		return this.customizableFinderRepository.findAll();
	}

	public CustomizableFinder findOne(final int customizableFinderId) {
		return this.customizableFinderRepository.findOne(customizableFinderId);
	}

	//updating
	public CustomizableFinder save(final CustomizableFinder customizableFinder) {
		final UserAccount user = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Assert.isTrue(customizableFinder.getResultNumber() <= 100 && customizableFinder.getResultNumber() >= 10 && customizableFinder.getTimeCache() >= 0 && customizableFinder.getTimeCache() <= 24);
		return this.customizableFinderRepository.save(customizableFinder);
	}

	public CustomizableFinder getValues() {
		return this.customizableFinderRepository.getCustomizableFinder();
	}

}
