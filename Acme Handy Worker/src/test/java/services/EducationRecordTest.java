
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

import utilities.AbstractTest;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordTest extends AbstractTest {

	@Autowired
	private EducationRecordService	ERService;


	@Test
	public void testCreateEducationRecord() {
		EducationRecord educationRecord;

		educationRecord = this.ERService.create();
		educationRecord.setTitleDiploma("hola");
		educationRecord.setStartDate(new Date(02 / 04 / 2018));
		educationRecord.setEndDate(new Date(02 / 05 / 2018));
		educationRecord.setInstitution("CL");
		educationRecord.setLink("http://hola.com");
		educationRecord.setComment(new HashSet<String>());

		Assert.isTrue(educationRecord.getTitleDiploma().equals("hola"));
		Assert.isTrue(educationRecord.getStartDate().equals(new Date(02 / 04 / 2018)));
		Assert.isTrue(educationRecord.getEndDate().equals(new Date(02 / 05 / 2018)));
		Assert.isTrue(educationRecord.getInstitution().equals("CL"));
		Assert.isTrue(educationRecord.getLink().equals("http://hola.com"));
		Assert.isTrue(educationRecord.getComment().equals(new HashSet<String>()));
	}
	@Test
	public void testSaveEducationRecord() {
		//Education
		EducationRecord educationRecord, savedE;
		//Lista de education
		final Collection<EducationRecord> educationRecords;

		//crear y guardar un educationRecord
		educationRecord = this.ERService.create();
		educationRecord.setTitleDiploma("adios");
		educationRecord.setStartDate(new Date(02 / 04 / 2018));
		educationRecord.setEndDate(new Date(02 / 05 / 2018));
		educationRecord.setInstitution("FP");
		educationRecord.setLink("http://hola.com");
		educationRecord.setComment(new HashSet<String>());

		savedE = this.ERService.save(educationRecord);

		//devulvo todos los educationRecords
		educationRecords = this.ERService.findAll();
		//Compruebo
		Assert.isTrue(educationRecords.contains(savedE));
	}

	@Test
	public void testDeleteEducationRecord() {
		EducationRecord educationRecord, saved;
		final Collection<EducationRecord> educationsRecords;
		educationRecord = this.ERService.create();

		educationRecord.setTitleDiploma("adios");
		educationRecord.setStartDate(new Date(02 / 04 / 2018));
		educationRecord.setEndDate(new Date(02 / 05 / 2018));
		educationRecord.setInstitution("FP");
		educationRecord.setLink("http://hola.com");
		educationRecord.setComment(new HashSet<String>());

		saved = this.ERService.save(educationRecord);
		this.ERService.delete(saved);
		educationsRecords = this.ERService.findAll();
		Assert.isTrue(!educationsRecords.contains(saved));
	}

}
