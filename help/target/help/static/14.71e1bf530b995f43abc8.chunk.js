webpackJsonp([14],{518:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(611),r=n.n(o);for(var i in o)"default"!==i&&function(t){n.d(e,t,function(){return o[t]})}(i);var c=n(650),a=n.n(c),l=n(21),p=l(r.a,a.a,!1,null,null,null);p.options.__file="src\\views\\mgnoptrec\\mgnoptrec.vue",e.default=p.exports},611:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(199);e.default={data:function(){return{get_optrec_loading:!1,get_optrec_clearing:!1,optRecInfo:[],optReccolumns:[{title:"操作时间",key:"opttime",width:200},{title:"操作类型",key:"optdescription",width:200},{title:"操作内容",key:"optcontent"}]}},methods:{getOptRec:function(){var t=this;(0,o.GetOptRecInfo)().then(function(e){var n=e.retCode,o=e.Optinfolist;500===n&&(t.optRecInfo=o)})},clearOptRec:function(){var t=this;(0,o.ClearOptRecInfo)().then(function(e){500===e.retCode&&t.getOptRec()})}},mounted:function(){this.heightVar=window.innerHeight-this.$refs.optRecTable.$el.offsetTop-50,this.getOptRec()}}},650:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("Row",{attrs:{gutter:10}},[n("Col",{attrs:{span:"24"}},[n("Row",[n("Button",{attrs:{type:"primary",loading:t.get_optrec_loading},on:{click:t.getOptRec}},[t._v("刷新操作日志")]),t._v(" "),n("Button",{attrs:{type:"primary",loading:t.get_optrec_clearing},on:{click:t.clearOptRec}},[t._v("清除操作日志")])],1)],1)],1),t._v(" "),n("Row",[n("br")]),t._v(" "),n("Table",{ref:"optRecTable",attrs:{border:"",columns:t.optReccolumns,data:t.optRecInfo,height:t.heightVar}})],1)},r=[];o._withStripped=!0;var i={render:o,staticRenderFns:r};e.default=i}});