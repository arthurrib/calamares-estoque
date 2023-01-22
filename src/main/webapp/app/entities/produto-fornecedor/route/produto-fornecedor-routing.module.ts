import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProdutoFornecedorComponent } from '../list/produto-fornecedor.component';
import { ProdutoFornecedorDetailComponent } from '../detail/produto-fornecedor-detail.component';
import { ProdutoFornecedorUpdateComponent } from '../update/produto-fornecedor-update.component';
import { ProdutoFornecedorRoutingResolveService } from './produto-fornecedor-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const produtoFornecedorRoute: Routes = [
  {
    path: '',
    component: ProdutoFornecedorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProdutoFornecedorDetailComponent,
    resolve: {
      produtoFornecedor: ProdutoFornecedorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProdutoFornecedorUpdateComponent,
    resolve: {
      produtoFornecedor: ProdutoFornecedorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProdutoFornecedorUpdateComponent,
    resolve: {
      produtoFornecedor: ProdutoFornecedorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(produtoFornecedorRoute)],
  exports: [RouterModule],
})
export class ProdutoFornecedorRoutingModule {}
