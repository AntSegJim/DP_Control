
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select ss from Sponsorship ss where ss.sponsor.id = ?1")
	public Collection<Sponsorship> allMySponsorships(Integer sponsorId);
}
