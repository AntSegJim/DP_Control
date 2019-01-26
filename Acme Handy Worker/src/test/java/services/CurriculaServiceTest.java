
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndoserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService			curriS;
	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private PersonalRecordService		personalRS;
	@Autowired
	private EducationRecordService		educationRS;
	@Autowired
	private EndoserRecordService		endoserRS;
	@Autowired
	private MiscellaneousRecordService	miscellaneousRS;
	@Autowired
	private ProfessionalRecordService	professionalRS;


	@Test
	public void testCreateCurricula() {
		super.authenticate("handyWorker");
		//Creamos un handy worker
		final HandyWorker h;
		h = this.handyWorkerService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola");
		ua.setUsername("Antonio");
		ua.setAuthorities(h.getUserAccount().getAuthorities());
		h.setName("Antonio");
		h.setAddress("calle Arahal");
		h.setEmail("antonio@us.es");
		h.setPhone("654321123");
		h.setUserAccount(ua);
		h.setSurname("SurnameHandy");

		//Creamos un personal record
		PersonalRecord personalRecord;
		personalRecord = this.personalRS.create();
		personalRecord.setNameHandyWorker("Raul");
		personalRecord.setPhoto("http://holadola.com");
		personalRecord.setEmail("raul@gmail.com");
		personalRecord.setPhone("+64 123456789");
		personalRecord.setLinkedInProfile("");

		//Creamos uns lista de education record
		EducationRecord educationRecord;
		educationRecord = this.educationRS.create();
		educationRecord.setTitleDiploma("hola");
		educationRecord.setStartDate(new Date(02 / 04 / 2018));
		educationRecord.setEndDate(new Date(02 / 05 / 2018));
		educationRecord.setInstitution("CL");
		educationRecord.setLink("http://hola.com");
		educationRecord.setComment(new HashSet<String>());
		final Collection<EducationRecord> educationsRecords = new HashSet<>();
		Collections.addAll(educationsRecords, educationRecord);

		//Creamos una lista de endoser record
		final Collection<String> commentsEnd = new HashSet<String>();
		commentsEnd.add("comentario1");
		EndoserRecord endoserRecord;
		endoserRecord = this.endoserRS.create();
		endoserRecord.setName("Acme Handy Worker");
		endoserRecord.setEmail("cristian@hotmail.com");
		endoserRecord.setPhoneNumber("+64 657678987");
		endoserRecord.setLinkedln("https://web.whatsapp.com/");
		endoserRecord.setComments(commentsEnd);
		final Collection<EndoserRecord> endosersRecords = new HashSet<>();
		Collections.addAll(endosersRecords, endoserRecord);

		//Creamos una lista de miscellaneous record
		final Collection<String> commentsMis = new HashSet<String>();
		commentsMis.add("comentario1");
		MiscellaneousRecord miscellaneousRecord;
		miscellaneousRecord = this.miscellaneousRS.create();
		miscellaneousRecord.setTitle("Acme Handy Worker");
		miscellaneousRecord.setLink("https://web.whatsapp.com/");
		miscellaneousRecord.setComments(commentsMis);
		final Collection<MiscellaneousRecord> miscellaneousRecords = new HashSet<>();
		Collections.addAll(miscellaneousRecords, miscellaneousRecord);

		//Creamos una lista de professionals records
		ProfessionalRecord professionalRecord;
		professionalRecord = this.professionalRS.create();
		professionalRecord.setNameCompany("am");
		professionalRecord.setStartDate(new Date(02 / 04 / 2018));
		professionalRecord.setEndDate(new Date(02 / 05 / 2018));
		professionalRecord.setLink("http://adios.com");
		professionalRecord.setRole("customer");
		professionalRecord.setComments(new HashSet<String>());
		final Collection<ProfessionalRecord> professionalsRecords = new HashSet<>();
		Collections.addAll(professionalsRecords, professionalRecord);

		//Creamos un curricula
		Curricula curricula;
		curricula = this.curriS.create();
		curricula.setHandyWorker(h);
		curricula.setPersonalRecord(personalRecord);
		curricula.setEducationsRecords(educationsRecords);
		curricula.setEndosersRecords(endosersRecords);
		curricula.setMiscellaneousRecords(miscellaneousRecords);
		curricula.setProfessionalsRecords(professionalsRecords);

		Assert.isTrue(curricula.getTicker() != null);
		Assert.isTrue(curricula.getPersonalRecord().equals(personalRecord));
		Assert.isTrue(curricula.getEducationsRecords().equals(educationsRecords));
		Assert.isTrue(curricula.getEndosersRecords().equals(endosersRecords));
		Assert.isTrue(curricula.getMiscellaneousRecords().equals(miscellaneousRecords));
		Assert.isTrue(curricula.getProfessionalsRecords().equals(professionalsRecords));
		Assert.isTrue(curricula.getHandyWorker().equals(h));
		super.authenticate(null);
	}

	@Test
	public void saveCurriculaTest() {

		super.authenticate("handyWorker");

		//Creamos un personal record
		PersonalRecord personalRecord, savedp;
		personalRecord = this.personalRS.create();
		personalRecord.setNameHandyWorker("Raul");
		personalRecord.setPhoto("http://holadola.com");
		personalRecord.setEmail("raul@gmail.com");
		personalRecord.setPhone("+64 123456789");
		personalRecord.setLinkedInProfile("");
		savedp = this.personalRS.save(personalRecord);

		//Creamos uns lista de education record
		EducationRecord educationRecord, savede;
		educationRecord = this.educationRS.create();
		educationRecord.setTitleDiploma("hola");
		educationRecord.setStartDate(new Date(02 / 04 / 2018));
		educationRecord.setEndDate(new Date(02 / 05 / 2018));
		educationRecord.setInstitution("CL");
		educationRecord.setLink("http://hola.com");
		educationRecord.setComment(new HashSet<String>());
		savede = this.educationRS.save(educationRecord);
		final Collection<EducationRecord> educationsRecords = new HashSet<>();
		Collections.addAll(educationsRecords, savede);

		//Creamos una lista de endoser record
		final Collection<String> commentsEnd = new HashSet<String>();
		commentsEnd.add("comentario1");
		EndoserRecord endoserRecord, saveden;
		endoserRecord = this.endoserRS.create();
		endoserRecord.setName("Acme Handy Worker");
		endoserRecord.setEmail("cristian@hotmail.com");
		endoserRecord.setPhoneNumber("+64 657678987");
		endoserRecord.setLinkedln("https://web.whatsapp.com/");
		endoserRecord.setComments(commentsEnd);
		saveden = this.endoserRS.save(endoserRecord);
		final Collection<EndoserRecord> endosersRecords = new HashSet<>();
		Collections.addAll(endosersRecords, saveden);

		//Creamos una lista de miscellaneous record
		final Collection<String> commentsMis = new HashSet<String>();
		commentsMis.add("comentario1");
		MiscellaneousRecord miscellaneousRecord, savedm;
		miscellaneousRecord = this.miscellaneousRS.create();
		miscellaneousRecord.setTitle("Acme Handy Worker");
		miscellaneousRecord.setLink("https://web.whatsapp.com/");
		miscellaneousRecord.setComments(commentsMis);
		savedm = this.miscellaneousRS.save(miscellaneousRecord);
		final Collection<MiscellaneousRecord> miscellaneousRecords = new HashSet<>();
		Collections.addAll(miscellaneousRecords, savedm);

		//Creamos una lista de professionals records
		ProfessionalRecord professionalRecord, savedpr;
		professionalRecord = this.professionalRS.create();
		professionalRecord.setNameCompany("am");
		professionalRecord.setStartDate(new Date(02 / 04 / 2018));
		professionalRecord.setEndDate(new Date(02 / 05 / 2018));
		professionalRecord.setLink("http://adios.com");
		professionalRecord.setRole("customer");
		professionalRecord.setComments(new HashSet<String>());
		savedpr = this.professionalRS.save(professionalRecord);
		final Collection<ProfessionalRecord> professionalsRecords = new HashSet<>();
		Collections.addAll(professionalsRecords, savedpr);

		//Creamos un curricula

		Curricula curricula, saved;
		Collection<Curricula> curriculas = new HashSet<>();
		curricula = this.curriS.create();
		curricula.setPersonalRecord(savedp);
		curricula.setEducationsRecords(educationsRecords);
		curricula.setEndosersRecords(endosersRecords);
		curricula.setMiscellaneousRecords(miscellaneousRecords);
		curricula.setProfessionalsRecords(professionalsRecords);

		//Guardamos
		saved = this.curriS.save(curricula);
		curriculas = this.curriS.findAll();
		Assert.isTrue(curriculas.contains(saved));
		super.authenticate(null);
	}
}
