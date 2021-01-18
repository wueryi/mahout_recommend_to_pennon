package pennon.handinhand.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pennon.handinhand.bo.PreferenceDate;
import pennon.handinhand.dao.PreferenceLogMapper;
import pennon.handinhand.dao.PreferenceMapper;
import pennon.handinhand.entity.Preference;
import pennon.handinhand.entity.PreferenceExample;
import pennon.handinhand.entity.PreferenceLog;
import pennon.handinhand.exception.BusinessException;
import pennon.handinhand.service.PreferenceService;
import pennon.handinhand.strategy.impl.PreferenceStrategyContext;
import pennon.handinhand.strategy.impl.PreferenceStrategyFactory;
import pennon.handinhand.util.DateUtil;

import java.util.List;

@Service("PreferenceService")
public class PreferenceServiceImpl implements PreferenceService {
    @Autowired
    private PreferenceMapper preferenceMapper;

    @Autowired
    private PreferenceLogMapper preferenceLogMapper;

    @Override
    public void compute(int type, PreferenceDate preference) {
        new PreferenceStrategyContext(new PreferenceStrategyFactory(type).getStrategy()).doOperate(preference);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, BusinessException.class})
    public void update(int userId, int itemId, float preference, String reason) {
        PreferenceExample preferenceExample = new PreferenceExample();
        PreferenceExample.Criteria criteria = preferenceExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andItemIdEqualTo(itemId);
        RowBounds rowBounds = new RowBounds(0, 1);
        List<Preference> preferenceList = this.preferenceMapper.selectByExampleWithRowbounds(preferenceExample, rowBounds);
        if (preferenceList.isEmpty()) {
            Preference preferenceInsert = new Preference();
            preferenceInsert.setUserId(userId);
            preferenceInsert.setItemId(itemId);
            preferenceInsert.setPreference(preference);
            preferenceInsert.setIsUpdated(0);
            preferenceInsert.setCreatedAt(DateUtil.currentTimestamp());
            preferenceInsert.setUpdatedAt(DateUtil.currentTimestamp());
            if (1 != this.preferenceMapper.insertSelective(preferenceInsert)) {
                throw new BusinessException("新增评分失败");
            }
        } else {
            Preference preferenceUpdate = preferenceList.get(0);
            preferenceUpdate.setIsUpdated(0);
            //控制preference最大值为5
            float max = preferenceUpdate.getPreference() + preference;
            if (max >= 5) {
                max = 5.0f;
                reason += "-达到相关度最大值5.0";
            }
            preferenceUpdate.setPreference(max);
            preferenceUpdate.setUpdatedAt(DateUtil.currentTimestamp());
            if (1 != this.preferenceMapper.updateByPrimaryKeySelective(preferenceUpdate)) {
                throw new BusinessException("更新评分失败");
            }
        }

        //记录日志
        PreferenceLog preferenceLog = new PreferenceLog();
        preferenceLog.setChangeType((byte) 1);
        preferenceLog.setCreatedAt(DateUtil.currentTimestamp());
        preferenceLog.setItemId(itemId);
        preferenceLog.setUserId(userId);
        preferenceLog.setReason(reason);
        preferenceLog.setPreferenceChange(preference);
        if (1 != this.preferenceLogMapper.insertSelective(preferenceLog)) {
            throw new BusinessException("新增日志失败");
        }
    }
}
