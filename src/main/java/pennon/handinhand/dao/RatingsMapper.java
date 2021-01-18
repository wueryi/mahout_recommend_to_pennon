package pennon.handinhand.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import pennon.handinhand.entity.Ratings;
import pennon.handinhand.entity.RatingsExample;

@Repository
public interface RatingsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    long countByExample(RatingsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int deleteByExample(RatingsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("itemId") Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int insert(Ratings record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int insertSelective(Ratings record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    List<Ratings> selectByExampleWithRowbounds(RatingsExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    List<Ratings> selectByExample(RatingsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    Ratings selectByPrimaryKey(@Param("userId") Integer userId, @Param("itemId") Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Ratings record, @Param("example") RatingsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Ratings record, @Param("example") RatingsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Ratings record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ratings
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Ratings record);
}