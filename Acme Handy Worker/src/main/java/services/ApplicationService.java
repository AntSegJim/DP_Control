
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private CreditCardService		CCService;
	@Autowired
	private FixUpTaskService		FUTService;
	@Autowired
	private HandyWorkerService		HWService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private MessageBoxService		messageBoxService;


	public Application create() {
		final Application a = new Application();
		final CreditCard cc = this.CCService.create();
		final FixUpTask fut = this.FUTService.create();
		final HandyWorker hw = this.HWService.create();
		a.setComments(new HashSet<String>());
		a.setCreditCard(cc);
		a.setFixUpTask(fut);
		a.setHandyWorker(hw);
		a.setMoment(new Date());
		a.setPrice(0.);
		a.setStatus(1);
		return a;
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Collection<Application> findAll() {

		return this.applicationRepository.findAll();
	}

	public Application save(final Application a) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER")) {
			Assert.isTrue(this.FUTService.findAll().contains(a.getFixUpTask()));
			Assert.isTrue(!this.FUTService.findAllfixUpTasksHandyWorkerId().contains(a.getFixUpTask()));

		} else {

			final Customer c = this.customerService.customerByUserAccount(userAccount.getId());
			Assert.isTrue(Integer.valueOf(a.getFixUpTask().getCustomer().getId()) == (c.getId()));

		}

		Assert.isTrue(a.getMoment() != null && a.getMoment().before(Calendar.getInstance().getTime()) && a.getStatus() >= 0 && a.getStatus() <= 2 && a.getHandyWorker() != null && a.getFixUpTask() != null);

		if (a.getId() != 0) {
			final Application app = this.applicationRepository.findOne(a.getId());
			if (app.getStatus() != a.getStatus()) {
				final Message m = this.messageService.create();
				m.setSubject("Status application of Fix Up " + a.getFixUpTask().getTicker());
				m.setBody("EN: Status of your appliaction has been modificated from " + this.getStatusApp(app.getStatus()).get(0) + " to " + this.getStatusApp(a.getStatus()).get(0) + "    " + "ES:El estatus de su aplicación ha sido modificado de "
					+ this.getStatusApp(app.getStatus()).get(1) + " a " + this.getStatusApp(a.getStatus()).get(1));
				m.setEmailReceiver(a.getHandyWorker().getEmail());
				m.setPriority(0);
				final Message saved = this.messageService.save(m);

				final Customer cust = this.customerService.customerByUserAccount(userAccount.getId());
				final MessageBox inCustomer = this.messageBoxService.getInBox(cust.getId());
				inCustomer.getMessages().add(saved);

				final MessageBox inHandy = this.messageBoxService.getInBox(a.getHandyWorker().getId());
				inHandy.getMessages().add(saved);

			}
		}

		if (a.getStatus() == 0)
			Assert.isTrue(a.getCreditCard() != null);
		else
			Assert.isTrue(a.getCreditCard() == null);
		return this.applicationRepository.save(a);
	}
	public Collection<Application> getMyApplications(final int handyWorkerId) {
		return this.getMyApplications(handyWorkerId);
	}

	public Collection<Application> findAllApplicationCustomer(final int customerId) {
		return this.applicationRepository.findAllCustomerApplication(customerId);
	}

	public List<Object[]> maxMavAvgDesvPriceOffered() {
		return this.applicationRepository.maxMinAvgDesvPriceOffered();
	}

	public Double ratioPendingApp() {
		return this.applicationRepository.ratioPendingApp();
	}

	public Double ratioAcceptedApp() {
		return this.applicationRepository.ratioAcepptedApp();
	}
	public Double ratioRejectedApp() {
		return this.applicationRepository.ratioRejectedApp();
	}

	public Double rationPendingAppStatus() {
		return this.applicationRepository.ratioPendingAppStatus2();
	}

	public List<String> getStatusApp(final int status) {
		final List<String> res = new ArrayList<>();
		if (status == 0) {
			res.add("Accepted");
			res.add("Aceptada");

		} else if (status == 1) {
			res.add("Pending");
			res.add("Pendiente");
		} else {
			res.add("Rejected");
			res.add("Rechazada");
		}

		return res;
	}
}
