import Vue from 'vue'
import { Button,Input,Form,FormItem,Message,MenuItemGroup,MenuItem,Menu,Submenu,Breadcrumb,BreadcrumbItem,Dropdown,DropdownItem,DropdownMenu,Tag,Table,TableColumn,Pagination,Tooltip,Dialog,MessageBox,Col,Card, Row,Switch, Select,Option, Checkbox, Divider, Radio, Upload } from 'element-ui'

Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox.confirm
//注册
Vue.use(Button)
Vue.use(Input)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Submenu)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Dropdown)
Vue.use(DropdownMenu)
Vue.use(DropdownItem)
Vue.use(Tag)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Pagination)
Vue.use(Tooltip)
Vue.use(Dialog)
Vue.use(Col)
Vue.use(Card)
Vue.use(Row)
Vue.use(Switch)
Vue.use(Select)
Vue.use(Option)
Vue.use(Checkbox)
Vue.use(Divider)
Vue.use(Radio)
Vue.use(Upload)

