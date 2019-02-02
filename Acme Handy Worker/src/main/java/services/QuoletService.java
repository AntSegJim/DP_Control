
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuoletRepository;
import domain.FixUpTask;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository	quoletRepository;


	public Quolet create() {
		final Quolet q = new Quolet();
		q.setTicker(ComplaintService.generar(new Date()));
		q.setMoment(new Date());
		q.setBody("");
		q.setPicture("");
		q.setDraftMode(1);
		q.setFixUpTask(new FixUpTask());
		return q;
	}

	public Collection<Quolet> findAll() {
		return this.quoletRepository.findAll();
	}

	public Collection<Quolet> findAllQuoletsByFix(final int id) {
		return this.quoletRepository.getAllQuoletsByFix(id);
	}

	public Collection<Quolet> findAllQuoletsByFixPublished(final int id) {
		return this.quoletRepository.getAllQuoletsByFixPublished(id);
	}

	public Quolet findOne(final int quoletId) {
		return this.quoletRepository.findOne(quoletId);
	}

	public Quolet save(final Quolet q) {
		final Quolet savedQuolet;
		final Quolet oldQuolet = this.quoletRepository.findOne(q.getId());
		if (q.getId() == 0) {

			q.setMoment(new Date());
			Assert.isTrue(q.getTicker() != "" && q.getTicker() != null, "Ticker no válido");
			Assert.isTrue(q.getMoment() != null, "Moment no válido");
			Assert.isTrue(q.getBody() != "" && q.getBody() != null && q.getBody().length() < 100, "Body no válido");
			Assert.isTrue(q.getDraftMode() == 0 || q.getDraftMode() == 1, "DraftMode no válido");
			Assert.isTrue(q.getFixUpTask() != null, "FixUpTask nula no válida");
			savedQuolet = this.quoletRepository.save(q);
		} else if (oldQuolet != null && oldQuolet.getDraftMode() == 1) {
			q.setMoment(new Date());
			Assert.isTrue(q.getTicker() != "" && q.getTicker() != null, "Ticker no válido");
			Assert.isTrue(q.getMoment() != null, "Moment no válido");
			Assert.isTrue(q.getBody() != "" && q.getBody() != null && q.getBody().length() < 100, "Body no válido");
			Assert.isTrue(q.getDraftMode() == 0 || q.getDraftMode() == 1, "DraftMode no válido");
			Assert.isTrue(q.getFixUpTask() != null, "FixUpTask nula no válida");
			savedQuolet = this.quoletRepository.save(q);
		} else
			savedQuolet = q;
		return savedQuolet;
	}

	public void delete(final Quolet q) {
		final Quolet oldQuolet = this.quoletRepository.findOne(q.getId());
		Assert.notNull(oldQuolet, "Tratando de borrar Quolet en modo no borrador");
		if (oldQuolet.getDraftMode() == 1)
			this.quoletRepository.delete(q);
	}
	public Double a() {
		return this.quoletRepository.a();
	}

	public Double b() {
		return this.quoletRepository.b();
	}

	public Double c() {
		return this.quoletRepository.c();
	}
}
