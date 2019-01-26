
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	@Autowired
	private PersonalRecordRepository	PRRepo;


	public PersonalRecord create() {
		final PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setNameHandyWorker("");
		personalRecord.setPhoto("");
		personalRecord.setEmail("");
		personalRecord.setPhone("");
		personalRecord.setLinkedInProfile("");
		return personalRecord;
	}

	//listing
	public Collection<PersonalRecord> findAll() {
		return this.PRRepo.findAll();
	}
	public PersonalRecord findOne(final int personalRecordId) {
		return this.PRRepo.findOne(personalRecordId);
	}

	//updating
	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.isTrue(personalRecord != null && personalRecord.getNameHandyWorker() != null && personalRecord.getNameHandyWorker() != "");
		return this.PRRepo.save(personalRecord);
	}

	//deleting
	public void delete(final PersonalRecord personalRecord) {
		this.PRRepo.delete(personalRecord);
	}

}
