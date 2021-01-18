package pennon.handinhand.strategy.impl.preference;

import pennon.handinhand.bo.PreferenceDate;
import pennon.handinhand.component.SpringContextHolder;
import pennon.handinhand.service.PreferenceService;
import pennon.handinhand.strategy.PreferenceStrategy;

public class Stay implements PreferenceStrategy {
    private PreferenceService preferenceService;

    public Stay() {
        this.preferenceService = SpringContextHolder.getBean(PreferenceService.class);
    }

    //5*10%
    private final static float PREFERENCE_CHARGING = 0.5f;

    @Override
    public void operate(PreferenceDate preference) {
        int times = preference.getTimes();
        if (times == 10 ) {
            this.preferenceService.update(preference.getUserId(), preference.getItemId(), PREFERENCE_CHARGING, "页面停留时间累计超过"+times+"秒-相关度+" + PREFERENCE_CHARGING);
        }

    }
}
