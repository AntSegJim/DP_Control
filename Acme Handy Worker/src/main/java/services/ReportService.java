
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.LoginService;
import security.UserAccount;
import domain.Attachment;
import domain.Complaint;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private RefereeService		refereeService;


	//Metodos CRUD

	public Report create() {
		final Report res = new Report();
		res.setMoment(new Date());
		res.setDescription("");
		res.setPublished(0);
		res.setAttachment(new HashSet<Attachment>());
		res.setComplaint(this.complaintService.create());
		return res;
	}

	public Report create(final Date moment, final String description, final int published, final Collection<Attachment> attachment, final Complaint complaint) {
		final Report report = new Report();
		report.setMoment(moment);
		report.setDescription(description);
		report.setPublished(published);
		report.setAttachment(attachment);
		report.setComplaint(complaint);

		return report;
	}
	//listing
	public Collection<Report> findAll() {
		return this.reportRepository.findAll();
	}

	public Report findOne(final int reportId) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("REFEREE"));

		return this.reportRepository.findOne(reportId);
	}
	//updating
	public Report save(final Report report) {
		final UserAccount ac = LoginService.getPrincipal();
		Assert.isTrue(ac.getAuthorities().iterator().next().getAuthority().equals("REFEREE") && this.complaintService.findAllByReferee().contains(report.getComplaint()), "acceso denegado");

		Assert.isTrue(report != null && report.getComplaint() != null && !report.getAttachment().isEmpty() && (report.getPublished() == 0 || report.getPublished() == 1));
		Assert.isTrue(!(report.getMoment().equals(null)));
		Assert.isTrue(!(report.getDescription().equals("")));
		return this.reportRepository.save(report);
	}

	public void delete(final Report report) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("REFEREE") && report.getPublished() == 0);
		this.reportRepository.delete(report);
	}
	public Collection<Report> findAllReportReferee() {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("REFEREE"));
		final Referee c = this.refereeService.refereeByUserAccount(userLoged.getId());
		return this.reportRepository.findAllReportReferee(c.getId());
	}
	public Collection<Report> findAllReportComplaint(final int Id) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("REFEREE"));
		return this.reportRepository.findAllReportComplaint(Id);
	}
	public Collection<Report> findAllReportRefereeId(final int i) {

		return this.reportRepository.findAllReportReferee(i);
	}

	public List<Object[]> maxMinAvgDesv() {
		return this.reportRepository.maxMinAvgDesvReportNotes();
	}

}
