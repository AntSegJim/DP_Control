
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	ERRepo;


	public EducationRecord create() {
		final EducationRecord educationRecord = new EducationRecord();
		educationRecord.setTitleDiploma("");
		educationRecord.setStartDate(new Date());
		educationRecord.setEndDate(new Date());
		educationRecord.setInstitution("");
		educationRecord.setLink("");
		educationRecord.setComment(new HashSet<String>());
		return educationRecord;
	}

	//listing
	public Collection<EducationRecord> findAll() {
		return this.ERRepo.findAll();
	}
	public EducationRecord findOne(final int educationRecordId) {
		return this.ERRepo.findOne(educationRecordId);
	}

	//updating
	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.isTrue(educationRecord != null && educationRecord.getTitleDiploma() != null && educationRecord.getTitleDiploma() != "" && educationRecord.getStartDate() != null && educationRecord.getStartDate().before(new Date())
			&& educationRecord.getEndDate().before(new Date()) && educationRecord.getInstitution() != null && educationRecord.getInstitution() != "");
		return this.ERRepo.save(educationRecord);
	}

	//deleting
	public void delete(final EducationRecord educationRecord) {
		this.ERRepo.delete(educationRecord);
	}
}
