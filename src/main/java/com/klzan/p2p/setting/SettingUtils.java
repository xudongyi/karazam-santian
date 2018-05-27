package com.klzan.p2p.setting;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.ObjectUtils;
import com.klzan.core.util.PropertiesUtils;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.service.options.OptionsService;
import com.klzan.p2p.vo.options.OptionsVo;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by suhao Date: 2016/11/30 Time: 9:48
 *
 * @version: 1.0
 */
@Component
public class SettingUtils implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(SettingUtils.class);

    @Inject
    private OptionsService optionsService;

    @Inject
    private Environment env;

    /**
     * 基本设置
     * @return
     */
    public BasicSetting getBasic() {
        return getSetting(OptionsType.BASIC_SETTING);
    }

    /**
     * 推荐设置
     * @return
     */
    public ReferralSetting getReferral() {
        return getSetting(OptionsType.REFERRAL_SETTING);
    }

    /**
     * 提现设置
     * @return
     */
    public WithdrawSetting getWithdrawSetting() {
        return getSettingAsJson(OptionsType.WITHDRAW_SETTING);
    }

    /**
     * 充值设置
     * @return
     */
    public RechargeSetting getRechargeSetting() {
        return getSettingAsJson(OptionsType.RECHARGE_SETTING);
    }

    /**
     * 债权转让设置
     * @return
     */
    public TransferSetting getTransferSetting() {
        return getSetting(OptionsType.TRANSFER_SETTING);
    }

    /**
     * 优惠券设置
     * @return
     */
    public CouponSetting getCouponSetting() {
        return getSetting(OptionsType.COUPON_SETTING);
    }

    /**
     * 还款通知设置
     * @return
     */
    public RepaymentNoticeSetting getRepaymentNoticeSetting() {
        return getSetting(OptionsType.POSTLOAN_SETTING);
    }

    /**
     * 积分设置
     * @return
     */
    public PointSetting getPointSetting() {
        return getSetting(OptionsType.POINT_SETTING);
    }

    /**
     * 自动投标设置
     * @return
     */
    public AutoInvestmentSetting getAutoInvestmentSetting() {
        return getSetting(OptionsType.AUTO_INVESTMENT_SETTING);
    }

    /**
     * 文章内容设置
     * @return
     */
    public PostsSetting getPostsSetting() {
        PostsSetting postsSetting = new PostsSetting();
        postsSetting.setContentUrlPrefix(PropertiesUtils.getString("web.url.content"));
        postsSetting.setTaxonomyUrlPrefix(PropertiesUtils.getString("web.url.taxonomy"));
        return postsSetting;
    }

    /**
     * 文件服务地址
     * @return
     */
    public String getDfsUrl() {
        try {
            return PropertiesUtils.getString("url.file_server");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取现在时间
     * @return
     */
    public Date getNow() {
        return new Date();
    }

    private <M> M getSetting(OptionsType optionsType) {
        Class setting = optionsType.getSetting();
        Object obj = ObjectUtils.newInstance(setting.getPackage().getName() + "." + setting.getSimpleName());
        try {
            List<OptionsVo> options = optionsService.findOptionsByType(optionsType);
            for (OptionsVo vo : options) {
                BeanUtils.copyProperty(obj, vo.getKeyName(), vo.getKeyValue());
//                if (vo.getDataType() == OptionsDataType.BOOLEAN) {
//                    ObjectUtils.setField(obj, vo.getKeyName(), Boolean.parseBoolean(vo.getKeyValue()));
//                } else if (vo.getDataType() == OptionsDataType.DATE) {
//                    ObjectUtils.setField(obj, vo.getKeyName(), vo.getKeyValue());
//                } else if (vo.getDataType() == OptionsDataType.DOUBLE) {
//                    ObjectUtils.setField(obj, vo.getKeyName(), new BigDecimal(vo.getKeyValue()));
//                } else if (vo.getDataType() == OptionsDataType.INTEGER) {
//                    ObjectUtils.setField(obj, vo.getKeyName(), Integer.parseInt(vo.getKeyValue()));
//                } else {
//                    ObjectUtils.setField(obj, vo.getKeyName(), vo.getKeyValue());
//                }
            }
        } catch (Exception e) {
            logger.error("获取{}异常", optionsType.getDisplayName());
        }
        return (M) obj;
    }

    private <M> M getSettingAsJson(OptionsType optionsType) {
        Class setting = optionsType.getSetting();
        Object obj = ObjectUtils.newInstance(setting.getPackage().getName() + "." + setting.getSimpleName());
        try {
            List<OptionsVo> options = optionsService.findOptionsByType(optionsType);
            if (!options.isEmpty()) {
                OptionsVo optionsVo = options.get(0);
                obj = JsonUtils.toObject(optionsVo.getKeyValue(), setting);
            } else {
                throw new BusinessProcessException("获取{}异常", optionsType.getDisplayName());
            }
        } catch (Exception e) {
            logger.error("获取{}异常", optionsType.getDisplayName());
        }
        return (M) obj;
    }
}
