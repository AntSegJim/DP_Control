
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EndoserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndoserRecordServiceTest extends AbstractTest {

	@Autowired
	private EndoserRecordService	endoserRecordService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateEndorseRecord() {

		//pasos previos
		final Collection<String> comments = new HashSet<String>();
		comments.add("comentario1");
		EndoserRecord endoserRecord;
		endoserRecord = this.endoserRecordService.create();
		endoserRecord.setName("Acme Handy Worker");
		endoserRecord.setEmail("cristian@hotmail.com");
		endoserRecord.setPhoneNumber("+64 657678987");
		endoserRecord.setLinkedln("https://web.whatsapp.com/");
		endoserRecord.setComments(comments);

		Assert.isTrue(endoserRecord.getName().equals("Acme Handy Worker"));
		Assert.isTrue(endoserRecord.getEmail().equals("cristian@hotmail.com"));
		Assert.isTrue(endoserRecord.getPhoneNumber().equals("+64 657678987"));
		Assert.isTrue(endoserRecord.getLinkedln().equals("https://web.whatsapp.com/"));
		Assert.isTrue(endoserRecord.getComments().equals(comments));
	}

	@Test
	public void testSaveEndorseRecord() {
		EndoserRecord endorserRecord;
		final EndoserRecord saved;
		Collection<EndoserRecord> endorseRecords;

		final Collection<String> comments = new HashSet<String>();
		comments.add("comentario1");
		endorserRecord = this.endoserRecordService.create();
		endorserRecord.setName("Acme Handy Worker");
		endorserRecord.setEmail("cristian@hotmail.com");
		endorserRecord.setPhoneNumber("657678987");
		endorserRecord.setLinkedln("https://web.whatsapp.com/");
		endorserRecord.setComments(comments);

		saved = this.endoserRecordService.save(endorserRecord);
		endorseRecords = this.endoserRecordService.findAll();
		Assert.isTrue(endorseRecords.contains(saved));
	}

	@Test
	public void testDeleteEndorseRecord() {
		EndoserRecord endorserRecord;
		final EndoserRecord saved;
		final Collection<EndoserRecord> endoserRecords;

		final Collection<String> comments = new HashSet<String>();
		comments.add("comentario1");
		endorserRecord = this.endoserRecordService.create();
		endorserRecord.setName("Acme Handy Worker");
		endorserRecord.setEmail("cristian@hotmail.com");
		endorserRecord.setPhoneNumber("657678987");
		endorserRecord.setLinkedln("https://web.whatsapp.com/");
		endorserRecord.setComments(comments);

		saved = this.endoserRecordService.save(endorserRecord);

		this.endoserRecordService.delete(saved);
		endoserRecords = this.endoserRecordService.findAll();
		Assert.isTrue(!endoserRecords.contains(saved));
	}

}
