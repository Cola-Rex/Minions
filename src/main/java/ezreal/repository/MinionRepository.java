package ezreal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ezreal.entity.Minion;

public interface MinionRepository extends JpaRepository<Minion, Long> {

	public Minion findById(int id);
}
