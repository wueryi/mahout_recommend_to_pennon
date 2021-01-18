package pennon.handinhand.strategy.impl.preference;

import pennon.handinhand.bo.PreferenceDate;
import pennon.handinhand.component.SpringContextHolder;
import pennon.handinhand.service.PreferenceService;
import pennon.handinhand.strategy.PreferenceStrategy;

public class Share implements PreferenceStrategy {
    private PreferenceService preferenceService;

    public Share() {
        this.preferenceService = SpringContextHolder.getBean(PreferenceService.class);
    }

    //5*30%
    private final static float PREFERENCE_CHARGING = 1.5f;

    @Override
    public void operate(PreferenceDate preference) {
        int times = preference.getTimes();
        float preferenceChange = 0;
        if (times == 0) {
            preferenceChange = PREFERENCE_CHARGING/3;
        } else if(times==2){
            preferenceChange = PREFERENCE_CHARGING/3;
        } else if (times==5){
            preferenceChange = PREFERENCE_CHARGING/3;
        }
        if (preferenceChange != 0) {
            this.preferenceService.update(preference.getUserId(), preference.getItemId(), preferenceChange, "转发累计"+(times+1)+"次-相关度+" + preferenceChange);
        }
    }
}
