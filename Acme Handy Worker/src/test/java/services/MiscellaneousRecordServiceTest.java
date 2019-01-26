
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateMiscellaneousRecord() {

		final Collection<String> comments = new HashSet<String>();
		comments.add("comentario1");
		MiscellaneousRecord miscellaneousRecord;
		miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("Acme Handy Worker");
		miscellaneousRecord.setLink("https://web.whatsapp.com/");
		miscellaneousRecord.setComments(comments);

		Assert.isTrue(miscellaneousRecord.getTitle().equals("Acme Handy Worker"));
		Assert.isTrue(miscellaneousRecord.getLink().equals("https://web.whatsapp.com/"));
		Assert.isTrue(miscellaneousRecord.getComments().equals(comments));
	}

	@Test
	public void testSaveMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord;
		final MiscellaneousRecord saved;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		final Collection<String> comments = new HashSet<String>();
		comments.add("comentario1");
		miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("Acme Handy Worker");
		miscellaneousRecord.setLink("https://web.whatsapp.com/");
		miscellaneousRecord.setComments(comments);

		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
		miscellaneousRecords = this.miscellaneousRecordService.findAll();
		Assert.isTrue(miscellaneousRecords.contains(saved));
	}

	@Test
	public void testDeleteMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord;
		final MiscellaneousRecord saved;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		final Collection<String> comments = new HashSet<String>();
		comments.add("comentario1");
		miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("Acme Handy Worker");
		miscellaneousRecord.setLink("https://web.whatsapp.com/");
		miscellaneousRecord.setComments(comments);

		saved = this.miscellaneousRecordService.save(miscellaneousRecord);

		this.miscellaneousRecordService.delete(saved);
		miscellaneousRecords = this.miscellaneousRecordService.findAll();
		Assert.isTrue(!miscellaneousRecords.contains(saved));
	}
}
