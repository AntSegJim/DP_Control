
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends JpaRepository<MessageBox, Integer> {

	@Query("select mb from MessageBox mb where mb.actor.id=?1")
	public List<MessageBox> boxesActor(int actorId);

	@Query("select mb from MessageBox mb where mb.actor.id=?1 and mb.name='Trash box'")
	public MessageBox getTrashBox(int actorId);

	@Query("select mb from MessageBox mb where mb.actor.id=?1 and mb.name='In box'")
	public MessageBox getInBox(int actorId);

	@Query("select mb from MessageBox mb where mb.actor.id=?1 and mb.name='Out box'")
	public MessageBox getOutBox(int actorId);

	@Query("select mb from MessageBox mb where mb.actor.id=?1 and mb.name='Spam box'")
	public MessageBox getSpamBox(int actorId);
}
