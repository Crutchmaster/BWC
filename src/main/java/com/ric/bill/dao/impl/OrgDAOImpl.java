package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.OrgDAO;
import com.ric.bill.model.bs.Org;


/**
 * DAO сущности com.ric.bill.model.bs.Org
 * @author Lev
 * @version 1.00
 *
 */
@Repository("OrgDAO_BWC")
@Slf4j
public class OrgDAOImpl implements OrgDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
	/**
	 * Получить организацию по klsk
	 */
    @Override
	//@Cacheable(cacheNames="OrgDAOImpl.getByKlsk", key="{#klsk }")
	public Org getByKlsk(int klsk) {
		
		Query query =em.createQuery("from com.ric.bill.model.bs.Org t where t.klskId = :klsk");
		query.setParameter("klsk", klsk);
		try {
			return (Org) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Получить организацию по CD
	 */
    @Override
	public Org getByCD(String cd) {
		Query query =em.createQuery("from com.ric.bill.model.bs.Org t where t.cd = :cd");
		query.setParameter("cd", cd);
		try {
			return (Org) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Получить все организации по типу
	 * @param tp - 0 - все, 1 - УК
	 * @return
	 */
    @Override
	//@Cacheable(cacheNames="OrgDAOImpl.getOrgAll", key="{#tp }")
	public List<Org> getOrgAll(int tp) {
		Query query;
		if (tp==0) {
			// все орг
			query = em.createQuery("from com.ric.bill.model.bs.Org t order by nvl(t.isMnt,0) desc, t.name");
		} else {
			// УК
			query = em.createQuery("select t from com.ric.bill.model.bs.Org t join t.orgTp otp join otp.addrTp atp where atp.cd='ЖЭО' order by nvl(t.isMnt,0) desc, t.name");
		}
		return query.getResultList();
	}

}
