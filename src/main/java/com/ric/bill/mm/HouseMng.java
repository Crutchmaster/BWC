package com.ric.bill.mm;

import java.util.Date;
import java.util.List;

import com.ric.bill.model.ar.House;
import com.ric.bill.model.bs.Org;

public interface HouseMng {
	
	public List<House> findAll();
	public List<House> findAll2(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2);
	public Org getUkByDt(House house , Date dt);
	
}
