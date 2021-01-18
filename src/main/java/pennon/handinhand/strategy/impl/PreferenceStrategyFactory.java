package pennon.handinhand.strategy.impl;

import org.springframework.stereotype.Service;
import pennon.handinhand.exception.BusinessException;
import pennon.handinhand.strategy.PreferenceStrategy;
import pennon.handinhand.strategy.impl.preference.*;

public class PreferenceStrategyFactory {
    private PreferenceStrategy preferenceStrategy;

    public PreferenceStrategyFactory(int type) {
        switch (type) {
            case 1:
                this.preferenceStrategy = new Collect();
                break;
            case 2:
                this.preferenceStrategy = new Share();
                break;
            case 3:
                this.preferenceStrategy = new Comment();
                break;
            case 4:
                this.preferenceStrategy = new Stay();
                break;
            case 5:
                this.preferenceStrategy = new View();
                break;
            default:
                throw new BusinessException("无法使用工厂生成策略类");
        }
    }

    public PreferenceStrategy getStrategy() {
        return this.preferenceStrategy;
    }
}
