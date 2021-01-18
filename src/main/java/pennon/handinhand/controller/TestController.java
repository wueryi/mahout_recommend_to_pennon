package pennon.handinhand.controller;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;
import org.apache.mahout.common.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pennon.handinhand.service.PreferenceService;
import pennon.handinhand.util.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author jishuai
 */
@RestController
@RequestMapping(value = "test")
public class TestController {
//    @Autowired
//    private DruidDataSource druidDataSource;

    @Autowired
    private PreferenceService preferenceService;

    private DataModel dataModel;

//    TestController() throws IOException, TasteException {
//        File file = new File(CommonUtil.getBootPath()+"data/ratings.csv");
//            System.out.println();

//            System.out.println(dataSource);
//            JDBCDataModel jdbcDataModel = new MySQLJDBCDataModel(druidDataSource,"ratings1", "user_id", "item_id","preference","created_at");
//            ReloadFromJDBCDataModel dataModel = new ReloadFromJDBCDataModel(jdbcDataModel);
            //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
//        this.dataModel = new FileDataModel(file);
//    }

    @RequestMapping(value = "test6", method = RequestMethod.GET)
    public List<RecommendedItem> test6(@RequestParam String s, String m, String u) {
        try {
            //准备数据 这里是电影评分数据
//            JDBCDataModel jdbcDataModel = new MySQLJDBCDataModel(druidDataSource,"ratings1", "user_id", "item_id","preference","created_at");
//            ReloadFromJDBCDataModel dataModel = new ReloadFromJDBCDataModel(jdbcDataModel);

            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(1000, similarity, dataModel);
            Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
            if (s.equals("1")) {
                recommender.refresh(null);
            }

            //给用户ID等于5的用户推荐10部电影
            List<RecommendedItem> recommendedItemList = recommender.recommend(Integer.parseInt(u), Integer.parseInt(m));
//            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
//            List<RecommendedItem> test = new GenericItemBasedRecommender(dataModel,itemSimilarity).mostSimilarItems(3,2);
//            long[] test = new GenericUserBasedRecommender(dataModel,userNeighborhood,similarity).mostSimilarUserIDs(1,3);
//            System.out.println(Arrays.toString(test));
            //打印推荐的结果
            System.out.println("使用基于用户的协同过滤算法");
            int count = recommendedItemList.size();
            System.out.println("为用户推荐"+count+"个商品");
            for (RecommendedItem recommendedItem : recommendedItemList) {
                System.out.println(recommendedItem);
            }
            return recommendedItemList;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @RequestMapping("test7")
    public void test7() {
        try {
            RandomUtils.useTestSeed();
            File file = new File("G:/CODE/work/shoulashou_recommender/api/data/ratings.csv");
            DataModel dataModel = new FileDataModel(file);
            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
//            RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
            RecommenderBuilder builder = dataModel1 -> {
                UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel1);
                UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, userSimilarity, dataModel1);
                return new GenericUserBasedRecommender(dataModel1, userNeighborhood, userSimilarity);
            };
            double score = evaluator.evaluate(builder, null, dataModel, 0.7, 0.3);
            System.out.println(score);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @RequestMapping("test8")
    public void test8() {
        try {
            RandomUtils.useTestSeed();
            File file = new File("G:/CODE/work/shoulashou_recommender/api/data/ratings2.csv");
            DataModel dataModel = new FileDataModel(file);
            GenericRecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
            RecommenderBuilder recommenderBuilder = dataModel1 -> {
                UserSimilarity userSimilarity = new EuclideanDistanceSimilarity(dataModel1);
                UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel1);
                return new GenericUserBasedRecommender(dataModel1, userNeighborhood, userSimilarity);
            };
            IRStatistics statistics = evaluator.evaluate(recommenderBuilder, null, dataModel, null, 2, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);

            System.out.println(statistics.getPrecision());
            System.out.println(statistics.getRecall());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @RequestMapping("test9")
    public void test9() {
        FastByIDMap<PreferenceArray> fastByIDMap = new FastByIDMap<>();
        GenericUserPreferenceArray genericUserPreferenceArray = new GenericUserPreferenceArray(4);
        genericUserPreferenceArray.setUserID(1, 1);
        genericUserPreferenceArray.setItemID(1, 101);
        genericUserPreferenceArray.setValue(1, Float.parseFloat("4.5"));

        genericUserPreferenceArray.setItemID(2, 102);
        genericUserPreferenceArray.setValue(2, Float.parseFloat("5.0"));

        genericUserPreferenceArray.setItemID(3, 104);
        genericUserPreferenceArray.setValue(3, Float.parseFloat("2.0"));

        fastByIDMap.put(1, genericUserPreferenceArray);
        DataModel dataModel = new GenericDataModel(fastByIDMap);
//        Preference preference = genericUserPreferenceArray.get(0);
//        System.out.println(preference.getUserID());
//        System.out.println(preference.getItemID());
//        System.out.println(preference.getValue());
    }

    @RequestMapping("test10")
    public void test10() {
        try {
            File file = new File("G:/CODE/work/shoulashou_recommender/api/data/ratings.csv");
            FileDataModel fileDataModel = new FileDataModel(file);

            DataModel dataModel = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(fileDataModel));

            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
            RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
                @Override
                public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                    UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                    UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, dataModel);
                    return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
                }
            };
            DataModelBuilder modelBuilder = new DataModelBuilder() {
                @Override
                public DataModel buildDataModel(FastByIDMap<PreferenceArray> fastByIDMap) {
                    return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(fastByIDMap));
                }
            };

