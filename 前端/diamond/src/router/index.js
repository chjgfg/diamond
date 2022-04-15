import Vue from 'vue'
import VueRouter from 'vue-router'
import {get, set} from "../utils/local";

const Login = () => import("../views/login/Login");
const Dashboard = () => import("../views/dashboard/Dashboard");
const Admin = () => import("../views/dashboard/Admin");

const User = () => import("../views/manage/user/User");
const Logger = () => import("../views/manage/log/Logger");
const Log = () => import("../views/manage/log/Log");
const Purview = () => import("../views/manage/purview/Purview");
// const OnePurview = () => import("../views/manage/purview/OnePurview");
const Database = () => import("../views/manage/data/Database");
const Data = () => import("../views/manage/data/Data");
const Table = () => import("../views/manage/data/Table");
const Console = () => import("../views/manage/data/Console");
const Email = () => import("../views/manage/user/Email");
const OnePurview2 = () => import("../views/manage/purview/OnePurview2");

Vue.use(VueRouter);

const routes = [
  {
    path: '*',
    redirect: "/login"
  },
  {
    path: '',
    redirect: "/login"
  },
  {
    path: '/login',//这个很重要
    component: Login,
  },
  {
    path: '/',
    component: Dashboard,
    meta: {
      requireAuth: true//拦截
    },
    children: [
      {
        path: 'database',
        name: "database",
        component: Database,
        meta: {
          requireAuth: true//拦截
        },
      },

      {
        path: 'logger',
        name: "logger",
        component: Logger,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'purview',
        name: "purview",
        component: Purview,
        meta: {
          requireAuth: true//拦截
        },
      },

      // {
      //   path: 'one_purview',
      //   name: "one_purview",
      //   component: OnePurview,
      //   meta: {
      //     requireAuth: true//拦截
      //   },
      // },
      {
        path: 'one_purview2',
        name: "one_purview2",
        component: OnePurview2,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'table',
        name: "table",
        component: Table,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'data',
        name: "data",
        component: Data,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'log',
        name: "log",
        component: Log,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'user',
        component: User,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'console',
        component: Console,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'admin',
        name: "admin",
        component: Admin,
        meta: {
          requireAuth: true//拦截
        },
      },
      {
        path: 'email',
        name: "email",
        component: Email,
        meta: {
          requireAuth: true//拦截
        },
      },
    ],
  },


];


const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
};




const originalReplace = VueRouter.prototype.replace;
VueRouter.prototype.replace = function replace(location) {
  return originalReplace.call(this, location).catch(err => err)
};

const router = new VueRouter({
  routes
});


export default router
