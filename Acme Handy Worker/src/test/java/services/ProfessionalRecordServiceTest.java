
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest {

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateProfessionalRecord() {

		ProfessionalRecord professionalRecord;
		professionalRecord = this.professionalRecordService.create();
		professionalRecord.setNameCompany("am");
		professionalRecord.setStartDate(new Date(02 / 04 / 2018));
		professionalRecord.setEndDate(new Date(02 / 05 / 2018));
		professionalRecord.setLink("http://adios.com");
		professionalRecord.setRole("customer");
		professionalRecord.setComments(new HashSet<String>());

		Assert.isTrue(professionalRecord.getNameCompany().equals("am"));
		Assert.isTrue(professionalRecord.getStartDate().equals(new Date(02 / 04 / 2018)));
		Assert.isTrue(professionalRecord.getEndDate().equals(new Date(02 / 05 / 2018)));
		Assert.isTrue(professionalRecord.getLink().equals("http://adios.com"));
		Assert.isTrue(professionalRecord.getRole().equals("customer"));
		Assert.isTrue(professionalRecord.getComments().equals(new HashSet<String>()));
	}

	@Test
	public void testSaveProfessionalRecord() {
		ProfessionalRecord professionalRecord;
		final ProfessionalRecord saved;
		Collection<ProfessionalRecord> professionalsRecords;

		professionalRecord = this.professionalRecordService.create();
		professionalRecord.setNameCompany("am");
		professionalRecord.setStartDate(new Date(02 / 04 / 2018));
		professionalRecord.setEndDate(new Date(02 / 05 / 2018));
		professionalRecord.setLink("http://adios.com");
		professionalRecord.setRole("customer");
		professionalRecord.setComments(new HashSet<String>());

		saved = this.professionalRecordService.save(professionalRecord);
		professionalsRecords = this.professionalRecordService.findAll();
		Assert.isTrue(professionalsRecords.contains(saved));
	}

	@Test
	public void testDeleteProfessionalRecord() {
		ProfessionalRecord professionalRecord;
		final ProfessionalRecord saved;
		Collection<ProfessionalRecord> professionalsRecords;

		professionalRecord = this.professionalRecordService.create();
		professionalRecord.setNameCompany("am");
		professionalRecord.setStartDate(new Date(02 / 04 / 2018));
		professionalRecord.setEndDate(new Date(02 / 05 / 2018));
		professionalRecord.setLink("http://adios.com");
		professionalRecord.setRole("customer");
		professionalRecord.setComments(new HashSet<String>());

		saved = this.professionalRecordService.save(professionalRecord);

		this.professionalRecordService.delete(saved);
		professionalsRecords = this.professionalRecordService.findAll();
		Assert.isTrue(!professionalsRecords.contains(saved));
	}
}
