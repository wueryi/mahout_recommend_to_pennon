package pennon.handinhand.strategy.impl;

import pennon.handinhand.bo.PreferenceDate;
import pennon.handinhand.strategy.PreferenceStrategy;

public class PreferenceStrategyContext {
    private PreferenceStrategy preferenceStrategy;

    public PreferenceStrategyContext(PreferenceStrategy preferenceStrategy) {
        this.preferenceStrategy = preferenceStrategy;
    }

    public void doOperate(PreferenceDate preference) {
        this.preferenceStrategy.operate(preference);
    }
}
