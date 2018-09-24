/**
 * 
 */
package cn.edu.ldxy.commons.service.impl;


import cn.edu.ldxy.commons.repository.MongodbBaseRepository;
import cn.edu.ldxy.commons.service.MongodbBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;
import java.util.List;


/**
 * @author Kooo
 * 
 */
public class MongodbBaseServiceImpl<T, ID extends Serializable, JPA extends MongodbBaseRepository<T, ID>>
		implements MongodbBaseService<T, ID, JPA> {

	@Autowired
	private JPA jpa;

	@Autowired
	private MongoTemplate mongoTemplate;



	public T save(T t) {
		return this.jpa.save(t);
	}


	public T update(T t) {
		return this.jpa.save(t);
	}

	public List<T> findAll() {
		return (List)this.jpa.findAll();
	}

    public Page<T> findAll(Pageable pageable) {
        return this.jpa.findAll(pageable);
    }

	

	public T findById(ID id) {

		return this.jpa.findById(id).get();
	}

	public void deleteById(ID id) {
		 this.jpa.deleteById(id);
	}






}
