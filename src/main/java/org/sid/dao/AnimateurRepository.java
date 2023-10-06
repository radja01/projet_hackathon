package org.sid.dao;


import org.sid.entities.Parc;
import org.sid.entities.Animateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface AnimateurRepository extends JpaRepository<Animateur, Long> {
	public Page<Animateur> findByTitreContains(String mc, Pageable pageable);

}
