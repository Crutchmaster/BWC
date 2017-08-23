package com.ric.bill.mm.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.EolinkDAO;
import com.ric.bill.dao.EolinkToEolinkDAO;
import com.ric.bill.mm.EolinkMng;
import com.ric.bill.model.exs.Eolink;


@Service
@Slf4j
public class EolinkMngImpl implements EolinkMng {

	@Autowired
	private EolinkDAO eolinkDao;
	@Autowired
	private EolinkToEolinkDAO eolinkToEolinkDao;

    /**
     * Получить Внешний объект по GUID
     * @param guid - GUID
     * @return
     */
    public Eolink getEolinkByGuid(String guid) {
    	
    	return eolinkDao.getEolinkByGuid(guid);
    	
    }
    
    /**
     * Получить Внешние объекты, связанные с данным
     * @param eolink - Внешний объект
     * @return
     */
    public List<Eolink> getLinkedEolink(Eolink eolink) {
    	
    	return eolinkToEolinkDao.getLinkedEolink(eolink);
    	
    }

    /**
     * Получить Внешний объект по reu,kul,nd
     * @param reu - REU из Квартплаты
     * @param kul - KUL из Квартплаты
     * @param nd -  ND из Квартплаты
     * @param kw -  KW из Квартплаты
     * @param entry -  ENTRY из Квартплаты
     * @param tp -  тип объекта
     */
	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd, 
			String kw, String entry, String tp) {
		return eolinkDao.getEolinkByReuKulNdTp(reu, kul, nd, kw, entry, tp);
	}
	
}