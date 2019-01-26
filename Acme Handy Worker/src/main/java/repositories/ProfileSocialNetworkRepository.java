
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfileSocialNetwork;

@Repository
public interface ProfileSocialNetworkRepository extends JpaRepository<ProfileSocialNetwork, Integer> {

	@Query("select p from ProfileSocialNetwork p where p.actor.id = ?1")
	public Collection<ProfileSocialNetwork> profilesByActor(int id);

	@Query("select count(p) from ProfileSocialNetwork p where p.actor.id = ?1")
	public Integer getNumberProfile(int id);
}
