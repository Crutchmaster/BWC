package com.ric.bill.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.dto.SumChrgRec;
import com.ric.bill.dto.SumDebRec;
import com.ric.bill.model.hotora.scott.Aflow;
import com.ric.bill.model.hotora.scott.AflowId;

/**
 * DAO сущности Aflow - начисления, долги (cтарая разработка)
 * @author Lev
 * @version 1.00
 *
 */
@Repository
public interface AflowDAO extends JpaRepository<Aflow, AflowId> {

	/**
	 * Получить сгруппированные записи начислений, связанных с услугой из ГИС ЖКХ по лиц.счету и периоду
	 * @param lsk - лицевой счет
	 * @param mg - период
	 * @return
	 */
	@Query(value = "select t2.id as \"ulistId\", sum(t2.summa) as \"summa\", sum(t2.vol) as \"vol\", sum(price) as \"price\" from ( "
			+ "select u.id, s.grp, sum(t.summa) as summa, sum(t.n1) as vol, min(t.n2) as price "
		+ "from scott.a_flow@hp t "
		+ "join exs.servgis s on t.fk_usl=s.fk_usl "
		+ "join exs.u_list u on s.fk_list=u.id " 
		+ "join exs.u_listtp tp on u.fk_listtp=tp.id "
		+ "where t.lsk = ?1 and t.mg = ?2 "
		+ "and NVL(tp.fk_eolink, ?3) = ?3 "
		+ "and t.fk_type = 0 "
		+ "group by u.id, s.grp) t2 "
		+ "group by t2.id", nativeQuery = true)
	List<SumChrgRec> getChrgGrp(String lsk, String period, Integer orgId);

	/**
	 * Получить долги и пеню по основным услугам
	 * @param lsk - лиц.счет
	 * @param dt - дата, на которую долг
	 * @return
	 */
	@Query(value = "select t.summa, t.summap, t.dopl from Aflow t "
			+ "where t.type = 61 and t.kart.lsk = ?1 and t.mg = TO_CHAR(?2,'YYYYMM') and t.dt1=?2")
	List<SumDebRec> getDeb(String lsk, Date dt);

	/**
	 * Получить долги и пеню по указанным, дополнительным услугам
	 * @param lsk - лиц.счет
	 * @param usl - код услуги, например 073 - капремонт
	 * @param dt - дата, на которую долг
	 * @return
	 */
	@Query(value = "select t.summa, t.summap, t.dopl from Aflow t "
			+ "where t.type = 1250 and t.kart.lsk = ?1 and t.mg = TO_CHAR(?3,'YYYYMM') and t.usl.id = ?2 and t.dt1=?3")
	List<SumDebRec> getDebByUsl(String lsk, String usl, Date dt);

	/**
	 * Получить совокупную пеню по основным услугам
	 * @param lsk - лиц.счет
	 * @param dt - дата, на которую пеня
	 * @return
	 */
	@Query(value = "select sum(t.summap) from Aflow t "
			+ "where t.type = 61 and t.kart.lsk = ?1 and t.mg = TO_CHAR(?2,'YYYYMM') and t.dt1=?2")
	BigDecimal getPenAmnt(String lsk, Date dt);
	
	/**
	 * Получить совокупную пеню по указанным, дополнительным услугам
	 * @param lsk - лиц.счет
	 * @param usl - код услуги, например 073 - капремонт
	 * @param dt - дата, на которую пеня
	 * @return
	 */
	@Query(value = "select sum(t.summap) from Aflow t "
			+ "where t.type = 1250 and t.kart.lsk = ?1 and t.mg = TO_CHAR(?3,'YYYYMM') and t.usl.id = ?2 and t.dt1=?3")
	BigDecimal getPenAmntByUsl(String lsk, String usl, Date dt);

}
