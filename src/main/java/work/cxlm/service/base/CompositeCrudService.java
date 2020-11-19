package work.cxlm.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 包含联合主键实体类的 CRUD
 * created 2020/11/18 13:03
 *
 * @author Chiru
 */
public interface CompositeCrudService<DOMAIN, ID> extends CrudService<DOMAIN, ID> {

    Page<DOMAIN> pageAllById(ID id, Pageable pageable);
}
