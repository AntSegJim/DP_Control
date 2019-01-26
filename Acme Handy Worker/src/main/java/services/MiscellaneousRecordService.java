
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	private MiscellaneousRecordRepository	MRRepo;


	public MiscellaneousRecord create() {
		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle("");
		miscellaneousRecord.setLink("");
		miscellaneousRecord.setComments(new HashSet<String>());
		return miscellaneousRecord;
	}

	//listing
	public Collection<MiscellaneousRecord> findAll() {
		return this.MRRepo.findAll();
	}
	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		return this.MRRepo.findOne(miscellaneousRecordId);
	}

	//updating
	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.isTrue(miscellaneousRecord != null && miscellaneousRecord.getTitle() != null && miscellaneousRecord.getTitle() != "");
		return this.MRRepo.save(miscellaneousRecord);
	}

	//deleting
	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		this.MRRepo.delete(miscellaneousRecord);
	}

}
