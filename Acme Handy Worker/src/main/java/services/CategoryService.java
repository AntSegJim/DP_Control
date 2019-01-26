
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;
	@Autowired
	private FixUpTaskService	fixUpService;


	//private final Category		c	= this.rootCategory();

	//creating
	/*
	 * public Category create(final String name, final Category parent, final Collection<Category> soon) {
	 * final Category category = new Category();
	 * category.setName(name);
	 * category.setParent(parent);
	 * category.setSoon(soon);
	 * return category;
	 * }
	 */

	public Category create() {
		final Category category = new Category();
		category.setName("");
		category.setSpanishName("");

		//PREGUNTAR
		category.setParent(this.rootCategory());
		category.setSoon(new HashSet<Category>());
		return category;
	}

	//listing
	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	//updating
	public Category save(final Category category) {
		Assert.isTrue(category.getName() != null && category.getName() != "" && !category.getSoon().contains(category.getParent()), "CategoryService.save -> Primero");
		Assert.isTrue(!(category.getParent().equals(null)), "CategoryService.save -> Parent");
		if (category.getId() == 0)
			Assert.isTrue(!(category.getSpanishName().equals("") && category.getSpanishName() != null));
		Assert.isTrue(!(category == category.getParent()));
		final Collection<String> names = this.namesCategory();

		if (category.getId() == 0)
			Assert.isTrue(!names.contains(category.getName().toUpperCase()), "CategoryService.save -> Fallo");
		//		else {
		//			names.remove(category.getName().toUpperCase());
		//			Assert.isTrue(!names.contains(category.getName().toUpperCase()), "CategoryService.save -> Fallo");
		//		}

		final Collection<Category> sonOfParent = category.getParent().getSoon();
		sonOfParent.add(category);
		return this.categoryRepository.save(category);
	}
	//deleting
	public void delete(final Category category) {
		final UserAccount user = LoginService.getPrincipal();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"), "CategoryService.delete -> Authority");
		Assert.isTrue(!(category.getName().equals("CATEGORY")));

		final List<FixUpTask> lista = this.fixUpService.getFixUpByCategory(category.getName());
		for (int i = 0; i < lista.size(); i++) {
			final FixUpTask f = lista.get(i);
			f.setCategory(this.rootCategory());
			this.fixUpService.save(f);
		}

		this.categoryRepository.delete(category);

	}

	public Category rootCategory() {
		return this.categoryRepository.rootCategory();
	}

	public Category categoryByName(final String name) {
		return this.categoryRepository.categoryByName(name);
	}

	public Collection<String> namesCategory() {
		return this.categoryRepository.namesCategory();
	}

}
