package pennon.handinhand.strategy.impl.preference;

import pennon.handinhand.bo.PreferenceDate;
import pennon.handinhand.component.SpringContextHolder;
import pennon.handinhand.service.PreferenceService;
import pennon.handinhand.strategy.PreferenceStrategy;

public class View implements PreferenceStrategy {
    private PreferenceService preferenceService;

    public View() {
        this.preferenceService = SpringContextHolder.getBean(PreferenceService.class);
    }

    //5*10%
    private final static float PREFERENCE_CHARGING = 0.5f;

    @Override
    public void operate(PreferenceDate preference) {
        this.preferenceService.update(preference.getUserId(), preference.getItemId(), PREFERENCE_CHARGING, "查看-相关度+" + PREFERENCE_CHARGING);
    }
}
