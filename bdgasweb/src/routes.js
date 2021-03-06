import DataPage from './pages/DataPage.vue'
import AuditPage from './pages/AuditPage.vue'
import DataListPage from './pages/DataListPage.vue'
import DataListTablePage from './pages/DataListTablePage.vue'
import DataListViewPage from './pages/DataListViewPage.vue'
import DataUploadPage from './pages/DataUploadPage.vue'
import DataHelpPage from './pages/DataHelpPage.vue'
import AuditHelpTab from './pages/AuditHelpTab.vue'
import ParameterTab from './pages/ParameterTab.vue'
import WordCount from './actions/plugins/WordCount.vue'
import SelectDataSource from './actions/ingestion/SelectDataSource.vue'
import FindDuplicates from './actions/core/FindDuplicates.vue'
import FindGaps from './actions/core/FindGaps.vue'
import RecalcColumn from './actions/core/RecalcColumn.vue'
import Sum from './actions/core/Sum.vue'
import DescribeData from './actions/core/DescribeData.vue'
import Sample from './actions/core/Sample.vue'
import ShowData from './actions/display/ShowData.vue'
import AuditDataSourcesTab from './pages/AuditDataSourcesTab.vue'

export const routes = [
  { path: '/',
      redirect:  { name: 'data.list' }
  },  
  { path: '/data', 
    component: DataPage,
    children: [
      {
        path: '',
        component: DataListPage,
        name: 'data',
        redirect: { name: 'data.list' }
      },      
      {
        path: 'list',
        component: DataListPage,
        name: 'data.list',
        redirect: { name: 'data.list.table' },
        children: [ 
          {
            path: 'table',
            component: DataListTablePage,
            name: 'data.list.table',
          },
          {
            path: 'view',
            component: DataListViewPage,
            name: 'data.list.view',
          }
        ]
      },
      {
        path: 'upload',
        component: DataUploadPage,
        name: 'data.upload',
      },      
      {
        path: 'help',
        component: DataHelpPage,
        name: 'data.help',
      }        
    ]
  },
  { path: '/audit', 
    component: AuditPage,
    redirect:  { name: 'audit.param' },
    children: [
      {
        path: 'param',
        component: ParameterTab,
        name: 'audit.param',
      },
      {
        path: '/audit/param/di_selectdata',
        component: SelectDataSource,
        name: 'audit.DI_selectdata',
      },
      {
        path: '/audit/param/ct_describedata',
        component: DescribeData,
        name: 'audit.CT_describedata',
      }, 
      {
        path: '/audit/param/ct_findgaps',
        component: FindGaps,
        name: 'audit.CT_findgaps',
      }, 
      {
        path: '/audit/param/ct_findduplicates',
        component: FindDuplicates,
        name: 'audit.CT_findduplicates',
      }, 
      {
        path: '/audit/param/ct_sum',
        component: Sum,
        name: 'audit.CT_sum',
      }, 
      {
        path: '/audit/param/ct_sample',
        component: Sample,
        name: 'audit.CT_sample',
      }, 
      {
        path: '/audit/param/pt_wordcount',
        component: WordCount,
        name: 'audit.PT_wordcount',
      },  
      {
        path: '/audit/param/dd_showdata',
        component: ShowData,
        name: 'audit.DD_showdata',
      },      
      {
        path: 'datasource',
        component: AuditDataSourcesTab,
        name: 'audit.datasource',
      }, 
      {
        path: 'help',
        component: AuditHelpTab,
        name: 'audit.help',
      }        
    ]
  }
]