package payhere.cafeproduct.api.productCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategory, Integer>, ProductCategoryJpaCustomRepository {
    @Modifying
    @Transactional(rollbackFor = {Exception.class})
    @Query("UPDATE ProductCategory pc SET pc.name = :name, pc.exposeYn = :exposeYn, pc.modifiedDate = now() WHERE pc.id = :productCategoryId")
    int updateProductCategory(@Param("name") String name, @Param("exposeYn") String exposeYn, @Param("productCategoryId") Integer productCategoryId);

    @Modifying
    @Transactional(rollbackFor = {Exception.class})
    @Query("UPDATE ProductCategory pc SET pc.delYn = 'Y', pc.deletedId = :userId, pc.deletedDate = now() WHERE pc.id IN :ids")
    int deleteProductCategory(@Param("ids") List<Integer> ids, @Param("userId") Integer userId);
}
