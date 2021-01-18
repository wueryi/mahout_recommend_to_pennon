package pennon.handinhand.strategy.impl.preference;

import pennon.handinhand.bo.PreferenceDate;
import pennon.handinhand.component.SpringContextHolder;
import pennon.handinhand.service.PreferenceService;
import pennon.handinhand.strategy.PreferenceStrategy;

public class Collect implements PreferenceStrategy {
    private PreferenceService preferenceService;

    public Collect() {
        this.preferenceService = SpringContextHolder.getBean(PreferenceService.class);
    }

    //5*30%
    private final static float PREFERENCE_CHARGING = 1.5f;

    @Override
    public void operate(PreferenceDate preference) {
        this.preferenceService.update(preference.getUserId(), preference.getItemId(), PREFERENCE_CHARGING, "收藏-相关度+" + PREFERENCE_CHARGING);
    }
}
