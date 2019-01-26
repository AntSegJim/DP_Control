
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f where f.customer.id = ?1")
	public Collection<FixUpTask> fixUpTasksCustomer(Integer id);

	@Query("select f from FixUpTask f join f.application a where a.handyWorker.id=?1")
	public Collection<FixUpTask> findAllfixUpTasksHandyWorkerId(Integer id);

	@Query("select f.fixUpTask from Finder f where f.id = ?1")
	public Collection<FixUpTask> fixUpTasksByFinder(Integer finderId);

	@Query("select count(f.customer) from FixUpTask f group by f.customer.id order by count(f.customer) ASC")
	public Collection<Integer> maxMinAvgDevFixUpTask();

	@Query("select f.ticker from FixUpTask f")
	public Collection<String> allTickerInFixUpTask();

	@Query("select f from FixUpTask f where (locate(?1,f.ticker) != 0 or locate(?2,f.description) != 0 or locate(?3,f.address) != 0) and f.moment between ?4 and ?5 and f.maximunPrice between ?6 and ?7 and locate(?8,f.category.name) != 0 and locate(?9,f.warranty.title) != 0")
	public Collection<FixUpTask> filterFixUpTask(String ticker, String description, String address, Date fi, Date ff, Double lp, Double hp, String c, String w);

	@Query(
		value = "select * from Fix_up_task f where (locate(?1,f.ticker) != 0 or locate(?2,f.description) != 0 or locate(?3,f.address) != 0) and f.moment between ?4 and ?5 and f.maximun_price between ?6 and ?7 and locate(?8,f.category) != 0 and locate(?9,f.warranty) != 0 LIMIT ?10",
		nativeQuery = true)
	public Collection<FixUpTask> filterFixUpTask2(String ticker, String description, String address, Date fi, Date ff, Double lp, Double hp, String c, String w, Integer limite);

	@Query("select max(a.application.size), min(a.application.size), avg(a.application.size),sqrt(sum(a.application.size * a.application.size) / count(a.application.size) - (avg(a.application.size) * avg(a.application.size))) from FixUpTask a")
	public List<Object[]> getMaxMinAvgDesvFixUpApp();

	@Query("select avg(1.0*(select count(f.customer) from FixUpTask f where f.customer.id=c.id)),  min(1.0*(select count(f.customer) from FixUpTask f where f.customer.id=c.id)),  max(1.0*(select count(f.customer) from FixUpTask f where f.customer.id=c.id)),  sqrt(1.0*sum(1.0*(select count(f.customer) from FixUpTask f where f.customer.id=c.id) *  (select count(f.customer) from FixUpTask f where f.customer.id=c.id)) / count(c) - avg(1.0*(select count(f.customer) from FixUpTask f where f.customer.id=c.id))*  avg(1.0*(select count(f.customer) from FixUpTask f where f.customer.id=c.id))) from Customer c")
	public List<Object[]> getMaxMinAvgDesvFixUp();

	@Query(" select max(a.maximunPrice), min(a.maximunPrice),  avg(a.maximunPrice) , sqrt(sum(a.maximunPrice * a.maximunPrice) / count(a.maximunPrice) - (avg(a.maximunPrice) * avg(a.maximunPrice))) from FixUpTask a")
	public List<Object[]> getMaxMinAvgDesvFixUpPrice();

	@Query("select avg(1.0*(select count(c.fixUpTask) from Complaint c where c.fixUpTask.id=f.id)), min(1.0*(select count(c.fixUpTask) from Complaint c where c.fixUpTask.id=f.id)),max(1.0*(select count(c.fixUpTask) from Complaint c where c.fixUpTask.id=f.id)), sqrt(1.0*sum(1.0*(select count(c.fixUpTask) from Complaint c where c.fixUpTask.id=f.id) *	 (select count(c.fixUpTask) from Complaint c where c.fixUpTask.id=f.id)) /	 count(f) - avg(1.0*(select count(c.fixUpTask) from Complaint c where c.fixUpTask.id=f.id))*	 avg(1.0*(select count(c.fixUpTask) from Complaint c where c.fixUpTask.id=f.id))) from FixUpTask f")
	public List<Object[]> getMaxMinAvgDesvFixUpComplaint();

	@Query(value = "select date_add(moment, interval period_time day) from Fix_up_task where Fix_up_task.id=?1", nativeQuery = true)
	public Date dateExpiracion(Integer FixId);

	@Query("select f from FixUpTask f where f.category.name=?1")
	public List<FixUpTask> getFixUpByCategory(String name);

}
