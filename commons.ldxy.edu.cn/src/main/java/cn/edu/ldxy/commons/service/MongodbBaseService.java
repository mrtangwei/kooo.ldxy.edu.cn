package cn.edu.ldxy.commons.service;


import cn.edu.ldxy.commons.repository.MongodbBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface MongodbBaseService<T, ID extends Serializable, JPA extends MongodbBaseRepository<T, ID>> {

	T save(T t);
	T update(T t);

	Page<T> findAll(Pageable pageable);
    List<T> findAll();


	T findById(ID id);
	
	void deleteById(ID id);


}
