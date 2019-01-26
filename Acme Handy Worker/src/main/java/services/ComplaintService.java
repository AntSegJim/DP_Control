
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private CustomerService		customerService;
	@Autowired
	private FixUpTaskService	FUTService;
	@Autowired
	private CurriculaService	curriculaService;


	//Metodos CRUD

	public Complaint create() {
		final Complaint res = new Complaint();
		res.setTicker(ComplaintService.generar(new Date()));
		res.setMoment(new Date());
		res.setDescription("");
		res.setNumberAttachments(0);
		res.setReferee(this.refereeService.create());
		res.setFixUpTask(this.FUTService.create());
		return res;
	}

	public Complaint create(final String ticker, final Date moment, final String description, final int numberAttachments, final Referee referee, final FixUpTask fixUpTask) {
		final Complaint complaint = new Complaint();
		complaint.setTicker(ticker);
		complaint.setMoment(moment);
		complaint.setDescription(description);
		complaint.setNumberAttachments(numberAttachments);
		complaint.setReferee(referee);
		complaint.setFixUpTask(fixUpTask);

		return complaint;
	}

	//listing
	public Collection<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}

	//updating
	public Complaint save(final Complaint complaint) {
		final Collection<String> allTickerComplaint = this.complaintRepository.tickerByComplaint();
		final Collection<String> allTickerFix = this.FUTService.allTickersFix();
		final Collection<String> allTickerCurricula = this.curriculaService.allTickersCurricula();
		Assert.isTrue(complaint != null && complaint.getMoment() != null && complaint.getFixUpTask() != null);
		Assert.isTrue(!allTickerComplaint.contains(complaint.getTicker()) && !allTickerFix.contains(complaint.getTicker()) && !allTickerCurricula.contains(complaint.getTicker()));
		return this.complaintRepository.save(complaint);
	}
	//	//deleting
	//	public void delete(final Complaint complaint) {
	//		this.complaintRepository.delete(complaint);
	//	}
	//------------------------Other business methods---------------------
	public Collection<Complaint> findAllByCustomer() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(Authority.CUSTOMER));

		final Customer c = this.customerService.customerByUserAccount(userAccount.getId());
		return this.complaintRepository.findAllCustomerComplaint(c.getId());
	}

	public Collection<Complaint> findAllByReferee() {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("REFEREE"));
		final Referee c = this.refereeService.refereeByUserAccount(userLoged.getId());
		return this.complaintRepository.findAllRefereeComplaint(c.getId());
	}

	public Collection<Complaint> findAllNoReferee() {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("REFEREE"));

		return this.complaintRepository.findAllNoRefereeComplaint();

	}
	public Complaint findOne(final int complaintId) {

		//final UserAccount userAccount = LoginService.getPrincipal();
		//final Customer c = this.customerService.customerByUserAccount(userAccount.getId());
		//Assert.isTrue(userAccount.getAuthorities().contains(Authority.CUSTOMER) && this.complaintRepository.findAllCustomerComplaint(c.getId()).contains(this.complaintRepository.findOne(complaintId)));

		return this.complaintRepository.findOne(complaintId);
	}

	public static String generar(final Date date) {
		final int tam = 6;
		final Integer ano = date.getYear() + 1900;
		final Integer mes = date.getMonth() + 1;
		final Integer dia = date.getDate();

		String day = dia.toString();
		String month = mes.toString();
		if (mes < 10)
			month = "0" + mes.toString();
		if (dia < 10)
			day = "0" + dia.toString();
		final String d = ano.toString().substring(ano.toString().length() - 2, ano.toString().length()) + month + day;

		String ticker = "-";
		final String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return d + ticker;

	}
	public Collection<String> allTickersComplaint() {
		return this.complaintRepository.tickerByComplaint();
	}

	public Collection<Complaint> findAllCustomerComplaint(final Integer id) {
		return this.complaintRepository.findAllCustomerComplaint(id);
	}

	public void selfAssignReferee(final int complaintId) {
		Assert.isTrue(complaintId != 0);

		Referee referee;
		Complaint complaint;
		final UserAccount userLoged = LoginService.getPrincipal();

		referee = this.refereeService.refereeByUserAccount(userLoged.getId());
		Assert.notNull(referee);

		complaint = this.complaintRepository.findOne(complaintId);
		Assert.notNull(complaint);

		complaint.setReferee(referee);
		this.complaintRepository.save(complaint);

	}

}
