import DataPage from './pages/DataPage.vue'
import AuditPage from './pages/AuditPage.vue'
import DataListPage from './pages/DataListPage.vue'
import DataListTablePage from './pages/DataListTablePage.vue'
import DataListViewPage from './pages/DataListViewPage.vue'
import DataUploadPage from './pages/DataUploadPage.vue'
import DataHelpPage from './pages/DataHelpPage.vue'
 
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
  { path: '/audit', component: AuditPage }
]