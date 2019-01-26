
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	@Autowired
	private ProfessionalRecordRepository	PRRepo;


	public ProfessionalRecord create() {
		final ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setNameCompany("");
		professionalRecord.setStartDate(new Date());
		professionalRecord.setEndDate(new Date());
		professionalRecord.setLink("");
		professionalRecord.setRole("");
		professionalRecord.setComments(new HashSet<String>());
		return professionalRecord;
	}

	//listing
	public Collection<ProfessionalRecord> findAll() {
		return this.PRRepo.findAll();
	}
	public ProfessionalRecord findOne(final int professionalRecordId) {
		return this.PRRepo.findOne(professionalRecordId);
	}

	//updating
	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(professionalRecord != null && professionalRecord.getStartDate() != null && professionalRecord.getStartDate().before(Calendar.getInstance().getTime()) && professionalRecord.getEndDate().before(Calendar.getInstance().getTime()));
		return this.PRRepo.save(professionalRecord);
	}

	//deleting
	public void delete(final ProfessionalRecord professionalRecord) {
		this.PRRepo.delete(professionalRecord);
	}

}
