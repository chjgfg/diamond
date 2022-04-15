<template>
  <v-container class="grey lighten-5"><!--是阴影-->
    <v-row>
      <v-col md="12" sm="3" lg="12"> <!--md多少列-->
        <div id="main" style="width: 1200px;height: 700px;"></div>
      </v-col>
    </v-row>
  </v-container>
</template>
<!--

1.title:标题组件
2.tooltip:提示框组件
3.legend：图例组件，展现了不同系列的标记(symbol)，颜色和名字
4.xAxis：直角坐标系 grid 中的 x 轴，单个 grid 组件最多只能放上下两个 x 轴。
5.yAxis:直角坐标系 grid 中的 y 轴，单个 grid 组件最多只能放左右两个 y 轴。
6.series:系列列表。每个系列通过 type 决定自己的图表类型。
   series  type=line ——-折线图
   series  type=bar ——-柱状图
   series  type=pie ——-饼图
-->
<script>
  import * as echarts from 'echarts';//看这引入方式多特别
  export default {
    name: "",
    data: () => ({
      charts: '',
      opinion: ['日志', '用户', '库', '表', '数据'],
      opinionData: [
        {value: 335, name: '表'},
        {value: 310, name: '库'},
        {value: 234, name: '用户'},
        {value: 135, name: '日志'},
        {value: 1548, name: '数据'}
      ]
    }),
    methods: {
      drawPie(id) {
        this.charts = echarts.init(document.getElementById(id), 'dark');
        this.charts.setOption({
          tooltip: {
            trigger: 'item',
            formatter: '{a}<br/>{b}:{c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            x: 'left',
            data: this.opinion
          },
          series: [
            {
              name: '统计数据',
              type: 'pie',
              radius: ['50%', '70%'],
              avoidLabelOverlap: false,
              label: {
                // 普通样式。
                normal: {
                  show: false,
                  position: 'center'
                },
                // 高亮样式。
                emphasis: {
                  show: true,
                  textStyle: {
                    fontSize: '30',
                    fontWeight: 'blod'
                  }
                }
              },
              labelLine: {
                normal: {
                  show: false
                }
              },
              data: this.opinionData
            }
          ]
        })
      }
    },
    //调用
    mounted() {
      this.$nextTick(function () {
        this.drawPie('main', 'sc')
      })
    }
  }
</script>

<style>
  /** {*/
  /*  margin: 0 !important;*/
  /*}*/

</style>
