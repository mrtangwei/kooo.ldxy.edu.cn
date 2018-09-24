/**
 * 
 */
package cn.edu.ldxy.commons.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author Kooo
 * 
 */
@NoRepositoryBean
public interface MongodbBaseRepository<T, ID extends Serializable>
        extends MongoRepository<T, ID> {



    Page<T> findAll(Pageable pageable);



}
