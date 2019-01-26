
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r join r.complaint c where c.referee.id=?1")
	public Collection<Report> findAllReportReferee(int i);

	@Query("select avg(1.0*(select count(n.report) from Note n where n.report.id = r.id)),	 min(1.0*(select count(n.report) from Note n where n.report.id = r.id)),	 max(1.0*(select count(n.report) from Note n where n.report.id = r.id)),	  sqrt(1.0*sum(1.0*(select count(n.report) from Note n where n.report.id = r.id) *	  (select count(n.report) from Note n where n.report.id = r.id)) /	 count(r) - avg(1.0*(select count(n.report) from Note n where n.report.id = r.id))*	 avg(1.0*(select count(n.report) from Note n where n.report.id = r.id))) from Report r")
	public List<Object[]> maxMinAvgDesvReportNotes();

	@Query("select r from Report r where r.complaint.id=?1")
	public Collection<Report> findAllReportComplaint(int i);

	@Query("select r from  Report r  join r.complaint c join c.fixUpTask f where f.customer.id=?1 and r.published=1")
	public Collection<Report> findAllReportCustomerId(int i);
}
