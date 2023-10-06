package org.sid.dao;

import org.sid.entities.Arrondissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface ArrondissementRepository extends JpaRepository<Arrondissement, Long> {

	public Page<Arrondissement> findByNameContains(String mc, Pageable pageable);
}
