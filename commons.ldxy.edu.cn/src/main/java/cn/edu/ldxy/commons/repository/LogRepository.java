/**
 *
 * 描述:
 *
 * @author: kooo
 * @date:
 */
package cn.edu.ldxy.commons.repository;



import cn.edu.ldxy.commons.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;


public interface LogRepository extends MongodbBaseRepository<Log, String> {

    Page<Log> findByTimeBetween(Date start, Date end, Pageable pageable);

    Page<Log> findByTimeAfter(Date time, Pageable pageable);

}
