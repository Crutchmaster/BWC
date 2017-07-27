package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.TaskDAO;
import com.ric.bill.model.exs.Task;



@Slf4j
@Repository
public class TaskDAOImpl implements TaskDAO {

	@PersistenceContext
    private EntityManager em;
    
    //конструктор
    public TaskDAOImpl() {
    	
    }
    
    /**
     * Вернуть список необработанных заданий 
     */
    public List<Task> getAllUnprocessed() {
			Query query =em.createQuery("select t from Task t left join t.depTask d where t.state in ('INS','ACK') and t.parentTask is null "
					+ "and (t.depTask is null or t.depTask.state in ('ACP')) order by t.id");
			return query.getResultList();
	}
    
    /**
     * Вернуть список дочерних заданий по родительскому заданию, по определённому типу объектов
     * @param task - родительское задание
     * @param addrTp - тип объекта
     * @param addrTp - уточняющий тип объекта
     */
    public List<Task> getByTaskAddrTp(Task task, String addrTp, String addrTpx) {
    	Query query = null;
    	if (task.getAppTp()==0) {
    		// "Квартплата"
    		if (addrTpx != null) {
    			// заполнен уточняющий тип
    			if (addrTp.equals("Документ")) {
        			query = em.createQuery("from Task t where t.parentTask.id = :parentId and t.eolink.objTp.cd = :addrTp and t.eolink.objTpx.cd = :addrTpx");
        			query.setParameter("parentId", task.getId());
        			query.setParameter("addrTp", addrTp);
        			query.setParameter("addrTpx", addrTpx);
    			} else {
    				// TODO: Прочие реализации 
    			}
    		} else {
    			// не заполнен уточняющий тип
    			query =em.createQuery("from Task t where t.parentTask.id = :parentId and t.eolink.objTp.cd = :addrTp");
    			query.setParameter("parentId", task.getId());
    			query.setParameter("addrTp", addrTp);
    		}
    	} else {
    		// Новая разработка
    		if (addrTpx != null) {
    			// заполнен уточняющий тип
    			if (addrTp.equals("Документ")) {
    				// TODO: Не проверял запрос! возможно не будет работать, доделать его!
        			query =em.createQuery("from Task t where t.parentTask.id = :parentId and t.eolink.ko.addrTp.cd = :addrTp and t.eolink.ko.doc.tp = :addrTpx");
        			query.setParameter("parentId", task.getParentTask().getId());
        			query.setParameter("addrTp", addrTp);
        			query.setParameter("addrTpx", addrTpx);
    			} else {
    				// TODO: Прочие реализации 
    			}
    			
    		} else {
    			// не заполнен уточняющий тип
    			query =em.createQuery("from Task t where t.parentTask.id = :parentId and t.eolink.ko.addrTp.cd = :addrTp");
    			query.setParameter("parentId", task.getParentTask().getId());
    			query.setParameter("addrTp", addrTp);
    		}
    	}
    	

			List<Task> lst;
			try {
				lst = query.getResultList();
			} catch (org.springframework.dao.EmptyResultDataAccessException e) {
				// не найден результат
				return null;
			}
			
			return lst;
	}

	/**
	 * Вернуть задание по ID родительского задания и транспортному GUID
	 * @param - task - родительское задание
	 * @param - tguid - транспортный GUID
	 */
	public Task getByTguid(Task task, String tguid) {
		Query query =em.createQuery("from Task t where (t.parentTask.id = :parentId or t.id = :parentId) and t.tguid = :tguid");
		query.setParameter("parentId", task.getId());
		query.setParameter("tguid", tguid);
		
		try {
			return (Task) query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			// не найден результат
			log.error("Не найдено задание по TGUID={}", tguid);
			return null;
		}
	}
    
	/**
	 * Вернуть наличие ошибки в любом дочернем задании
	 * @param task - родительское задание
	 * @return - наличие ошибки
	 */
	public Boolean getChildAnyErr(Task task) {
		Query query =em.createQuery("from Task t where t.parentTask.id = :parentId and t.state = 'ERR' ");
		query.setParameter("parentId", task.getId());
		List<Task> lst;
		try {
			lst = query.getResultList();
			return true;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			// не найден результат
			return false;
		}
	}
}