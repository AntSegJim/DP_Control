
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest {

	@Autowired
	private PersonalRecordService	personalRecordService;


	//---------------------- Test ----------------------
	@Test
	public void testCreatePersonalRecord() {

		PersonalRecord personalRecord;
		personalRecord = this.personalRecordService.create();
		personalRecord.setNameHandyWorker("Raul");
		personalRecord.setPhoto("http://holadola.com");
		personalRecord.setEmail("raul@gmail.com");
		personalRecord.setPhone("+64 123456789");
		personalRecord.setLinkedInProfile("");

		Assert.isTrue(personalRecord.getNameHandyWorker().equals("Raul"));
		Assert.isTrue(personalRecord.getPhoto().equals("http://holadola.com"));
		Assert.isTrue(personalRecord.getEmail().equals("raul@gmail.com"));
		Assert.isTrue(personalRecord.getPhone().equals("+64 123456789"));
		Assert.isTrue(personalRecord.getLinkedInProfile().equals(""));
	}

	@Test
	public void testSavePersonalRecord() {
		PersonalRecord personalRecord;
		final PersonalRecord saved;
		Collection<PersonalRecord> personalsRecords;

		personalRecord = this.personalRecordService.create();
		personalRecord.setNameHandyWorker("Raul");
		personalRecord.setPhoto("http://holadola.com");
		personalRecord.setEmail("raul@gmail.com");
		personalRecord.setPhone("+64 123456789");
		personalRecord.setLinkedInProfile("");

		saved = this.personalRecordService.save(personalRecord);
		personalsRecords = this.personalRecordService.findAll();
		Assert.isTrue(personalsRecords.contains(saved));
	}

	@Test
	public void testDeletePersonalRecord() {
		PersonalRecord personalRecord;
		final PersonalRecord saved;
		Collection<PersonalRecord> personalsRecords;

		personalRecord = this.personalRecordService.create();
		personalRecord.setNameHandyWorker("Raul");
		personalRecord.setPhoto("http://holadola.com");
		personalRecord.setEmail("raul@gmail.com");
		personalRecord.setPhone("+64 123456789");
		personalRecord.setLinkedInProfile("");

		saved = this.personalRecordService.save(personalRecord);

		this.personalRecordService.delete(saved);
		personalsRecords = this.personalRecordService.findAll();
		Assert.isTrue(!personalsRecords.contains(saved));
	}
}
