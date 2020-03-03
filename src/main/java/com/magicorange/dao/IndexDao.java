package com.magicorange.dao;

import com.magicorange.pojo.CallBack;
import com.magicorange.pojo.OrderinfoQueryResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameIndexDao
 * @Description
 * @Author
 * @Date2019/11/28 18:50
 **/
@Mapper
public interface IndexDao {
    Integer insertInfo(List<String> list);
    Integer insertDown(List<String> listTwo);
    Integer updateState(@Param("cporderid") String cporderid,@Param("tx_id") String tx_id,@Param("fromAcc") String fromAcc);
    OrderinfoQueryResult orderinfoQueryResult(String cporderid);
    CallBack callBack(String cporderid);
    Integer updateCallBack(String cporderid);
}