            double score = evaluator.evaluate(recommenderBuilder, modelBuilder, dataModel, 0.9, 0.3);
            System.out.println(score);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @RequestMapping("test11")
    public void test11() {
        try {
            File file = new File("G:/CODE/work/shoulashou_recommender/api/data/ratings.csv");
            FileDataModel fileDataModel = new FileDataModel(file);
            DataModel dataModel = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(fileDataModel));

            RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();

            RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
                @Override
                public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                    UserSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
                    UserNeighborhood neighborhood = new NearestNUserNeighborhood(1000, similarity, dataModel);
                    return new GenericBooleanPrefUserBasedRecommender(dataModel, neighborhood, similarity);
                }
            };

            DataModelBuilder dataModelBuilder = new DataModelBuilder() {
                @Override
                public DataModel buildDataModel(FastByIDMap<PreferenceArray> fastByIDMap) {
                    return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(fastByIDMap));
                }
            };

            IRStatistics irStatistics = evaluator.evaluate(recommenderBuilder, dataModelBuilder, dataModel, null, 10, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
            System.out.println(irStatistics.getPrecision());
            System.out.println(irStatistics.getRecall());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @RequestMapping("test1")
    public void test1() {
        try {
            File file = new File("G:/CODE/work/shoulashou_recommender/api/data/ratings.dat");
            DataModel dataModel = new GroupLensDataModel(file);

            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();

            RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
                @Override
                public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                    UserSimilarity similarity = new TanimotoCoefficientSimilarity(dataModel);
//                    UserSimilarity similarity = new CachingUserSimilarity(new SpearmanCorrelationSimilarity(dataModel), dataModel);
//                    UserSimilarity similarity = new EuclideanDistanceSimilarity(dataModel);
//                    UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
//                    UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, dataModel);
                    UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, dataModel);

                    return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
                }
            };

            double score = evaluator.evaluate(recommenderBuilder, null, dataModel, 0.95, 0.05);
            System.out.println(score);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @RequestMapping("test2")
    public void test2() {
        try {
            File file = new File("G:/CODE/work/shoulashou_recommender/api/data/ratings.dat");
            DataModel dataModel = new GroupLensDataModel(file);
            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();

            RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
                @Override
                public Recommender buildRecommender(DataModel dataModel) throws TasteException {
//                    ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
//                    ItemSimilarity similarity = new TanimotoCoefficientSimilarity(dataModel);
//                    ItemSimilarity similarity = new SpearmanCorrelationSimilarity(dataModel);
//                    ItemSimilarity similarity = new EuclideanDistanceSimilarity(dataModel);
//                    ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
//                    return new GenericItemBasedRecommender(dataModel, similarity);

//                    return new SVDRecommender(dataModel, new ALSWRFactorizer(dataModel, 10, 0.05, 10));
                    return new RandomRecommender(dataModel);
                }
            };
            double score = evaluator.evaluate(recommenderBuilder, null, dataModel, 0.95, 0.5);
            System.out.println(score);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
