
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
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	// ------------ Service under test ------------
	@Autowired
	private WarrantyService	warrantyService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateWarranty() {
		Warranty w;
		w = this.warrantyService.create();
		w.setDraftMode(1);
		final Collection<String> laws = new HashSet<String>();
		laws.add("law1");
		w.setLaws(laws);
		w.setTitle("Warranty Title");
		final Collection<String> terms = new HashSet<String>();
		terms.add("term1");
		w.setTerms(terms);
		Assert.isTrue(w.getTitle().equals("Warranty Title") && w.getLaws().contains("law1"));
	}

	@Test
	public void testSaveWarranty() {
		Warranty w, saved;
		Collection<Warranty> ws;
		w = this.warrantyService.create();
		w.setDraftMode(1);
		final Collection<String> laws = new HashSet<String>();
		laws.add("law1");
		w.setLaws(laws);
		w.setTitle("Warranty Title");
		final Collection<String> terms = new HashSet<String>();
		terms.add("term1");
		w.setTerms(terms);

		saved = this.warrantyService.save(w);
		ws = this.warrantyService.findAll();
		Assert.isTrue(ws.contains(saved));
	}

	@Test
	public void testDeleteWarranty() {
		Warranty w, saved;
		Collection<Warranty> ws;
		w = this.warrantyService.create();
		w.setDraftMode(1);
		final Collection<String> laws = new HashSet<String>();
		laws.add("law1");
		w.setLaws(laws);
		w.setTitle("Warranty Title");
		final Collection<String> terms = new HashSet<String>();
		terms.add("term1");
		w.setTerms(terms);
		saved = this.warrantyService.save(w);
		this.warrantyService.delete(saved);
		ws = this.warrantyService.findAll();
		Assert.isTrue(!ws.contains(saved));
	}
}
