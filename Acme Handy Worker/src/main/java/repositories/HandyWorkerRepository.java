
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select e from HandyWorker e where ((select sum(a.application.size)*1.0 /(select count(h) from HandyWorker h) from HandyWorker a)) * 0.1 + ((select sum(a.application.size)*1.0/(select count(h) from HandyWorker h) from HandyWorker a)) < e.application.size order by e.application.size ASC")
	public Collection<HandyWorker> handyWorkerMoreTentPercentApplicatonsAccepted();

	@Query("select c from HandyWorker c where c.userAccount.id = ?1")
	public HandyWorker handyWorkerUserAccount(Integer id);

	@Query("select a.handyWorker from Application a join a.fixUpTask f where f.customer.id = ?1")
	public Collection<HandyWorker> getHandyWorkerInvolveInAnyOfHisFixUpTask(int customerId);

	@Query("select h.id from HandyWorker h join h.application ha where ha.status = 2 and (select count(a) from Application a where a.status=2 and h.id = a.handyWorker) * 1.1 > (select count(a)*1.0 / (select count(h) from HandyWorker h) from Application a where a.status = 2) order by h.application.size DESC")
	public Collection<Integer> handyWorkerMoreTentPercentApplicatonsAccepted2();

	@Query("select h.name from HandyWorker h join h.application ha where ha.status = 2 and (select count(a) from Application a where a.status=2 and h.id = a.handyWorker) * 1.1 > (select count(a)*1.0 / (select count(h) from HandyWorker h) from Application a where a.status = 2) order by h.application.size DESC")
	public Collection<String> handyWorkerMoreTentPercentApplicatonsAccepted3();
}
