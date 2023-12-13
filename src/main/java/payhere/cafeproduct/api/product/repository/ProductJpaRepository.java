package payhere.cafeproduct.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import payhere.cafeproduct.api.product.domain.Product;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.global.enums.ProductSize;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductJpaCustomRepository {
    @Modifying
    @Transactional(rollbackFor = {Exception.class})
    @Query("UPDATE Product p SET p.price = :price, p.cost = :cost, p.name = :name, p.code = :code, " +
            "p.expirationDate = :expirationDate,p.size = :productSize, p.exposeYn = :exposeYn, " +
            "p.soldOutYn = :soldOutYn, p.productCategory.id = :productCategoryId, p.modifiedId = :id, p.modifiedDate = now() WHERE p.id = :productId")
    int updateProduct(@Param("price") Integer price, @Param("cost") Integer cost,
                      @Param("name") String name, @Param("code") String code,
                      @Param("expirationDate") LocalDateTime expirationDate, @Param("productSize") ProductSize productSize, @Param("exposeYn") String exposeYn,
                      @Param("soldOutYn") String soldOutYn, @Param("productCategoryId") Integer productCategoryId, @Param("id") Integer id, @Param("productId") Long productId);

    @Modifying
    @Transactional(rollbackFor = {Exception.class})
    @Query("UPDATE Product p SET p.delYn = 'Y', p.deletedId = :userId, p.deletedDate = now() where p.id IN :ids")
    int deleteProduct(@Param("ids") List<Long> ids, @Param("userId") Integer userId);
}
