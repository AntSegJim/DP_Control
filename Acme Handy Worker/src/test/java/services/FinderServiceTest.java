
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
import domain.Category;
import domain.Finder;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;
	@Autowired
	private CategoryService	categoryService;
	@Autowired
	private WarrantyService	warrantyService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateFinder() {
		Finder f;
		final Warranty warranty, savedWarranty;
		warranty = this.warrantyService.create();
		final Collection<String> laws = warranty.getLaws();
		laws.add("Ley1");
		final Collection<String> terms = warranty.getTerms();
		terms.add("Term1");
		warranty.setLaws(laws);
		warranty.setTerms(terms);
		warranty.setTitle("TituloWarranty");
		warranty.setDraftMode(1);
		savedWarranty = this.warrantyService.save(warranty);

		Category category, savedCategory;
		category = this.categoryService.create();
		category.setName("CategoriaPrimera");
		category.setParent(category);
		savedCategory = this.categoryService.save(category);

		f = this.finderService.create();
		f.setAddress("Direccion");
		f.setDescription("Description");
		f.setEndDate(new Date());
		f.setHighPrice(12.);
		f.setLowPrice(10.);
		f.setStartDate(new Date());
		f.setWarranty(savedWarranty);
		f.setCategory(savedCategory);
		f.setMoment(new Date());
		Assert.isTrue(f.getMoment() != null);
	}
	@Test
	public void testSaveFinder() {
		super.authenticate("admin");
		Finder f, savedF;
		f = this.finderService.create();
		f = this.finderService.create();
		f.setAddress("Direccion");
		f.setDescription("Description");
		f.setEndDate(new Date());
		f.setHighPrice(12.);
		f.setLowPrice(10.);
		f.setStartDate(new Date());

		f.setMoment(new Date());
		f.setMoment(new Date());

		final Category c = f.getCategory();
		c.setName("hola");
		c.setSoon(new HashSet<Category>());
		c.setParent(this.categoryService.findOne(5625));

		final Warranty w, savedw;
		w = this.warrantyService.create();
		w.setDraftMode(1);
		final Collection<String> laws = new HashSet<String>();
		laws.add("law1");
		w.setLaws(laws);
		final Collection<String> terms = new HashSet<String>();
		laws.add("term1");
		w.setTerms(terms);
		w.setTitle("TitleWarranty");
		savedw = this.warrantyService.save(w);

		f.setWarranty(savedw);
		f.setCategory(this.categoryService.save(c));

		savedF = this.finderService.save(f);
		final Collection<Finder> fs = this.finderService.findAll();
		Assert.isTrue(fs.contains(savedF));
		super.authenticate(null);
	}

}
