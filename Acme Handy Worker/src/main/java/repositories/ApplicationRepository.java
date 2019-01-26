
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	//NO esta corregida
	@Query("select count(r)*1.0/(select(a) from Application a) from Application r where r.status=1")
	public double pendingRatio();

	@Query("select a from Application a join a.fixUpTask f where f.customer.id = ?1")
	public Collection<Application> findAllCustomerApplication(Integer id);

	@Query("select a from Application a where a.handyWorker.id = ?1")
	public Collection<Application> getMyApplications(int handyWorkerId);

	@Query("select avg(a.price),min(a.price), max(a.price), sqrt(sum((select a.price - avg(e.price) from Application e) * (select a.price - avg(e.price) from Application e))/count(a)) from Application a")
	public List<Object[]> maxMinAvgDesvPriceOffered();

	@Query("select count(r)*1.0/(select count(a) from Application a) from Application r where r.status=1")
	public Double ratioPendingApp();

	@Query("select count(r)*1.0/(select count(a) from Application a) from Application r where r.status=0")
	public Double ratioAcepptedApp();

	@Query("select count(r)*1.0/(select count(a) from Application a) from Application r where r.status=2")
	public Double ratioRejectedApp();

	@Query("select count(a)*1.0/(select count(a) from Application a where a.status=1) from Application a join a.fixUpTask x where a.status = 1 and date(a.moment) - date(x.moment) < x.periodTime")
	public Double ratioPendingAppStatus();

	@Query(
		value = "SELECT (SELECT count(a.id) FROM `acme-handy-worker`.application a join `acme-handy-worker`.fix_up_task f on f.id = a.fix_up_task where a.status =1 and not a.moment between f.moment and (select DATE_ADD(moment, INTERVAL period_time DAY) FROM `acme-handy-worker`.fix_up_task where fix_up_task.id = f.id)) / (SELECT count(ap.id) FROM `acme-handy-worker`.application ap where ap.status=1) FROM `acme-handy-worker`.application x LIMIT 1",
		nativeQuery = true)
	public Double ratioPendingAppStatus2();

}
