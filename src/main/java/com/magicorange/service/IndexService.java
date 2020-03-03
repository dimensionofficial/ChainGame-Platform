package com.magicorange.service;

import com.magicorange.dao.IndexDao;
import com.magicorange.pojo.CallBack;
import com.magicorange.pojo.OrderinfoQueryResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassNameIndexService
 * @Description
 * @Author
 * @Date2019/11/28 18:51
 **/
@Service
public class IndexService {

    @Resource
    private IndexDao indexDao;

    /**
     * 插入订单接口
     * @param list
     * @param listTwo
     * @return
     */
    @Transactional
    public Integer insert(List<String> list, List<String> listTwo){
        Integer one=indexDao.insertInfo(list);
        System.out.println("orderinfo插入为"+one);
        Integer two=indexDao.insertDown(listTwo);
        System.out.println("orderinfo插入为"+two);
        return one+two;
    }

    /**
     * 第二个接口更改支付状态
     * @param cporderid
     * @param tx_id
     * @return
     */
    public Integer update(String cporderid,String tx_id,String fromAcc){
        Integer one=indexDao.updateState(cporderid,tx_id,fromAcc);
        return one;
    }

    /**
     * 第三个参数查询
     * @param cporderid
     * @return
     */
    public OrderinfoQueryResult orderinfoqueryresult(String cporderid){
        OrderinfoQueryResult orderinfoQueryResult=indexDao.orderinfoQueryResult(cporderid);

        if (orderinfoQueryResult==null){
            return null;
        }else {
            return orderinfoQueryResult;
        }
    }

    /**
     * 查询数据返回给callback接口
     * @param cporderid 通过接口查询到的tx_id获得的cporderid
     * @return
     */
    public CallBack callBack(String cporderid){
        return indexDao.callBack(cporderid);
    }
    /**
     * 如果值为空则默认传0
     * @param list 参数列表
     * @return
     */
    public List<String> autoMaticZero(List<String> list){
        for (int i=0;i<list.size();i++){
            if (list.get(i)==null||list.get(i).equals("")){
                list.set(i,"0");
            }
        }
        return list;
    }

    /**
     * 如果必填字段为空则返回0
     * @param list 参数列表
     * @return
     */
    public Integer notNull(List<String> list){
        for (int i=0;i<list.size();i++){
            if (list.get(i)==null||list.get(i).equals("")){
                return 0;
            }
        }
        return 1;
    }
}
