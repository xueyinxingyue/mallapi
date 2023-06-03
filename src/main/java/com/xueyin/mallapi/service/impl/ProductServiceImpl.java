package com.xueyin.mallapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyin.mallapi.entity.Product;
import com.xueyin.mallapi.entity.Productimage;
import com.xueyin.mallapi.entity.Propertyvalue;
import com.xueyin.mallapi.mapper.ProductMapper;
import com.xueyin.mallapi.mapper.ProductimageMapper;
import com.xueyin.mallapi.mapper.PropertyvalueMapper;
import com.xueyin.mallapi.qo.ProductQo;
import com.xueyin.mallapi.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-03
 */
@Service
@Transactional
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private ProductimageMapper productimageMapper;
    @Autowired
    private PropertyvalueMapper propertyvalueMapper;

    @Override
    public IPage<Product> page(ProductQo qo){
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(qo.getProductName()),Product::getProductName,qo.getProductName())
                .eq(qo.getCategoryId() != -1,Product::getProductCategoryId,qo.getCategoryId())
                .eq(qo.getIsUp() != -1,Product::getProductIsUp,qo.getIsUp())
                .ge(qo.getMinPrice() != null,Product::getProductSalePrice,qo.getMinPrice())
                .le(qo.getMaxPrice() != null,Product::getProductSalePrice,qo.getMaxPrice());

        IPage<Product> page = new Page<>(qo.getPageNum(), qo.getPageSize());
        page = baseMapper.selectPage(page,wrapper);
        return page;
    }

    @Override
    public String upload(MultipartFile file, String imageType) {
        //为上传的文件重新起名字       名字+原有文件后缀
        //1.获取到上传的文件原来的名字
        String originalFilename = file.getOriginalFilename();
        //2.获取名字中的后缀名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3.生成新的文件名
        String fileName = UUID.randomUUID() + ext;

        //上传文件：将上传的文件保存到服务器的某个位置
        //指定上传的文件保存位置
        String fileUpLoadPath = uploadPath + imageType;

        //保证文件保存位置一定存在
        File fileUpLoadPathObj = new File(fileUpLoadPath);
        //如果fileUpLoadPathObj代表的目录不存在
        if (!fileUpLoadPathObj.exists()){
            //创建目录
            fileUpLoadPathObj.mkdirs();
        }

        try {
            //将上传的文件保存到服务器中
            file.transferTo(new File(fileUpLoadPath + "/" + fileName));
            return "upload/" + imageType + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Product entity){
        //保存商品信息
        entity.setProductCreateDate(LocalDateTime.now());
        baseMapper.insert(entity);
        //保存商品的图片信息

        //保存概述图片
        List<Productimage> singlePic = entity.getSinglePic();
        if (singlePic != null && singlePic.size() > 0){
            for (Productimage productimage : singlePic) {
                //为productimage设置商品id
                productimage.setProductimageProductId(entity.getProductId());
                productimageMapper.insert(productimage);
            }
        }
        //保存详情图片
        List<Productimage> detailPic = entity.getSinglePic();
        if (detailPic != null && detailPic.size() > 0){
            for (Productimage productimage : detailPic) {
                //为productimage设置商品id
                productimage.setProductimageProductId(entity.getProductId());
                productimageMapper.insert(productimage);
            }
        }
        //保存属性值信息
        List<Propertyvalue> propvalues = entity.getPropvalues();
        if (propvalues != null && propvalues.size() > 0){
            for (Propertyvalue propvalue : propvalues){
                propvalue.setPropertyvalueProductId(entity.getProductId());
                propertyvalueMapper.insert(propvalue);
            }
        }
        return false;
    }
}
