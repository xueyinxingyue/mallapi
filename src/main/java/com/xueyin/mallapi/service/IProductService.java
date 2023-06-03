package com.xueyin.mallapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyin.mallapi.qo.ProductQo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 产品表 服务类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-03
 */
public interface IProductService extends IService<Product> {
    IPage<Product> page(ProductQo qo);

    /**
     * 图片上传
     * @param file          上传的图片文件
     * @param imageType     图片类型
     * @return              图片在服务器中保存路径
     */
    String upload(MultipartFile file,String imageType);
}
