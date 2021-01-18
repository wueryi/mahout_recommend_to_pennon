package pennon.handinhand.bo;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import pennon.handinhand.util.CommonUtil;

import java.io.File;

public class NewsRecommendSource {
    private final static NewsRecommendSource instance = new NewsRecommendSource();
    private static Recommender recommender;

    private NewsRecommendSource() {
        if (instance != null) {
            throw new RuntimeException("单例模式非法创建对象！");
        }
        try {
            FileDataModel dataModel = new FileDataModel(new File(CommonUtil.getBootPath() + "data/ratings.csv"));
            UserSimilarity similarity = new EuclideanDistanceSimilarity(dataModel);
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
            recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static NewsRecommendSource getInstance() {
        return instance;
    }

    public Recommender getRecommend() {
        return recommender;
    }

    public void refresh() {
        recommender.refresh(null);
    }
}
