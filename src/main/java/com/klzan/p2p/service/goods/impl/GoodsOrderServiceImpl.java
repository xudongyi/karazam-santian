package com.klzan.p2p.service.goods.impl;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.SnUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.goods.GoodsOrderDao;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsShippingStatus;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.goods.GoodsOrderService;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.service.user.UserPointService;
import com.klzan.p2p.service.user.UserShippingAddressService;
import com.klzan.p2p.vo.goods.GoodsExchangeVo;
import com.klzan.p2p.vo.goods.GoodsOrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品订单
 * @author: chenxinglin
 */
@Service
@Transactional
public class GoodsOrderServiceImpl extends BaseService<GoodsOrder> implements GoodsOrderService {

    @Inject
    private GoodsOrderDao goodsOrderDao;
    @Inject
    private GoodsService goodsService;
    @Inject
    private UserShippingAddressService addressService;
    @Inject
    private UserPointService userPointService;

    @Override
    public PageResult<GoodsOrder> findPage(PageCriteria criteria) {
        return goodsOrderDao.findPage(criteria);
    }

    @Override
    public PageResult<GoodsOrder> findPage(PageCriteria criteria, Integer userId, GoodsType type, GoodsOrderStatus status, Integer month){
        return goodsOrderDao.findPage(criteria, userId, type, status, month);
    }

    @Override
    public List<GoodsOrder> findListByUserId(Integer userId) {
        return goodsOrderDao.findListByUserId(userId);
    }

    @Override
    public List<GoodsOrder> findListByGoods(Integer goodsId) {
        return goodsOrderDao.findListByGoods(goodsId);
    }

    @Override
    public synchronized GoodsOrder submit(User currentUser, GoodsExchangeVo vo) {

        Goods goods = goodsService.get(vo.getGoodsId());
        UserShippingAddress address = addressService.get(vo.getAddress());

        //新增商品订单
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUserId(currentUser.getId());
        goodsOrder.setOrderNo(SnUtils.getOrderNo());
        goodsOrder.setStatus(GoodsOrderStatus.paid);
        goodsOrder.setOperator(currentUser.getLoginName());
        goodsOrder.setGoods(vo.getGoodsId());
        goodsOrder.setQuantity(vo.getQuantity());
        goodsOrder.setPoint(vo.getPoint());
        goodsOrder.setDiscountPoint(0);
        goodsOrder.setConfirmDate(new Date());
        goodsOrder.setMemo("商品兑换");
        goodsOrder.setAdrConsignee(address.getConsignee());
        goodsOrder.setAdrArea(address.getArea());
        goodsOrder.setAdrAddress(address.getAddress());
        goodsOrder.setAdrZipCode(address.getZipCode());
        goodsOrder.setAdrMobile(address.getMobile());
        goodsOrder.setAdrTelephone(address.getTelephone());
        goodsOrder.setAdrMemo(address.getMemo());
        goodsOrder.setLogisticsStatus(GoodsShippingStatus.unshipped);
        goodsOrder = goodsOrderDao.persist(goodsOrder);

        //更新商品库存
        if(goods.getStock()<vo.getQuantity()){
            throw new RuntimeException("库存不足");
        }
        goods.subtractStock(vo.getQuantity());
        goodsService.merge(goods);

        //用户积分更新
        userPointService.exchange(currentUser.getId(), vo.getPoint());

        return goodsOrder;
    }

//    @Override
//    public GoodsOrder confirm(Integer goodsOrderId) {
//        GoodsOrder goodsOrder = goodsOrderDao.get(goodsOrderId);
//
//        goodsOrderDao.merge(goodsOrder);
//        return goodsOrder;
//    }

    @Override
    public synchronized GoodsOrder cancel(GoodsOrder goodsOrder) {
        if(goodsOrder.getLogisticsStatus().equals(GoodsShippingStatus.shipped)){
            throw new RuntimeException("订单已发货");
        }
        goodsOrder.setStatus(GoodsOrderStatus.cancel);
        goodsOrder = goodsOrderDao.merge(goodsOrder);

        //用户积分更新
        userPointService.exchangeCancel(goodsOrder);
        return goodsOrder;
    }

    @Override
    public GoodsOrder sendOut(GoodsOrder goodsOrder, String logisticsCorp, String logisticsCorpUrl, String logisticsNo) {
        goodsOrder.setStatus(GoodsOrderStatus.send);
        goodsOrder.setLogisticsStatus(GoodsShippingStatus.shipped);
        goodsOrder.setLogisticsCorp(logisticsCorp);
        goodsOrder.setLogisticsCorpUrl(logisticsCorpUrl);
        goodsOrder.setLogisticsNo(logisticsNo);
        goodsOrder.setSendDate(new Date());
        goodsOrder = goodsOrderDao.merge(goodsOrder);
        return goodsOrder;
    }

    @Override
    public GoodsOrder receive(GoodsOrder goodsOrder) {
        goodsOrder.setStatus(GoodsOrderStatus.complet);
        goodsOrder.setLogisticsStatus(GoodsShippingStatus.take_over);
        goodsOrder.setReceiveDate(new Date());
        goodsOrder = goodsOrderDao.merge(goodsOrder);
        return goodsOrder;
    }

    @Override
    public GoodsOrder update(GoodsOrderVo vo) {
        return null;
    }

}
