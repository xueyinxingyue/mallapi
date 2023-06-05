package com.xueyin.mallapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyin.mallapi.entity.Product;
import com.xueyin.mallapi.entity.Productimage;
import com.xueyin.mallapi.entity.Property;
import com.xueyin.mallapi.entity.Propertyvalue;
import com.xueyin.mallapi.mapper.ProductMapper;
import com.xueyin.mallapi.mapper.ProductimageMapper;
import com.xueyin.mallapi.mapper.PropertyMapper;
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
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Autowired
    private PropertyMapper propertyMapper;

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
        entity.setProductCreateDate(new Date());
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

    @Override
    public Product getById(Serializable id) {
        //根据id查询商品信息
        Product product = baseMapper.selectById(id);
        //查询商品的概述图片信息
        LambdaQueryWrapper<Productimage> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Productimage::getProductimageProductId,id)
                .eq(Productimage::getProductimageType,0);
        List<Productimage> singlePic = productimageMapper.selectList(wrapper1);

        //查询商品的详细图片信息
        LambdaQueryWrapper<Productimage> wrapper2 = new LambdaQueryWrapper<>();
        wrapper1.eq(Productimage::getProductimageProductId,id)
                .eq(Productimage::getProductimageType,1);
        List<Productimage> detailPic = productimageMapper.selectList(wrapper2);

        //查询商品的属性值信息
        LambdaQueryWrapper<Propertyvalue> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(Propertyvalue::getPropertyvalueProductId,id);
        List<Propertyvalue> propertyvalues = propertyvalueMapper.selectList(wrapper3);
        //遍历出每一个属性值，分别查询属性值对应属性的名称
        if (propertyvalues != null && propertyvalues.size() > 0){
            for (Propertyvalue propertyvalue : propertyvalues){
                Property property = propertyMapper.selectById(propertyvalue.getPropertyvaluePropertyId());
                propertyvalue.setPropertyName(property.getPropertyName());
            }
        }

        //将概述图片、详细图片、属性值信息存储到product对象
        product.setSinglePic(singlePic);
        product.setDetailPic(detailPic);
        product.setPropvalues(propertyvalues);
        return product;
    }

    @Override
    public boolean updateById(Product entity) {
        //修改商品信息
        baseMapper.updateById(entity);
        //想删除商品原有的概述和详细图片
        LambdaUpdateWrapper<Productimage> wrapper1 = new LambdaUpdateWrapper<>();
        wrapper1.eq(Productimage::getProductimageProductId,entity.getProductId());
        productimageMapper.delete(wrapper1);
        //修改概述图片
        List<Productimage> singlePic = entity.getSinglePic();
        if (singlePic != null && singlePic.size() > 0){
            for (Productimage productimage : singlePic){
                productimage.setProductimageProductId(entity.getProductId());
                productimageMapper.insert(productimage);
            }
        }
        //修改详情图片信息
        List<Productimage> detailPic = entity.getSinglePic();
        if (detailPic != null && detailPic.size() > 0){
            for (Productimage productimage : detailPic){
                productimage.setProductimageProductId(entity.getProductId());
                productimageMapper.insert(productimage);
            }
        }
        //修改属性值信息
        //删除商品原有的属性值信息
        LambdaUpdateWrapper<Propertyvalue> wrapper2 = new LambdaUpdateWrapper<>();
        wrapper2.eq(Propertyvalue::getPropertyvalueProductId,entity.getProductId());
        propertyvalueMapper.delete(wrapper2);
        List<Propertyvalue> propvalues = entity.getPropvalues();
        if (propvalues != null && propvalues.size() > 0){
            for (Propertyvalue propvalue : propvalues){
                propvalue.setPropertyvaluePropertyId(entity.getProductId());
                propertyvalueMapper.insert(propvalue);
            }
        }
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        //删除商品信息
        baseMapper.deleteById(id);
        //删除商品的图片信息
        LambdaUpdateWrapper<Productimage> wrapper1 = new LambdaUpdateWrapper<>();
        wrapper1.eq(Productimage::getProductimageProductId,id);
        productimageMapper.delete(wrapper1);
        //删除商品属性值信息
        LambdaUpdateWrapper<Propertyvalue> wrapper2 = new LambdaUpdateWrapper<>();
        wrapper2.eq(Propertyvalue::getPropertyvalueProductId,id);
        propertyvalueMapper.delete(wrapper2);

        return false;
    }
}
