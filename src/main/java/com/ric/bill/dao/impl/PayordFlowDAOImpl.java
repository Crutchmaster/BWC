package com.ric.bill.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.PayordFlowDAO;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.PayordFlow;


@Repository
public class PayordFlowDAOImpl implements PayordFlowDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить движения по всем платежкам по типу и периоду (используется ли?)
     * @param uk - УК
     * @param tp - тип 
     * @param period - период
     * @return
     */
    public List<PayordFlow> getPayordFlowByTpPeriod(Integer tp, Org uk, String period) {
		Query query =em.createQuery("select t from PayordFlow t where "
				+ "t.period = :period and t.tp = :tp and t.uk.id = :id "
				+ "order by t.dt");
		query.setParameter("period", period);
		query.setParameter("tp", tp);
		query.setParameter("id", uk.getId());
		return query.getResultList();
	}

    /**
     * Получить движения по всем платежкам по типу и дате
     * @param tp - тип
     * @param dt1 - дата начала
     * @param dt2 - дата окончания
     * @param uk - УК
     * @return
     */
    public List<PayordFlow> getPayordFlowByTpDt(Integer tp, Date dt1, Date dt2, Integer uk) {
    	Query query = null;
    	if (dt1 != null && dt2 != null ) {
    		query =em.createQuery("select t from PayordFlow t where "
    				+ "t.dt between :dt1 and :dt2 and t.tp = :tp and "
    				+ "t.uk.id = decode(:uk, -1, t.uk.id, :uk)  " // NVL не получилось сделать - ORA-00932: inconsistent datatypes: expected BINARY got NUMBER
    				+ "order by t.id");
    		query.setParameter("dt1", dt1);
    		query.setParameter("dt2", dt2);
    		query.setParameter("tp", tp);
    		query.setParameter("uk", uk);
    	} else {
    		query =em.createQuery("select t from PayordFlow t where "
    				+ "t.tp = :tp and "
    				+ "t.uk.id = decode(:uk, -1, t.uk.id, :uk)  "
    				+ "order by t.id");
    		query.setParameter("tp", tp);
    		query.setParameter("uk", uk);
    	}
		return query.getResultList();
	}

    
    /**
     * Получить движение по платежке, до определенной даты
     * @param payordId - ID платежки
     * @param uk - УК
     * @param tp - тип
     * @param dt - дата
     */
    public List<PayordFlow> getPayordFlowBeforeDt(Integer payordId, Org uk, Integer tp, Date dt) {
		Query query =em.createQuery("select t from PayordFlow t join t.payord p where p.id = :payordId "
				+ "and t.dt <= :dt and t.tp = :tp and t.uk.id = :id "
				+ "order by t.dt desc");
		query.setParameter("payordId", payordId);
		query.setParameter("dt", dt);
		query.setParameter("tp", tp);
		query.setParameter("id", uk.getId());
		return query.getResultList();
	}

    /**
     * Получить движение по платежке, до определенного периода даты (напр.для вычисления сальдо)
     */
    public List<PayordFlow> getPayordFlowBeforePeriod(Integer payordId, Org uk, Integer tp, String period) {
		Query query =em.createQuery("select t from PayordFlow t join t.payord p where p.id = :payordId "
				+ "and t.period <= :period and t.tp = :tp and t.uk.id = :id "
				+ "order by t.period desc");
		query.setParameter("payordId", payordId);
		query.setParameter("period", period);
		query.setParameter("tp", tp);
		query.setParameter("id", uk.getId());
		return query.getResultList();
	}

    
}
