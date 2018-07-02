import DataPage from './pages/DataPage.vue'
import AuditPage from './pages/AuditPage.vue'
import DataListPage from './pages/DataListPage.vue'
import DataUploadPage from './pages/DataUploadPage.vue'
import DataHelpPage from './pages/DataHelpPage.vue'
 
export const routes = [
  { path: '/data', component: DataPage,
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
      },        
    ]
  },
  { path: '/audit', component: AuditPage }
]