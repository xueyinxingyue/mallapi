import Vue from 'vue'
import VueRouter from 'vue-router'
// import HomeView from '../views/HomeView.vue'
// import {GetUserRoutersAPI} from '@/request/api'
// import store from '@/store'

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function (location) {
  return originalPush.call(this, location).catch(err => { })
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'mainlayout',
    component: () => import(/* webpackChunkName: "mainlayout" */ '../views/layout/MainLayout.vue'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        component: () => import(/* webpackChunkName: "home" */ '../views/HomeView.vue'),
        meta: { titles: ['首页'] }
      },
      {
        path: '/category',
        component: () => import(/* webpackChunkName: "customer" */ '../views/category/Category.vue'),
        meta: { titles: ['商品分类'] }
      },
      {
        path: '/product',
        component: () => import(/* webpackChunkName: "product" */ '../views/product/Product.vue'),
        meta: { titles: ['所有商品'] }
      },
      {
        path: '/user',
        component: () => import(/* webpackChunkName: "product" */ '../views/user/User.vue'),
        meta: { titles: ['用户管理'] }
      },
      {
        path: '/order',
        component: () => import(/* webpackChunkName: "product" */ '../views/order/Order.vue'),
        meta: { titles: ['所有订单'] }
      }
    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "login" */ '../views/login/Login.vue')
  },
  {
    path: '*',
    component: () => import(/* webpackChunkName: "404" */ '../views/404.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//路由守卫
router.beforeEach(async (to, from, next) => {
  const token = localStorage.getItem("m-authorization-token");
  //1.如果用户访问登录页面，但是token已经存在，跳转到首页
  if (to.path === '/login' && token) {
    next("/")
    return;
  }
  //2.如果用户访问不是登录页面，但是没有token，跳转到登录页面
  if (to.path !== '/login' && !token) {
    next("/login");
    return;
  }

  // if(token && store.state.userMenuData.menuData.length == 0){
  //   let res = await GetUserRoutersAPI();
  //   console.log(res);
  //   if(!res) return
  //   //请求到的res这个数据和我们想要的menuData结构上不太一样
  //   //所以需要通过res，生合成我们想要的menuData
  //   let newUserMenuData=[{title:'首页',path:'/home',icon:'dashboard'}]

  //   let ret = res.data.map(item =>{
  //     if(item.children){
  //       return {
  //         title:item.meta.title,
  //         path:item.path,
  //         icon:item.meta.icon,
  //         children:item.children.map(subItem =>{
  //           return {
  //             title: subItem.meta.title,
  //             path: item.path + "/" + subItem.path
  //           }
  //         })
  //       }
  //     }else{
  //       return {
  //         title:item.meta.title,
  //         path:item.path,
  //         icon:item.meta.icon
  //       }
  //     }

  //   })

  //   newUserMenuData = [...newUserMenuData,...ret]
  //   //store.commit("文件夹名称/方法名",要传递的参数)
  //   store.commit("userMenuData/changeMenuData",newUserMenuData)
  //   console.log(newUserMenuData);

  //   //以上生成菜单数据-----------------以下生成用户可以访问的路由数据
  // let newChildrenRoutes = [
  //   {
  //     path: '/home',
  //     component: () => import('../views/HomeView.vue'),
  //     meta:{
  //       titles:['首页']
  //     }
  //   },
  //   {
  //     path: '/profile',
  //     component: () => import('../views/Profile.vue'),
  //     meta:{
  //       titles:['个人中心']
  //     }
  //   },
  // ]
  //   res.data.forEach(item =>{
  //     let ret = item.children.map(subItem =>{
  //       return {
  //         path:item.path + "/" + subItem.path,
  //         component:() => import(`../views${item.path}/${subItem.name}.vue`),
  //         meta:{
  //           titles:[item.meta.title,subItem.meta.title]
  //         }
  //       }
  //     })
  //     newChildrenRoutes = [...newChildrenRoutes,...ret]
  //   });

  //   console.log("newChildrenRoutes:" , newChildrenRoutes);

  //   //将生成好的路由数据添加到mainlayout路由里面的children，做为子路由
  //   //router.addRoute(父路由名称，单个子路由对象)
  // newChildrenRoutes.forEach(item =>{
  //   router.addRoute("mainlayout",item)
  // });
  //   //这个to.path一定要写
  //   //如果直接next(),路由还没有完整，还是空的，不确定路由有没有这个路径
  //   //加了to.path之后，会重新走一遍路由守卫，此时路由添加完成，可以检查出用户能否访问这个路径
  //   next(to.path);
  //   return;

  // }



  next()  //放行
})

export default router
