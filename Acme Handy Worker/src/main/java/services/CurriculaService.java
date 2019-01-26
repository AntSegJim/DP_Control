
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndoserRecord;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository		CRepo;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private PersonalRecordService	personalRecordService;
	@Autowired
	private ActorService			actorS;


	public Curricula create() {
		final Curricula curricula = new Curricula();
		final UserAccount user = LoginService.getPrincipal();
		curricula.setTicker(CurriculaService.generateTicker(new Date()));
		curricula.setHandyWorker(this.handyWorkerService.handyWorkerUserAccount(user.getId()));
		curricula.setEducationsRecords(new HashSet<EducationRecord>());
		curricula.setEndosersRecords(new HashSet<EndoserRecord>());
		curricula.setMiscellaneousRecords(new HashSet<MiscellaneousRecord>());
		curricula.setProfessionalsRecords(new HashSet<ProfessionalRecord>());
		curricula.setPersonalRecord(this.personalRecordService.create());
		return curricula;
	}

	//listing
	public Collection<Curricula> findAll() {
		return this.CRepo.findAll();
	}
	public Curricula findOne(final int curriculaId) {
		return this.CRepo.findOne(curriculaId);
	}

	//updating
	public Curricula save(final Curricula curricula) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Collection<String> allTickerCurricula = this.allTickersCurricula();
		final Collection<String> allTickerFix = this.fixUpTaskService.allTickersFix();
		final Collection<String> allTickerComplaint = this.complaintService.allTickersComplaint();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER"));
		Assert.isTrue(curricula != null && curricula.getTicker() != null && curricula.getTicker() != "" && curricula.getPersonalRecord() != null && curricula.getEducationsRecords() != null && curricula.getEndosersRecords() != null
			&& curricula.getMiscellaneousRecords() != null && curricula.getProfessionalsRecords() != null);
		Assert.isTrue(!allTickerComplaint.contains(curricula.getTicker()) && !allTickerFix.contains(curricula.getTicker()) && !allTickerCurricula.contains(curricula.getTicker()));

		return this.CRepo.save(curricula);
	}
	//deleting
	//	public void delete(final Curricula curricula) {
	//		this.CRepo.delete(curricula);
	//	}

	public static String generateTicker(final Date date) {
		final int tam = 6;
		final Integer ano = date.getYear() + 1900;
		final Integer mes = date.getMonth() + 1;
		final Integer dia = date.getDate();
		final String d = ano.toString().substring(ano.toString().length() - 2, ano.toString().length()) + mes.toString() + dia.toString();

		String ticker = "-";
		final String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return d + ticker;

	}
	public Collection<String> allTickersCurricula() {
		return this.CRepo.tickerByCurricula();
	}
	public Collection<Curricula> findAllHandyWorkerCurricula(final Integer id) {
		return this.CRepo.findAllHandyWorkerCurricula(id);
	}

}
