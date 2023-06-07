import instance from "./request"
import qs from "qs"

//获取验证码请求
export const GetCaptchaAPI = () => instance.get("/captcha");
//登录请求
export const LoginAPI = (params) => instance.post("/login",params)
//获取用户信息
export const GetUserInfoAPI = () => instance.get("/userInfo");
//商品分类管理-分类列表信息
export const GetCategoryListAPI = (params) => instance.get("/category/list",{params});
//商品分类管理-根据id查询分类信息
export const GetCategoryInfoAPI = (params) => instance.get(`/category/info/${params}`);
//商品分类管理-根据id修改分类信息
export const UpdateCategoryAPI = (params) => instance.put("/category/update",params)
//商品分类管理-根据id添加分类信息
export const InsertCategoryAPI = (params) => instance.post("/category/insert",params)
//商品分类管理-删除分类信息
export const DeleteCategoryInfoAPI = (params) => instance.delete(`/category/delete/${params}`);
//商品分类管理-查询所有分类信息
export const GetCategoryListAllAPI = () => instance.get("/category/listAll");
//商品列表
export const GetProductListAPI = (params) => instance.get("/product/list",{params});

//根据分类id查询属性
export const GetPropertyByCategoryIdAPI = (params) => instance.get(`/property/list/${params}`);
//文件上传
export const ProductImageUpLoadAPI = (params,config) => instance.post("/product/upload",params,config)
//商品添加
export const InsertProductAPI = (params) => instance.post("/product/insert",params)
export const GetProductInfoAPI = (params) => instance.get(`/product/info/${params}`);
export const UpdateProductAPI = (params) => instance.put("/product/update",params)
export const DeleteProductInfoAPI = (params) => instance.delete(`/product/delete/${params}`);

export const GetUserListAPI = (params) => instance.get("/user/list",{params});


export const GetOrderListAPI = (params) => instance.get("/productorder/list",{params,paramsSerializer: params => {
    return qs.stringify(params, { indices: false })
  }});

export const GetOrderInfoAPI = (params) => instance.get(`/productorder/info/${params}`);

export const OrderSendAPI = (params) => instance.get(`/productorder/send/${params}`);


