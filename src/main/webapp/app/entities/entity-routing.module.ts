import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produto',
        data: { pageTitle: 'Produtos' },
        loadChildren: () => import('./produto/produto.module').then(m => m.ProdutoModule),
      },
      {
        path: 'fornecedor',
        data: { pageTitle: 'Fornecedors' },
        loadChildren: () => import('./fornecedor/fornecedor.module').then(m => m.FornecedorModule),
      },
      {
        path: 'produto-fornecedor',
        data: { pageTitle: 'ProdutoFornecedors' },
        loadChildren: () => import('./produto-fornecedor/produto-fornecedor.module').then(m => m.ProdutoFornecedorModule),
      },
      {
        path: 'pedido',
        data: { pageTitle: 'Pedidos' },
        loadChildren: () => import('./pedido/pedido.module').then(m => m.PedidoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
