package pennon.handinhand.service;

import pennon.handinhand.bo.PreferenceDate;

public interface PreferenceService {
    void compute(int type, PreferenceDate preference);

    void update(int userId, int itemId, float preference, String reason);
}
