
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;


	//	@Autowired
	//	private CategoryService		categoryService;
	//	@Autowired
	//	private WarrantyService		warrantyService;

	// ---------- Simple CRUD methods ----------

	public Finder create() {
		final Finder f = new Finder();

		f.setAddress("");
		f.setCategory(null);
		f.setDescription("");
		f.setEndDate(new Date());
		f.setHighPrice(0.);
		f.setLowPrice(0.);
		f.setStartDate(new Date());
		f.setTicker("");
		f.setWarranty(null);
		f.setMoment(new Date());
		f.setFixUpTask(new HashSet<FixUpTask>());
		return f;
	}
	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}
	public Finder findOne(final int finderId) {
		return this.finderRepository.findOne(finderId);
	}
	public Finder save(final Finder f) {
		Assert.isTrue(f != null && f.getMoment() != null, "FinderService.save -> Finder no valido");
		return this.finderRepository.save(f);
	}

}
