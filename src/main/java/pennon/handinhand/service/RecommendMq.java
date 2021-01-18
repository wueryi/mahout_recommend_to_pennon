package pennon.handinhand.service;

public interface RecommendMq  extends CommonMq{
   void dispatch(long userId);

   void handle(long userId);
}
