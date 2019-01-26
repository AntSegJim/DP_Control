
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndoserRecordRepository;
import domain.EndoserRecord;

@Service
@Transactional
public class EndoserRecordService {

	@Autowired
	private EndoserRecordRepository	ERRepo;


	public EndoserRecord create() {
		final EndoserRecord endoserRecord = new EndoserRecord();
		endoserRecord.setName("");
		endoserRecord.setEmail("");
		endoserRecord.setPhoneNumber("");
		endoserRecord.setLinkedln("");
		endoserRecord.setComments(new HashSet<String>());
		return endoserRecord;
	}

	//listing
	public Collection<EndoserRecord> findAll() {
		return this.ERRepo.findAll();
	}
	public EndoserRecord findOne(final int endoserRecordId) {
		return this.ERRepo.findOne(endoserRecordId);
	}

	//updating
	public EndoserRecord save(final EndoserRecord endoserRecord) {
		Assert.isTrue(endoserRecord != null && endoserRecord.getName() != null && endoserRecord.getName() != "");
		return this.ERRepo.save(endoserRecord);
	}

	//deleting
	public void delete(final EndoserRecord endoserRecord) {
		this.ERRepo.delete(endoserRecord);
	}
}
