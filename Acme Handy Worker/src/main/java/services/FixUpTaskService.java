
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private CategoryService		CService;
	@Autowired
	private WarrantyService		WService;
	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	public FixUpTask create() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		final FixUpTask f = new FixUpTask();
		final Category ca = this.CService.create();
		final Warranty wa = this.WService.create();
		final Customer cus = this.customerService.customerByUserAccount(userAccount.getId());
		f.setAddress("");
		f.setApplication(null);
		f.setCategory(ca);
		f.setCustomer(cus);
		f.setDescription("");
		f.setMaximunPrice(0.);
		f.setMoment(new Date());
		f.setPeriodTime(0);
		f.setTicker(ComplaintService.generar(new Date()));
		f.setWarranty(wa);
		return f;
	}
	public Collection<FixUpTask> findAll() {

		return this.fixUpTaskRepository.findAll();
	}

	public FixUpTask findOne(final Integer id) {
		return this.fixUpTaskRepository.findOne(id);
	}
	public FixUpTask save(final FixUpTask f) {
		if (f.getId() == 0) {
			final Collection<String> allTickerFix = this.fixUpTaskRepository.allTickerInFixUpTask();
			final Collection<String> allTickerCurricula = this.curriculaService.allTickersCurricula();
			final Collection<String> allTickerComplaint = this.complaintService.allTickersComplaint();
			Assert.isTrue(!allTickerComplaint.contains(f.getTicker()) && !allTickerFix.contains(f.getTicker()) && !allTickerCurricula.contains(f.getTicker()));
		}

		Assert.isTrue(f.getTicker() != null && f.getTicker() != "" && f.getCategory() != null && f.getCustomer() != null);
		return this.fixUpTaskRepository.save(f);
	}
	public void delete(final FixUpTask f) {
		this.fixUpTaskRepository.delete(f);
	}

	public Collection<FixUpTask> fixUpTasksByFinder(final Integer finderId) {
		return this.fixUpTaskRepository.fixUpTasksByFinder(finderId);
	}

	public Collection<FixUpTask> findAllfixUpTasksHandyWorkerId() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final HandyWorker handyWorker = this.handyWorkerService.handyWorkerUserAccount(userAccount.getId());

		return this.fixUpTaskRepository.findAllfixUpTasksHandyWorkerId(handyWorker.getId());
	}
	public Collection<String> allTickersFix() {
		return this.fixUpTaskRepository.allTickerInFixUpTask();
	}

	public List<Object[]> maxMinAvgDevFixUpTask() {
		return this.fixUpTaskRepository.getMaxMinAvgDesvFixUp();
	}

	public List<Object[]> maxMinAvgDevFixUpTaskApp() {
		return this.fixUpTaskRepository.getMaxMinAvgDesvFixUpApp();
	}

	public List<Object[]> maxMinAvgDesvFixUpPrice() {
		return this.fixUpTaskRepository.getMaxMinAvgDesvFixUpPrice();
	}

	public List<Object[]> maxMinAvgDesvFixUpComplaint() {
		return this.fixUpTaskRepository.getMaxMinAvgDesvFixUpComplaint();
	}

	public Collection<FixUpTask> findAllCustomer() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Customer c = this.customerService.customerByUserAccount(userAccount.getId());
		return this.fixUpTaskRepository.fixUpTasksCustomer(c.getId());
	}

	public Collection<FixUpTask> filterFixUpTask(final String ticker, final String description, final String address, final Date fi, final Date ff, final Double lowPrice, final Double highPrice, final String category, final String warranty) {
		return this.fixUpTaskRepository.filterFixUpTask(ticker, description, address, fi, ff, lowPrice, highPrice, category, warranty);
	}
	public Collection<FixUpTask> filterFixUpTask2(final String ticker, final String description, final String address, final Date fi, final Date ff, final Double lowPrice, final Double highPrice, final String category, final String warranty,
		final Integer limite) {
		return this.fixUpTaskRepository.filterFixUpTask2(ticker, description, address, fi, ff, lowPrice, highPrice, category, warranty, limite);
	}

	public Collection<FixUpTask> findAllCustomerById(final Integer customerId) {
		return this.fixUpTaskRepository.fixUpTasksCustomer(customerId);
	}
	public Date dateExpiration(final Integer id) {
		return this.fixUpTaskRepository.dateExpiracion(id);
	}
	public List<FixUpTask> getFixUpByCategory(final String name) {
		return this.fixUpTaskRepository.getFixUpByCategory(name);
	}
}
